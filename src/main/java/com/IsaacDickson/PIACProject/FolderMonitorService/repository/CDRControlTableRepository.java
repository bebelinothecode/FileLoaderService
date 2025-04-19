package com.IsaacDickson.PIACProject.FolderMonitorService.repository;

import com.IsaacDickson.PIACProject.FolderMonitorService.Entity.CDRLogsControlTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CDRControlTableRepository extends JpaRepository<CDRLogsControlTable, Long> {
}
