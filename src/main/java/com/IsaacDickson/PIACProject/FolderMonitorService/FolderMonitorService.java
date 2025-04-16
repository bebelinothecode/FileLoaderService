package com.IsaacDickson.PIACProject.FolderMonitorService;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class FolderMonitorService {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(FolderMonitorService.class);

    private Map<String, Long> fileLastModifiedMap = new HashMap<>();

    @Value("${folder.monitor.path}")
    private String folderPath;

    @Scheduled(fixedRate = 60000)

    public void monitorFolder() {
        logger.info("Checking Folder for changes:");

        try{
            Path directory = Paths.get(folderPath);
            if (!Files.exists(directory)) {
                logger.warning("Directory does not exist: {}");
                return;
            }

            Map<String, Long> currentFiles = new HashMap<>();

            Files.list(directory).forEach(file -> {
                String fileName = file.getFileName().toString();
                long lastModified = file.toFile().lastModified();
                currentFiles.put(fileName, lastModified);
            });

            if(fileLastModifiedMap.isEmpty()) {
                logger.info("Initial folder scan, found {} files");
            } else {
                for (Map.Entry<String,Long> entry:currentFiles.entrySet()) {
                    String fileName = entry.getKey();
                    Long lastModified = entry.getValue();

                    if (!fileLastModifiedMap.containsKey(fileName)) {
                        logger.info("New file detected: {}");
                        // Handle new file logic here
                        processNewFile(Paths.get(folderPath, fileName));
                    } else if (!fileLastModifiedMap.get(fileName).equals(lastModified)) {
                        logger.info("File modified: {}");
                        // Handle modified file logic here
                        processModifiedFile(Paths.get(folderPath, fileName));
                    }
                }
                for (String fileName : fileLastModifiedMap.keySet()) {
                    if (!currentFiles.containsKey(fileName)) {
                        logger.info("File deleted: {}");
                        // Handle deleted file logic here
                        processDeletedFile(fileName);
                    }
                }
            }

            fileLastModifiedMap = new HashMap<>(currentFiles);
        } catch (Exception e) {
            logger.severe("Error monitoring folder: {}");
        }

    }
    private void processNewFile(Path filePath) {
        // Implement your logic for new files here
        logger.info("Processing new file: {}");
    }

    private void processModifiedFile(Path filePath) {
        // Implement your logic for modified files here
        logger.info("Processing modified file: {}");
    }

    private void processDeletedFile(String fileName) {
        // Implement your logic for deleted files here
        logger.info("Processing deleted file: {}");
    }
}
