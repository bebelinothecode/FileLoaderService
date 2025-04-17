package com.IsaacDickson.PIACProject.FolderMonitorService.service;

import com.IsaacDickson.PIACProject.FolderMonitorService.repository.ControlTableRepository;
import jakarta.annotation.PostConstruct;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class FolderMonitorService {
    private static final Logger logger = (Logger) LoggerFactory.getLogger(FolderMonitorService.class);
    private static final String EXPECTED_HEADER = "caller_id,receiver_id,timestamp,duration";

    @Value("${file.input.dir}")
    private String inputDir;

    @Value("${file.processed.dir}")
    private String processedDir;

    @Value("${file.rejected.dir}")
    private String rejectedDir;

    private final ControlTableRepository repository;

    public FolderMonitorService(ControlTableRepository repository) {
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
            logger.severe("Failed to create directories"+ e);
            throw new RuntimeException(e);
        }
    }
}