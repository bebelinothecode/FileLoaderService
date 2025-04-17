package com.IsaacDickson.PIACProject.FolderMonitorService.repository;

import com.IsaacDickson.PIACProject.FolderMonitorService.Entity.CallDetailRecords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileWatcherRepository extends JpaRepository<CallDetailRecords, Long> {
}
