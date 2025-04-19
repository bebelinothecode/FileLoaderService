package com.IsaacDickson.PIACProject.FolderMonitorService.service;

import com.IsaacDickson.PIACProject.FolderMonitorService.Entity.CDRLogsControlTable;
import com.IsaacDickson.PIACProject.FolderMonitorService.Entity.CallDetailRecords;
import com.IsaacDickson.PIACProject.FolderMonitorService.repository.CDRControlTableRepository;
import com.IsaacDickson.PIACProject.FolderMonitorService.repository.FileWatcherRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;

@Service
public class FolderMonitorService {
    private static final Logger logger = LoggerFactory.getLogger(FolderMonitorService.class);
    private static final int BATCH_SIZE = 500;

    @Value("${file.input.dir}")
    private String inputDir;

    @Value("${file.processed.dir}")
    private String processedDir;

    @Value("${file.rejected.dir}")
    private String rejectedDir;

    private final FileWatcherRepository repository;
    private final CDRControlTableRepository cdrControlTableRepository;

    public FolderMonitorService(FileWatcherRepository repository, CDRControlTableRepository cdrControlTableRepository) {
        this.repository = repository;
        this.cdrControlTableRepository = cdrControlTableRepository;
    }

    @PostConstruct
    public void init() {
        try{
            Files.createDirectories(Paths.get(inputDir));
            Files.createDirectories(Paths.get(processedDir));
            Files.createDirectories(Paths.get(rejectedDir));
            logger.info("Input, Processed, and Rejected directories verified/created.");
        } catch (IOException e) {
            logger.error("Failed to create directories", e);
            throw new RuntimeException(e);
        }
    }

    @Scheduled(fixedRate = 60000)
    public void monitor() {
        File folder = new File(inputDir);
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

        if (files == null) {
            logger.warn("No files found in input directory.");
            return;
        }

        for(File file : files) {
            logger.info("Processing file: {}", file.getName());
            try{
                processFile(file);
            } catch (Exception e) {
                logger.error("Error processing file: {}", file.getName(), e);
                moveFile(file, rejectedDir);
            }
        }
    }

    private CallDetailRecords mapFieldsToEntity(List<String> fields) {
        CallDetailRecords record = new CallDetailRecords();
        try {
            Field[] declaredFields = CallDetailRecords.class.getDeclaredFields();
            int dataIndex = 0;
            for (Field field : declaredFields) {
                if (field.getName().equals("id")) {
                    continue;
                }
                field.setAccessible(true);
                if (dataIndex >= fields.size()) break;

                if (field.getType().equals(String.class)) {
                    String value = fields.get(dataIndex).trim();
                    if (!value.isEmpty()) {
                        field.set(record, value);
                    }
                }

                dataIndex++;
            }
        } catch (Exception e) {
            logger.error("Failed to map fields to entity", e);
        }
        return record;
    }

    private void processFile(File file) throws IOException {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        CDRLogsControlTable cdrLog = new CDRLogsControlTable();
        cdrLog.setFileName(file.getName());
        cdrLog.setUploadStartTime(startTime);
        cdrLog.setNumOfSuccessfullyLoadedRecords(0);
        cdrLog.setNumOfFailedRecords(0);
        cdrLog = cdrControlTableRepository.save(cdrLog);

        int successful = 0;
        int failed = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<CallDetailRecords> batch = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<CallDetailRecords> record = parseLine(line);

                if (record.isPresent()) {
                    batch.add(record.get());
                    successful++;
                } else {
                    failed++;
                }

                if (batch.size() >= BATCH_SIZE) {
                    saveBatch(batch);
                    batch.clear();
                }
            }

            if (!batch.isEmpty()) {
                saveBatch(batch);
                batch.clear();
            }

            moveFile(file, processedDir);
            logger.info("Completed processing file: {}", file.getName());

        } catch (Exception e) {
            logger.error("Error processing file: {}", file.getName(), e);
            moveFile(file, rejectedDir);
        } finally {
            Timestamp endTime = new Timestamp(System.currentTimeMillis());
            cdrLog.setUploadEndTime(endTime);
            cdrLog.setNumOfSuccessfullyLoadedRecords(successful);
            cdrLog.setNumOfFailedRecords(failed);
            cdrControlTableRepository.save(cdrLog);
        }
    }

    private void saveBatch(List<CallDetailRecords> batch) {
        try {
            repository.saveAll(batch);
            logger.info("Inserted batch of {} records", batch.size());
        } catch (Exception e) {
            logger.error("Failed to save batch of records", e);
        }
    }

    private Optional<CallDetailRecords> parseLine(String line) {
        try {
            String[] fields = line.split("\\|", -1);
            if (fields.length != 33) {
                logger.error("Line does not have expected 33 fields: {}", line);
                return Optional.empty();
            }

            CallDetailRecords record = new CallDetailRecords();
            record.setRecordDate(fields[0]);
            record.setLSpc(parseIntegerOrNull(fields[1]));
            record.setLSsn(parseIntegerOrNull(fields[2]));
            record.setLRi(parseIntegerOrNull(fields[3]));
            record.setLGtI(parseIntegerOrNull(fields[4]));
            record.setLGtDigits(fields[5]);
            record.setRSpc(parseIntegerOrNull(fields[6]));
            record.setRSsn(parseIntegerOrNull(fields[7]));
            record.setRRi(parseIntegerOrNull(fields[8]));
            record.setRGtI(parseIntegerOrNull(fields[9]));
            record.setRGtDigits(fields[10]);
            record.setServiceCode(fields[11]);
            record.setOrNature(parseIntegerOrNull(fields[12]));
            record.setOrPlan(parseIntegerOrNull(fields[13]));
            record.setOrDigits(fields[14]);
            record.setDeNature(parseIntegerOrNull(fields[15]));
            record.setDePlan(parseIntegerOrNull(fields[16]));
            record.setDeDigits(fields[17]);
            record.setIsdnNature(parseIntegerOrNull(fields[18]));
            record.setIsdnPlan(parseIntegerOrNull(fields[19]));
            record.setMsisdn(fields[20]);
            record.setVlrNature(parseIntegerOrNull(fields[21]));
            record.setVlrPlan(parseIntegerOrNull(fields[22]));
            record.setVlrDigits(fields[23]);
            record.setImsi(fields[24]);
            record.setStatus(fields[25]);
            record.setType(fields[26]);
            record.setTstamp(fields[27]);
            record.setLocalDialogId(parseLongOrNull(fields[28]));
            record.setRemoteDialogId(parseLongOrNull(fields[29]));
            record.setDialogDuration(parseLongOrNull(fields[30]));
            record.setUssdString(fields[31]);
            record.setReferenceID(fields[32]);

            return Optional.of(record);
        } catch (Exception e) {
            logger.error("Error parsing line: {}", line, e);
            return Optional.empty();
        }
    }

    private Integer parseIntegerOrNull(String field) {
        return (field == null || field.isEmpty()) ? null : Integer.parseInt(field);
    }

    private Long parseLongOrNull(String field) {
        return (field == null || field.isEmpty()) ? null : Long.parseLong(field);
    }


    private void moveFile(File file, String destinationDir) {
        try {
            Path destination = Paths.get(destinationDir, file.getName());
            Files.move(file.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);
            logger.info("Moved file '{}' to '{}'", file.getName(), destinationDir);
        } catch (IOException e) {
            logger.error("Error moving file: {}", file.getName(), e);
        }
    }
}