package com.ectrip.mapper;

import com.ectrip.model.Employees;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface EmployeesMapper {
    int insert(Employees record);

    int insertSelective(Employees record);

    //根据用户ID删除用户
    int delEmployees(Integer employeeid);

    //修改用户
    int editEmployees(Employees employees);

    //根据用户ID读取用户信息
    Employees getEmployees(Integer employeeid);


    //根据条件获取用户
    List<Employees> getEmployeesByPage(Employees employees);

    //根据用户名读取用户信息
    Employees getEmployeesByEmpId(String empid);

    //根据参数获取对象
    List<Employees> getByParams(Map<String,Object> params);


    //根据参数修改
    int updateSelective(Employees employees);

    Integer getIcompanyinfoid();

    //根据可用系统用户获取用户信息
    List<Employees> getEmpBySource(Map<String,Object> params);

    //根据公司名称获取公司编号
    String getCompanyCodeByName(String companyname);

    List<Employees> getEmpBySourceTwo(Map<String,Object> params);

    Employees getSupEmpByParams(Map<String,Object> params);

}