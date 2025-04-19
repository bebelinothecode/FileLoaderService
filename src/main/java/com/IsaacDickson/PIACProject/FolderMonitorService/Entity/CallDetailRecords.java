package com.IsaacDickson.PIACProject.FolderMonitorService.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "call_detail_records") // your table name here
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CallDetailRecords {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", updatable = false, nullable = false)
    private String id;  // <-- This is your generated ID!

    @Column(name = "RECORD_DATE", nullable = false)
    private String recordDate;

    @Column(name = "L_SPC")
    private Integer lSpc;

    @Column(name = "L_SSN")
    private Integer lSsn;

    @Column(name = "L_RI")
    private Integer lRi;

    @Column(name = "L_GT_I")
    private Integer lGtI;

    @Column(name = "L_GT_DIGITS")
    private String lGtDigits;

    @Column(name = "R_SPC")
    private Integer rSpc;

    @Column(name = "R_SSN")
    private Integer rSsn;

    @Column(name = "R_RI")
    private Integer rRi;

    @Column(name = "R_GT_I")
    private Integer rGtI;

    @Column(name = "R_GT_DIGITS")
    private String rGtDigits;

    @Column(name = "SERVICE_CODE")
    private String serviceCode;

    @Column(name = "OR_NATURE")
    private Integer orNature;

    @Column(name = "OR_PLAN")
    private Integer orPlan;

    @Column(name = "OR_DIGITS")
    private String orDigits;

    @Column(name = "DE_NATURE")
    private Integer deNature;

    @Column(name = "DE_PLAN")
    private Integer dePlan;

    @Column(name = "DE_DIGITS")
    private String deDigits;

    @Column(name = "ISDN_NATURE")
    private Integer isdnNature;

    @Column(name = "ISDN_PLAN")
    private Integer isdnPlan;

    @Column(name = "MSISDN")
    private String msisdn;

    @Column(name = "VLR_NATURE")
    private Integer vlrNature;

    @Column(name = "VLR_PLAN")
    private Integer vlrPlan;

    @Column(name = "VLR_DIGITS")
    private String vlrDigits;

    @Column(name = "IMSI")
    private String imsi;

    @Column(name = "STATUS", nullable = false)
    private String status;

    @Column(name = "TYPE", nullable = false)
    private String type;

    @Column(name = "TSTAMP", nullable = false)
    private String tstamp;

    @Column(name = "LOCAL_DIALOG_ID")
    private Long localDialogId;

    @Column(name = "REMOTE_DIALOG_ID")
    private Long remoteDialogId;

    @Column(name = "DIALOG_DURATION")
    private Long dialogDuration;

    @Column(name = "USSD_STRING")
    private String ussdString;

    @Column(name = "REFERENCE_ID", nullable = false) // The field you already had in DB
    private String referenceID;
}
