package com.ectrip.model;

public class Rsourcestab {
    private Integer rid;

    private Integer employeeid;

    private Integer byisuse;

    private String sourcename;

    private String dtenddate;

    private String dtmakedate;
    //非数据库参数
    private String szsourcename;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }

    public Integer getByisuse() {
        return byisuse;
    }

    public void setByisuse(Integer byisuse) {
        this.byisuse = byisuse;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename == null ? null : sourcename.trim();
    }

    public String getDtenddate() {
        return dtenddate;
    }

    public void setDtenddate(String dtenddate) {
        this.dtenddate = dtenddate == null ? null : dtenddate.trim();
    }

    public String getDtmakedate() {
        return dtmakedate;
    }

    public void setDtmakedate(String dtmakedate) {
        this.dtmakedate = dtmakedate == null ? null : dtmakedate.trim();
    }

    public String getSzsourcename() {
        return szsourcename;
    }

    public void setSzsourcename(String szsourcename) {
        this.szsourcename = szsourcename;
    }
}