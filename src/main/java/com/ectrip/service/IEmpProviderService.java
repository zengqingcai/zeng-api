package com.ectrip.service;


import com.alibaba.fastjson.JSONObject;
import com.ectrip.ajax.AjaxResult;
import com.ectrip.model.Employees;

/**
 * 对外提供的接口
 */
public interface IEmpProviderService {


    /**
     * 通过非平台渠道的添加
     * @param requestStr
     * @param sources 渠道
     * @return
     * @throws Exception
     */
    String saveEmployees(String requestStr, Integer sources) throws Exception;

    /**
     * 通过平台渠道的添加
     * @param employees
     * @return
     * @throws Exception
     */
    AjaxResult saveFlatEmployees(Employees employees,Integer currentEmployeesId) throws Exception;

    /**
     * 通过用户名 empid获取用户信息
     * @param requestStr
     * @param sources
     * @return
     * @throws Exception
     */
    String getEmployees(String requestStr, Integer sources) throws Exception;

    /**
     * 集成用户审核
     */
    AjaxResult reviewEmp(JSONObject object,Employees employees);



    String doLogin(String requestStr) throws Exception;


    String updatePassword(String requestStr) throws Exception;


}
