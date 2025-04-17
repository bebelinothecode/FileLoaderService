package com.IsaacDickson.PIACProject.FolderMonitorService.repository;

import com.IsaacDickson.PIACProject.FolderMonitorService.Entity.CDRLogsControlTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ControlTableRepository extends JpaRepository<CDRLogsControlTable, Long> {
}
