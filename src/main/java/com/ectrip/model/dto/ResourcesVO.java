package com.ectrip.model.dto;

public class ResourcesVO {

    private Integer rid;

    private Integer employeeid;

    private Integer semployeeid;

    //用户名
    private String empid;

    //中文名称
    private String empname;

    //可用系统
    private String sourcename;

    //用户状态（启用，禁用）
    private String byisuse;

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

    public Integer getSemployeeid() {
        return semployeeid;
    }

    public void setSemployeeid(Integer semployeeid) {
        this.semployeeid = semployeeid;
    }

    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid;
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname;
    }

    public String getSourcename() {
        return sourcename;
    }

    public void setSourcename(String sourcename) {
        this.sourcename = sourcename;
    }

    public String getByisuse() {
        return byisuse;
    }

    public void setByisuse(String byisuse) {
        this.byisuse = byisuse;
    }
}
