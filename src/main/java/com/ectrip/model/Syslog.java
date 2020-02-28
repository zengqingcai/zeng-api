package com.ectrip.model;

public class Syslog {
    private Integer logid;

    private Integer employeeid;

    private String stlg;

    private String brief;

    private String note;

    private String logdatetime;

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public String getStlg() {
        return stlg;
    }

    public void setStlg(String stlg) {
        this.stlg = stlg == null ? null : stlg.trim();
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief == null ? null : brief.trim();
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note == null ? null : note.trim();
    }

    public String getLogdatetime() {
        return logdatetime;
    }

    public void setLogdatetime(String logdatetime) {
        this.logdatetime = logdatetime == null ? null : logdatetime.trim();
    }
}