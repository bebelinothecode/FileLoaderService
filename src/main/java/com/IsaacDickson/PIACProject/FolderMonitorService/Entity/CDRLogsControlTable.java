package com.IsaacDickson.PIACProject.FolderMonitorService.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Data
@Table(name = "cdr_logs")
@NoArgsConstructor
@AllArgsConstructor
public class CDRLogsControlTable {
    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "ID", nullable = false, length = 100)
    private int ID;

    @Column(name = "file_name", nullable = false, length = 100)
    private String FileName;

    @Column(name = "upload_start_time", nullable = false, length = 100)
    private Timestamp UploadStartTime;

    @Column(name = "upload_end_time", length = 100)
    private Timestamp UploadEndTime;

    @Column(name = "num_of_successfuly_loaded_records", nullable = false, length = 100)
    private int NumOfSuccessfullyLoadedRecords;

    @Column(name = "num_of_failed_records", nullable = false, length = 100)
    private int NumOfFailedRecords;
}
