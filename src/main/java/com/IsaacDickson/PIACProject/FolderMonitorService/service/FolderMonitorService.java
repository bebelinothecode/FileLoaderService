package com.IsaacDickson.PIACProject.FolderMonitorService.service;

import com.IsaacDickson.PIACProject.FolderMonitorService.Entity.CallDetailRecords;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    public FolderMonitorService(FileWatcherRepository repository) {
        this.repository = repository;
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
//                throw new RuntimeException(e);
            }
        }
    }

//    private CallDetailRecords mapFieldsToEntity(List<String> fields) {
//        CallDetailRecords record = new CallDetailRecords();
//        try {
//            Field[] declaredFields = CallDetailRecords.class.getDeclaredFields();
//            int dataIndex = 0;
//            for (Field field : declaredFields) {
//                if (Modifier.isStatic(field.getModifiers()) || field.getName().equals("id")) {
//                    continue; // Skip static fields and id
//                }
//                field.setAccessible(true);
//                if (dataIndex >= fields.size()) break;
//
//                String value = fields.get(dataIndex).trim();
//
//                if (value.isEmpty()) {
//                    // Handle empty field
//                    dataIndex++;
//                    continue; // Skip setting this field if empty
//                }
//
//                if (field.getType().equals(String.class)) {
//                    field.set(record, value);
//                } else if (field.getType().equals(LocalDateTime.class)) {
//                    try {
//                        field.set(record, LocalDateTime.parse(value));
//                    } catch (DateTimeParseException e) {
//                        logger.warn("Invalid timestamp format: '{}' at column {}", value, dataIndex);
//                    }
//                } else if (field.getType().equals(Integer.class) || field.getType().equals(int.class)) {
//                    try {
//                        field.set(record, Integer.parseInt(value));
//                    } catch (NumberFormatException e) {
//                        logger.warn("Invalid integer value: '{}' at column {}", value, dataIndex);
//                    }
//                } else if (field.getType().equals(Long.class) || field.getType().equals(long.class)) {
//                    try {
//                        field.set(record, Long.parseLong(value));
//                    } catch (NumberFormatException e) {
//                        logger.warn("Invalid long value: '{}' at column {}", value, dataIndex);
//                    }
//                }
//
//                dataIndex++;
//            }
//        } catch (Exception e) {
//            logger.error("Failed to map fields to entity", e);
//        }
//        return record;
//    }

    private CallDetailRecords mapFieldsToEntity(List<String> fields) {
        CallDetailRecords record = new CallDetailRecords();
        try {
            Field[] declaredFields = CallDetailRecords.class.getDeclaredFields();
            int dataIndex = 0;
            for (Field field : declaredFields) {
                if (field.getName().equals("id")) {
                    continue; // Skip static fields and ID field
                }
                field.setAccessible(true);
                if (dataIndex >= fields.size()) break;

                // Only process String fields
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
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            List<CallDetailRecords> batch = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null) {
                Optional<CallDetailRecords> optionalRecord = parseLine(line); // parseLine returns Optional

                if (optionalRecord.isPresent()) { // Correctly check if present
                    batch.add(optionalRecord.get());

                    if (batch.size() >= BATCH_SIZE) {
                        saveBatch(batch);
                        batch.clear();
                    }
                }
            }

            // Save any remaining records
            if (!batch.isEmpty()) {
                saveBatch(batch);
                batch.clear();
            }

            moveFile(file, processedDir);
            logger.info("Completed processing file: {}", file.getName());
        } catch (Exception e) {
            logger.error("Error processing file: {}", file.getName(), e);
            moveFile(file, rejectedDir);
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


//    private CallDetailRecords parseLine(String line) {
//        String[] parts = line.split("\\|", -1); // -1 to keep trailing empty strings
//
//        CallDetailRecords record = new CallDetailRecords();
//
//        try {
//            // Manually mapping by position (index starts from 0)
//            record.setRecordDate(Timestamp.valueOf(LocalDateTime.parse(parts[0], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS"))));
//            record.setLSpc(Integer.valueOf(parts[1]));
//            record.setLSsn(Integer.valueOf(parts[2]));
//            record.setLRi(Integer.valueOf(parts[3]));
//            record.setLGtI(Integer.valueOf(parts[4]));
//            record.setLGtDigits(parts[5]);
//            record.setRSpc(Integer.valueOf(parts[6]));
//            record.setRSsn(Integer.valueOf(parts[7]));
//            record.setRRi(Integer.valueOf(parts[8]));
//            record.setRGtI(Integer.valueOf(parts[9]));
//            record.setRGtDigits(parts[10]);
//            record.setServiceCode(parts[11]);
//            record.setOrNature(Integer.valueOf(parts[12]));
//            record.setOrPlan(Integer.valueOf(parts[13]));
//            record.setOrDigits(parts[14]);
//            record.setDeNature(Integer.valueOf(parts[15]));
//            record.setDePlan(Integer.valueOf(parts[16]));
//            record.setDeDigits(parts[17]);
//            record.setIsdnNature(Integer.valueOf(parts[18]));
//            record.setIsdnPlan(Integer.valueOf(parts[19]));
//            record.setMsisdn(parts[20]);
//            record.setVlrNature(Integer.valueOf(parts[21]));
//            record.setVlrPlan(Integer.valueOf(parts[22]));
//            record.setVlrDigits(parts[23]);
//            record.setImsi(parts[24]);
//            record.setStatus(parts[25]);
//            record.setType(parts[26]);
//            record.setTstamp(Timestamp.valueOf(LocalDateTime.parse(parts[27], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss,SSS"))));
//            record.setLocalDialogId(Long.valueOf(parts[28]));
//            record.setRemoteDialogId(Long.valueOf(parts[29]));
//            record.setDialogDuration(Long.valueOf(parts[30]));
//            record.setReferenceID(parts[31]);
//        } catch (Exception e) {
//            // You can log the bad line here
//            System.err.println("Error parsing line: " + line);
//            e.printStackTrace();
//            // Optionally rethrow or handle differently
//        }
//        return record;
//    }

//    private Optional<CallDetailRecords> parseLine(String line) {
//        // Early check for empty/null line
//        if (line == null || line.trim().isEmpty()) {
//            logger.warn("Empty or null line received");
//            return Optional.empty();
//        }
//
//        try {
//            String[] fields = line.split("\\|", -1);
//
//            // Validate field count before processing
//            if (fields.length < 33) {
//                logger.error("Invalid field count. Expected >=33, got {}. Line: {}", fields.length, line);
//                return Optional.empty();
//            }
//
//            // Initialize builder or record
//            CallDetailRecords record = new CallDetailRecords();
//
//            // Parse with individual field validation
//            record.setRecordDate(Timestamp.valueOf(parseTimestamp(fields[0])
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid record date"))));
//
//            // Set all fields with validation
//            record.setLSpc(validateAndParseInt(fields[1], "LSpc"));
//            record.setLSsn(validateAndParseInt(fields[2], "LSsn"));
//            record.setLRi(validateAndParseInt(fields[3], "LRi"));
//            record.setLGtI(validateAndParseInt(fields[4], "LGtI"));
//            record.setLGtDigits(validateString(fields[5], "LGtDigits"));
//
//            // Continue with other fields...
//            record.setRSpc(validateAndParseInt(fields[6], "RSpc"));
//            record.setRSsn(validateAndParseInt(fields[7], "RSsn"));
//            record.setRRi(validateAndParseInt(fields[8], "RRi"));
//            record.setRGtI(validateAndParseInt(fields[9], "RGtI"));
//            record.setRGtDigits(validateString(fields[10], "RGtDigits"));
//
//            // Handle critical fields with special validation
//            if (fields[20] == null || fields[20].trim().isEmpty()) {
//                throw new IllegalArgumentException("MSISDN cannot be empty");
//            }
//            record.setMsisdn(fields[20].trim());
//
//            // Parse remaining fields...
//            record.setImsi(validateString(fields[24], "IMSI"));
//            record.setStatus(validateString(fields[25], "Status"));
//            record.setType(validateString(fields[26], "Type"));
//
//            record.setTstamp(Timestamp.valueOf(parseTimestamp(fields[27])
//                    .orElseThrow(() -> new IllegalArgumentException("Invalid timestamp"))));
//
//            record.setLocalDialogId(validateAndParseLong(fields[28], "LocalDialogId"));
//            record.setRemoteDialogId(validateAndParseLong(fields[29], "RemoteDialogId"));
//            record.setDialogDuration(validateAndParseLong(fields[30], "DialogDuration"));
//
//            record.setUssdString(fields[31]); // Optional field
//            record.setReferenceID(validateString(fields[32], "ReferenceID"));
//
//            return Optional.of(record);
//
//        } catch (IllegalArgumentException e) {
//            logger.error("Validation error parsing line: {} - {}", line, e.getMessage());
//            return Optional.empty();
//        } catch (Exception e) {
//            logger.error("Unexpected error parsing line: {}", line, e);
//            return Optional.empty();
//        }
//    }
    private Optional<CallDetailRecords> parseLine(String line) {
        try {
            String[] fields = line.split("\\|", -1); // <- Important: use limit -1 to include empty fields!
            System.out.println("Line has " + fields.length + " fields."); // Debugging
            logger.info("Line has{}fields", fields.length);

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

    // Helper methods
    private Integer parseIntegerOrNull(String field) {
        return (field == null || field.isEmpty()) ? null : Integer.parseInt(field);
    }

    private Long parseLongOrNull(String field) {
        return (field == null || field.isEmpty()) ? null : Long.parseLong(field);
    }

    private String validateString(String value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null");
        }
        return value.trim();
    }

    private Optional<LocalDateTime> parseTimestamp(String timestamp) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss[,SSS][.SSS]");
            return Optional.of(LocalDateTime.parse(timestamp, formatter));
        } catch (Exception e) {
            return Optional.empty();
        }
    }



    private Integer safeParseInt(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Integer.valueOf(value);
    }

    private Long safeParseLong(String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }
        return Long.valueOf(value);
    }

//    private Timestamp parseTimestamp(String value) {
//        if (value == null || value.isEmpty()) {
//            return null;
//        }
//        if (value.contains(",")) {
//            value = value.replace(',', '.'); // replace ',' with '.' for milliseconds
//        }
//        return Timestamp.valueOf(value.replace("T", " "));
//    }


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