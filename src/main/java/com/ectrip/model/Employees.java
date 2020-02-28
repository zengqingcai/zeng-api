package com.ectrip.model;

public class Employees {
    private Integer employeeid;

    private String sempid;

    private String empid;

    private String empname;

    private String password;

    private Integer ztimes;

    private Integer emptype;

    private Integer icompanyinfoid;

    private String companycode;

    private String companyname;

    private String mobile;

    private String email;

    private String addr;

    private Integer byisuse;

    private Integer shempid;

    private Integer shbyisuse;

    private String shnote;

    private String sources;

    private String sznote;

    private String dtmakedate;

    //非数据库字段
    private String oldPassword;//修改密码时的原密码

    //非数据库字段
    private String qrpassword;//提交的确认密码

    private String CAPTCHA;//提交的验证码

    //非数据库字段 分页参数
    private Integer currentPage = 1;

    private Integer pageSize = 10;


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

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getSempid() {
        return sempid;
    }

    public void setSempid(String sempid) {
        this.sempid = sempid;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(Integer employeeid) {
        this.employeeid = employeeid;
    }


    public String getEmpid() {
        return empid;
    }

    public void setEmpid(String empid) {
        this.empid = empid == null ? null : empid.trim();
    }

    public String getEmpname() {
        return empname;
    }

    public void setEmpname(String empname) {
        this.empname = empname == null ? null : empname.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Integer getZtimes() {
        return ztimes;
    }

    public void setZtimes(Integer ztimes) {
        this.ztimes = ztimes;
    }

    public Integer getEmptype() {
        return emptype;
    }

    public void setEmptype(Integer emptype) {
        this.emptype = emptype;
    }

    public Integer getIcompanyinfoid() {
        return icompanyinfoid;
    }

    public void setIcompanyinfoid(Integer icompanyinfoid) {
        this.icompanyinfoid = icompanyinfoid;
    }

    public String getCompanycode() {
        return companycode;
    }

    public void setCompanycode(String companycode) {
        this.companycode = companycode == null ? null : companycode.trim();
    }

    public String getCompanyname() {
        return companyname;
    }

    public void setCompanyname(String companyname) {
        this.companyname = companyname == null ? null : companyname.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr == null ? null : addr.trim();
    }

    public Integer getByisuse() {
        return byisuse;
    }

    public void setByisuse(Integer byisuse) {
        this.byisuse = byisuse;
    }

    public Integer getShempid() {
        return shempid;
    }

    public void setShempid(Integer shempid) {
        this.shempid = shempid;
    }

    public Integer getShbyisuse() {
        return shbyisuse;
    }

    public void setShbyisuse(Integer shbyisuse) {
        this.shbyisuse = shbyisuse;
    }

    public String getShnote() {
        return shnote;
    }

    public void setShnote(String shnote) {
        this.shnote = shnote == null ? null : shnote.trim();
    }

    public String getSources() {
        return sources;
    }

    public void setSources(String sources) {
        this.sources = sources == null ? null : sources.trim();
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

    public String getQrpassword() {
        return qrpassword;
    }

    public void setQrpassword(String qrpassword) {
        this.qrpassword = qrpassword;
    }

    public String getCAPTCHA() {
        return CAPTCHA;
    }

    public void setCAPTCHA(String CAPTCHA) {
        this.CAPTCHA = CAPTCHA;
    }
}