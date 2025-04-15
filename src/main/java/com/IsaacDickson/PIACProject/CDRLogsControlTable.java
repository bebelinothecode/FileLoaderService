package com.IsaacDickson.PIACProject;

import jakarta.persistence.*;

import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "cdr_logs")

public class CDRLogsControlTable {
    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "ID", nullable = false, length = 100)
    private int ID;

    @Column(name = "file_name", nullable = false, length = 100)
    private String FileName;

    @Column(name = "upload_start_time", nullable = false, length = 100)
    private int UploadStartTime;

    @Column(name = "upload_end_time", nullable = false, length = 100)
    private int UploadEndTime;

    @Column(name = "num_of_successfuly_loaded_records", nullable = false, length = 100)
    private int NumOfSuccessfullyLoadedRecords;

    @Column(name = "num_of_failed_records", nullable = false, length = 100)
    private int NumOfFailedRecords;

    public CDRLogsControlTable() {
    }

    public CDRLogsControlTable(int ID, String fileName, int uploadStartTime, int uploadEndTime, int numOfSuccessfullyLoadedRecords, int numOfFailedRecords) {
        this.ID = ID;
        FileName = fileName;
        UploadStartTime = uploadStartTime;
        UploadEndTime = uploadEndTime;
        NumOfSuccessfullyLoadedRecords = numOfSuccessfullyLoadedRecords;
        NumOfFailedRecords = numOfFailedRecords;
    }

    public CDRLogsControlTable(String fileName, int uploadStartTime, int uploadEndTime, int numOfSuccessfullyLoadedRecords, int numOfFailedRecords) {
        FileName = fileName;
        UploadStartTime = uploadStartTime;
        UploadEndTime = uploadEndTime;
        NumOfSuccessfullyLoadedRecords = numOfSuccessfullyLoadedRecords;
        NumOfFailedRecords = numOfFailedRecords;
    }

    @Override
    public String toString() {
        return "CDRLogsControlTable{" +
                "ID=" + ID +
                ", FileName='" + FileName + '\'' +
                ", UploadStartTime=" + UploadStartTime +
                ", UploadEndTime=" + UploadEndTime +
                ", NumOfSuccessfullyLoadedRecords=" + NumOfSuccessfullyLoadedRecords +
                ", NumOfFailedRecords=" + NumOfFailedRecords +
                '}';
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }

    public int getUploadStartTime() {
        return UploadStartTime;
    }

    public void setUploadStartTime(int uploadStartTime) {
        UploadStartTime = uploadStartTime;
    }

    public int getUploadEndTime() {
        return UploadEndTime;
    }

    public void setUploadEndTime(int uploadEndTime) {
        UploadEndTime = uploadEndTime;
    }

    public int getNumOfSuccessfullyLoadedRecords() {
        return NumOfSuccessfullyLoadedRecords;
    }

    public void setNumOfSuccessfullyLoadedRecords(int numOfSuccessfullyLoadedRecords) {
        NumOfSuccessfullyLoadedRecords = numOfSuccessfullyLoadedRecords;
    }

    public int getNumOfFailedRecords() {
        return NumOfFailedRecords;
    }

    public void setNumOfFailedRecords(int numOfFailedRecords) {
        NumOfFailedRecords = numOfFailedRecords;
    }
}
