package com.ectrip.model;

public class Emppush {
    private Integer pid;

    private Integer employeeid;

    private Integer pushstatus;

    private Integer source;

    private String sznote;

    private Integer byisuse;

    private String dtmakedate;

    public Integer getSource() {
        return source;
    }

    public void setSource(Integer source) {
        this.source = source;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getPushstatus() {
        return pushstatus;
    }

    public void setPushstatus(Integer pushstatus) {
        this.pushstatus = pushstatus;
    }

    public String getSznote() {
        return sznote;
    }

    public void setSznote(String sznote) {
        this.sznote = sznote == null ? null : sznote.trim();
    }

    public String getDtmakedate() {
        return dtmakedate;
    }

    public void setDtmakedate(String dtmakedate) {
        this.dtmakedate = dtmakedate == null ? null : dtmakedate.trim();
    }

    public Integer getByisuse() {
        return byisuse;
    }

    public void setByisuse(Integer byisuse) {
        this.byisuse = byisuse;
    }
}