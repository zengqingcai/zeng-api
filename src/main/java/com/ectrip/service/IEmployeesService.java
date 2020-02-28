package com.ectrip.service;

import com.ectrip.model.Employees;

import java.util.List;
import java.util.Map;

public interface IEmployeesService {
    //新增用户
    void addEmployess(Employees employees);

    //根据用户ID删除用户
    void delEmployees(Integer employeeid);

    //修改用户
    Object editEmployees(Employees employees);

    //用户密码初始化
    Object editEmpPassWord(Employees employees);

    //禁用用户
    Object prohibitEmployees(Employees employees);

    //将用户加入黑名单
    Object blacklistEmployees(Employees employees);

    //取消用户黑名单
    Object cancelBlacklist(Employees employees);

    //根据用户ID读取用户信息
    Employees getEmployees(Integer employeeid);

    //根据条件分页获取用户
    List<Employees> getEmployeesByPage(Employees employees);

    //根据用户名获取用户
    Employees getEmployess(String empid);

    Object saveResource(Map paramMap);

    List<Employees> getEmpByParams(Map<String,Object> params);

    String getCompanyCodeByName(String companyname);

    List<Employees> getEmpByParamsTwo(Map<String,Object> params);


    Integer getIcompanyinfoid();


    int insertSelective(Employees record);


    //获取分页对象
    Map<String,Object> getEmpByPage(Employees employees);

}
