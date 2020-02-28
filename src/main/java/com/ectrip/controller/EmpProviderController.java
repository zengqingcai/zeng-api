package com.ectrip.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ectrip.ajax.AjaxResult;
import com.ectrip.config.UserContext;
import com.ectrip.model.Employees;
import com.ectrip.service.IEmpProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 对外提供接口
 */
@RestController
@RequestMapping("/empApi")
public class EmpProviderController {

    @Autowired
    private IEmpProviderService empProviderService;



    /**
     * 通过saas注册的用户
     * 通过支付桥注册的用户
     * 通过整合营销注册的用户
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveEmp/{sources}",method = RequestMethod.POST)
    public String saveEmpBySaas(@RequestBody String request, @PathVariable("sources") String sources)throws Exception{
        if("saas".equals(sources))
            return empProviderService.saveEmployees(request,1);
        if("pay".equals(sources))
            return empProviderService.saveEmployees(request,2);
        if("market".equals(sources))
            return empProviderService.saveEmployees(request,3);
        return null;

    }

    /**
     * 通过saas查找
     * 通过支付桥查找
     * 通过整合营销查找
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "findEmp/{sources}",method = RequestMethod.POST)
    public String findEmpBySaas(@RequestBody String request, @PathVariable("sources") String sources)throws Exception{

        if("saas".equals(sources))
            return empProviderService.getEmployees(request,1);
        if("pay".equals(sources))
            return empProviderService.getEmployees(request,2);
        if("market".equals(sources))
            return empProviderService.getEmployees(request,3);
        return null;
    }


    @RequestMapping("/dologin")
    public String dologin(@RequestBody String requestStr) throws Exception{
        return empProviderService.doLogin(requestStr);
    }


    @RequestMapping("/updatePassword")
    public String updatePassword(@RequestBody String requestStr) throws Exception{
        return empProviderService.updatePassword(requestStr);
    }

    /**
     * 通过集成平台注册的用户
     * @param employees
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "saveEmpByPlat",method = RequestMethod.POST)
    public Object saveEmpByPlat(@RequestBody Employees employees)throws Exception{
        if(employees == null)
            return null;
        Employees currentEmp = UserContext.getEmployees();
        return empProviderService.saveFlatEmployees(employees,currentEmp.getEmployeeid());
    }


    /**
     * 用户审核
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/reviewEmp")
    public AjaxResult reviewEmp(@RequestBody String params) {
        Employees employees=UserContext.getEmployees();
        if(employees==null){
            return AjaxResult.error("未登陆用户,无法进行此操作","500");
        }
        JSONObject object= JSON.parseObject(params);
        try{
            return empProviderService.reviewEmp(object,employees);
        }catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("服务器错误","500");
        }
    }


}
