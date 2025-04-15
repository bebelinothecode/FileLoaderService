package com.IsaacDickson.PIACProject;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "call_detail_records")
public class CallDetailRecords {
    @Id
    @GeneratedValue(strategy = IDENTITY)

    @Column(name = "ID", nullable = false, length = 100)
    private int ID;

    @Column(name = "RECORD_DATE", nullable = false, length = 100)
    private LocalDate Record_Date;

    @Column(name = "L_SPC", nullable = true, length = 100)
    private int L_SPC;

    @Column(name = "L_SSN", nullable = true, length = 100)
    private int L_SSN;

    @Column(name = "L_RI", nullable = true, length = 100)
    private int L_RI;

    @Column(name = "L_GT_I", nullable = true, length = 100)
    private int L_GT_I;

    @Column(name = "L_GT_DIGITS", nullable = true, length = 100)
    private String L_GT_DIGITS;

    @Column(name = "R_SPC", nullable = true, length = 100)
    private int R_SPC;

    @Column(name = "R_SSN", nullable = true, length = 100)
    private int R_SSN;

    @Column(name = "R_RI", nullable = true, length = 100)
    private int R_RI;

    @Column(name = "R_GT_I", nullable = true, length = 100)
    private int R_GT_I;

    @Column(name = "R_GT_DIGITS", nullable = true, length = 100)
    private String R_GT_DIGITS;

    @Column(name = "SERVICE_CODE", nullable = true, length = 100)
    private String SERVICE_CODE;

    @Column(name = "OR_NATURE", nullable = true, length = 100)
    private int OR_NATURE;

    @Column(name = "OR_PLAN", nullable = true, length = 100)
    private int OR_PLAN;

    @Column(name = "OR_DIGITS", nullable = true, length = 100)
    private String OR_DIGITS;

    @Column(name = "DE_NATURE", nullable = true, length = 100)
    private int DE_NATURE;

    @Column(name = "DE_PLAN", nullable = true, length = 100)
    private int DE_PLAN;

    @Column(name = "DE_DIGITS", nullable = true, length = 100)
    private String DE_DIGITS;

    @Column(name = "ISDN_NATURE", nullable = true, length = 100)
    private int ISDN_NATURE;

    @Column(name = "ISDN_PLAN", nullable = true, length = 100)
    private int ISDN_PLAN;

    @Column(name = "MSISDN", nullable = true, length = 100)
    private int MSISDN;

    @Column(name = "VLR_NATURE", nullable = true, length = 100)
    private int VLR_NATURE;

    @Column(name = "VLR_PLAN", nullable = true, length = 100)
    private int VLR_PLAN;

    @Column(name = "VLR_DIGITS", nullable = true, length = 100)
    private String VLR_DIGITS;

    @Column(name = "IMSI", nullable = true, length = 100)
    private int IMSI;

    @Column(name = "STATUS", nullable = false, length = 100)
    private String STATUS;

    @Column(name = "TYPE", nullable = false, length = 100)
    private String TYPE;

    @Column(name = "TSTAMP", nullable = false, length = 100)
    private LocalDateTime TSTAMP;

    @Column(name = "LOCAL_DIALOG_ID", nullable = true, length = 100)
    private long LOCAL_DIALOG_ID;

    @Column(name = "REMOTE_DIALOG_ID", nullable = true, length = 100)
    private long REMOTE_DIALOG_ID;

    @Column(name = "DIALOG_DURATION", nullable = true, length = 100)
    private long DIALOG_DURATION;

    @Column(name = "USSD_STRING", nullable = true, length = 100)
    private String USSD_STRING;




    public CallDetailRecords() {
    }

    public CallDetailRecords(LocalDate record_Date, int l_SPC, int l_SSN, int l_RI, int l_GT_I, String l_GT_DIGITS, int r_SPC, int r_SSN, int r_RI, int r_GT_I, String r_GT_DIGITS, String SERVICE_CODE, int OR_NATURE, int OR_PLAN, String OR_DIGITS, int DE_NATURE, int DE_PLAN, String DE_DIGITS, int ISDN_NATURE, int ISDN_PLAN, int MSISDN, int VLR_NATURE, int VLR_PLAN, String VLR_DIGITS, int IMSI, String STATUS, String TYPE, LocalDateTime TSTAMP, long LOCAL_DIALOG_ID, long REMOTE_DIALOG_ID, long DIALOG_DURATION, String USSD_STRING, int ID) {
        Record_Date = record_Date;
        L_SPC = l_SPC;
        L_SSN = l_SSN;
        L_RI = l_RI;
        L_GT_I = l_GT_I;
        L_GT_DIGITS = l_GT_DIGITS;
        R_SPC = r_SPC;
        R_SSN = r_SSN;
        R_RI = r_RI;
        R_GT_I = r_GT_I;
        R_GT_DIGITS = r_GT_DIGITS;
        this.SERVICE_CODE = SERVICE_CODE;
        this.OR_NATURE = OR_NATURE;
        this.OR_PLAN = OR_PLAN;
        this.OR_DIGITS = OR_DIGITS;
        this.DE_NATURE = DE_NATURE;
        this.DE_PLAN = DE_PLAN;
        this.DE_DIGITS = DE_DIGITS;
        this.ISDN_NATURE = ISDN_NATURE;
        this.ISDN_PLAN = ISDN_PLAN;
        this.MSISDN = MSISDN;
        this.VLR_NATURE = VLR_NATURE;
        this.VLR_PLAN = VLR_PLAN;
        this.VLR_DIGITS = VLR_DIGITS;
        this.IMSI = IMSI;
        this.STATUS = STATUS;
        this.TYPE = TYPE;
        this.TSTAMP = TSTAMP;
        this.LOCAL_DIALOG_ID = LOCAL_DIALOG_ID;
        this.REMOTE_DIALOG_ID = REMOTE_DIALOG_ID;
        this.DIALOG_DURATION = DIALOG_DURATION;
        this.USSD_STRING = USSD_STRING;
        this.ID = ID;
    }

    public LocalDate getRecord_Date() {
        return Record_Date;
    }

    public void setRecord_Date(LocalDate record_Date) {
        Record_Date = record_Date;
    }

    public int getL_SPC() {
        return L_SPC;
    }

    public void setL_SPC(int l_SPC) {
        L_SPC = l_SPC;
    }

    public int getL_SSN() {
        return L_SSN;
    }

    public void setL_SSN(int l_SSN) {
        L_SSN = l_SSN;
    }

    public int getL_RI() {
        return L_RI;
    }

    public void setL_RI(int l_RI) {
        L_RI = l_RI;
    }

    public int getL_GT_I() {
        return L_GT_I;
    }

    public void setL_GT_I(int l_GT_I) {
        L_GT_I = l_GT_I;
    }

    public String getL_GT_DIGITS() {
        return L_GT_DIGITS;
    }

    public void setL_GT_DIGITS(String l_GT_DIGITS) {
        L_GT_DIGITS = l_GT_DIGITS;
    }

    public int getR_SPC() {
        return R_SPC;
    }

    public void setR_SPC(int r_SPC) {
        R_SPC = r_SPC;
    }

    public int getR_SSN() {
        return R_SSN;
    }

    public void setR_SSN(int r_SSN) {
        R_SSN = r_SSN;
    }

    public int getR_RI() {
        return R_RI;
    }

    public void setR_RI(int r_RI) {
        R_RI = r_RI;
    }

    public int getR_GT_I() {
        return R_GT_I;
    }

    public void setR_GT_I(int r_GT_I) {
        R_GT_I = r_GT_I;
    }

    public String getR_GT_DIGITS() {
        return R_GT_DIGITS;
    }

    public void setR_GT_DIGITS(String r_GT_DIGITS) {
        R_GT_DIGITS = r_GT_DIGITS;
    }

    public String getSERVICE_CODE() {
        return SERVICE_CODE;
    }

    public void setSERVICE_CODE(String SERVICE_CODE) {
        this.SERVICE_CODE = SERVICE_CODE;
    }

    public int getOR_NATURE() {
        return OR_NATURE;
    }

    public void setOR_NATURE(int OR_NATURE) {
        this.OR_NATURE = OR_NATURE;
    }

    public int getOR_PLAN() {
        return OR_PLAN;
    }

    public void setOR_PLAN(int OR_PLAN) {
        this.OR_PLAN = OR_PLAN;
    }

    public String getOR_DIGITS() {
        return OR_DIGITS;
    }

    public void setOR_DIGITS(String OR_DIGITS) {
        this.OR_DIGITS = OR_DIGITS;
    }

    public int getDE_NATURE() {
        return DE_NATURE;
    }

    public void setDE_NATURE(int DE_NATURE) {
        this.DE_NATURE = DE_NATURE;
    }

    public int getDE_PLAN() {
        return DE_PLAN;
    }

    public void setDE_PLAN(int DE_PLAN) {
        this.DE_PLAN = DE_PLAN;
    }

    public String getDE_DIGITS() {
        return DE_DIGITS;
    }

    public void setDE_DIGITS(String DE_DIGITS) {
        this.DE_DIGITS = DE_DIGITS;
    }

    public int getISDN_NATURE() {
        return ISDN_NATURE;
    }

    public void setISDN_NATURE(int ISDN_NATURE) {
        this.ISDN_NATURE = ISDN_NATURE;
    }

    public int getISDN_PLAN() {
        return ISDN_PLAN;
    }

    public void setISDN_PLAN(int ISDN_PLAN) {
        this.ISDN_PLAN = ISDN_PLAN;
    }

    public int getMSISDN() {
        return MSISDN;
    }

    public void setMSISDN(int MSISDN) {
        this.MSISDN = MSISDN;
    }

    public int getVLR_NATURE() {
        return VLR_NATURE;
    }

    public void setVLR_NATURE(int VLR_NATURE) {
        this.VLR_NATURE = VLR_NATURE;
    }

    public int getVLR_PLAN() {
        return VLR_PLAN;
    }

    public void setVLR_PLAN(int VLR_PLAN) {
        this.VLR_PLAN = VLR_PLAN;
    }

    public String getVLR_DIGITS() {
        return VLR_DIGITS;
    }

    public void setVLR_DIGITS(String VLR_DIGITS) {
        this.VLR_DIGITS = VLR_DIGITS;
    }

    public int getIMSI() {
        return IMSI;
    }

    public void setIMSI(int IMSI) {
        this.IMSI = IMSI;
    }

    public String getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public String getTYPE() {
        return TYPE;
    }

    public void setTYPE(String TYPE) {
        this.TYPE = TYPE;
    }

    public LocalDateTime getTSTAMP() {
        return TSTAMP;
    }

    public void setTSTAMP(LocalDateTime TSTAMP) {
        this.TSTAMP = TSTAMP;
    }

    public long getLOCAL_DIALOG_ID() {
        return LOCAL_DIALOG_ID;
    }

    public void setLOCAL_DIALOG_ID(long LOCAL_DIALOG_ID) {
        this.LOCAL_DIALOG_ID = LOCAL_DIALOG_ID;
    }

    public long getREMOTE_DIALOG_ID() {
        return REMOTE_DIALOG_ID;
    }

    public void setREMOTE_DIALOG_ID(long REMOTE_DIALOG_ID) {
        this.REMOTE_DIALOG_ID = REMOTE_DIALOG_ID;
    }

    public long getDIALOG_DURATION() {
        return DIALOG_DURATION;
    }

    public void setDIALOG_DURATION(long DIALOG_DURATION) {
        this.DIALOG_DURATION = DIALOG_DURATION;
    }

    public String getUSSD_STRING() {
        return USSD_STRING;
    }

    public void setUSSD_STRING(String USSD_STRING) {
        this.USSD_STRING = USSD_STRING;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public String toString() {
        return "CallDetailRecords{" +
                "Record_Date=" + Record_Date +
                ", L_SPC=" + L_SPC +
                ", L_SSN=" + L_SSN +
                ", L_RI=" + L_RI +
                ", L_GT_I=" + L_GT_I +
                ", L_GT_DIGITS='" + L_GT_DIGITS + '\'' +
                ", R_SPC=" + R_SPC +
                ", R_SSN=" + R_SSN +
                ", R_RI=" + R_RI +
                ", R_GT_I=" + R_GT_I +
                ", R_GT_DIGITS='" + R_GT_DIGITS + '\'' +
                ", SERVICE_CODE='" + SERVICE_CODE + '\'' +
                ", OR_NATURE=" + OR_NATURE +
                ", OR_PLAN=" + OR_PLAN +
                ", OR_DIGITS='" + OR_DIGITS + '\'' +
                ", DE_NATURE=" + DE_NATURE +
                ", DE_PLAN=" + DE_PLAN +
                ", DE_DIGITS='" + DE_DIGITS + '\'' +
                ", ISDN_NATURE=" + ISDN_NATURE +
                ", ISDN_PLAN=" + ISDN_PLAN +
                ", MSISDN=" + MSISDN +
                ", VLR_NATURE=" + VLR_NATURE +
                ", VLR_PLAN=" + VLR_PLAN +
                ", VLR_DIGITS='" + VLR_DIGITS + '\'' +
                ", IMSI=" + IMSI +
                ", STATUS='" + STATUS + '\'' +
                ", TYPE='" + TYPE + '\'' +
                ", TSTAMP=" + TSTAMP +
                ", LOCAL_DIALOG_ID=" + LOCAL_DIALOG_ID +
                ", REMOTE_DIALOG_ID=" + REMOTE_DIALOG_ID +
                ", DIALOG_DURATION=" + DIALOG_DURATION +
                ", USSD_STRING='" + USSD_STRING + '\'' +
                ", ID=" + ID +
                '}';
    }
}
