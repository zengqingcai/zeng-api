package com.ectrip.model.dto;

public class SyslogVO {

    private Integer logid;

    private String empname;

    private String logname;

    private String brief;

    private String note;

    private String logdatetime;

    //非数据库字段
    private Integer currentPage=1;

    private Integer pageSize=10;

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getLogid() {
        return logid;
    }

    public void setLogid(Integer logid) {
        this.logid = logid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getLogname() {
        return logname;
    }

    public void setLogname(String logname) {
        this.logname = logname;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getLogdatetime() {
        return logdatetime;
    }

    public void setLogdatetime(String logdatetime) {
        this.logdatetime = logdatetime;
    }
}
