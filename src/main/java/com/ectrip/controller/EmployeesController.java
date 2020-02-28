package com.ectrip.controller;

import com.ectrip.ajax.AjaxResult;
import com.ectrip.config.UserContext;
import com.ectrip.model.Empbyisuse;
import com.ectrip.model.Employees;
import com.ectrip.model.Empsources;
import com.ectrip.model.Emptype;
import com.ectrip.service.IEmpProviderService;
import com.ectrip.service.IEmployeesService;
import com.ectrip.service.IRsourcestabService;
import com.ectrip.service.ISysparv5Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/emp")
public class EmployeesController {
    @Resource
    private IEmployeesService employeesService;

    @Resource
    private ISysparv5Service sysparv5Service;

    @Resource
    private IRsourcestabService rsourcestabService;


    @Autowired
    private IEmpProviderService empProviderService;



    @RequestMapping(value = "/empList")
    public String showAllEmployees(ModelMap map,Employees employees) {
        Map<String,Object> result=employeesService.getEmpByPage(employees);
        map.put("emplist", result.get("emplist"));
        map.put("page",result.get("page"));
        map.put("emptypeList",Emptype.allEmpType());
        map.put("sourcesList", Empsources.allEmpsources());
        map.put("byisuseList",Empbyisuse.allEmpByiute());
        return "empAllList";
    }

    @RequestMapping(value = "/empRegister")
    public String empRegister() {
        return "empRegister";
    }


    @RequestMapping(value = "doSaveEmp",method = RequestMethod.POST)
    @ResponseBody
    public Object saveEmpByPlat(@RequestBody Employees employees)throws Exception{
        if(employees == null)
            return null;
        return empProviderService.saveFlatEmployees(employees,null);
    }

    @RequestMapping(value = "/empAdd")
    public String addEmployees() {
        return "empAdd";
    }

    @RequestMapping(value = "/empView/{employeeid}")
    public String viewEmployees(ModelMap map,@PathVariable int employeeid) {
        Employees employees=employeesService.getEmployees(employeeid);
        map.put("Employees", employees);
        map.put("status","view");
        if("0".equals(employees.getSources())) {
            if(employees.getShempid()!=null) {
                Employees shEmp = employeesService.getEmployees(employees.getShempid());
                map.put("shEmpName", shEmp.getEmpname());
            }

        }
        return "empView";
    }

    @RequestMapping(value = "/empEdit/{employeeid}")
    public String editEmployees(ModelMap map,@PathVariable int employeeid) {
        Employees employees=employeesService.getEmployees(employeeid);
        map.put("Employees", employees);
        map.put("status","edit");
        if("0".equals(employees.getSources())) {
            if(employees.getShempid()!=null) {
                Employees shEmp = employeesService.getEmployees(employees.getShempid());
                map.put("shEmpName", shEmp.getEmpname());
            }

        }
        return "empView";
    }


    @RequestMapping(value = "/empAudit/{employeeid}")
    public String auditEmployees(ModelMap map,@PathVariable int employeeid) {
        Employees employees=employeesService.getEmployees(employeeid);
        map.put("Employees", employees);
        map.put("status","audit");
        map.put("systemList",sysparv5Service.getSysList("DJXT"));
        return "empView";
    }

    @RequestMapping(value = "/empPassWordEdit/{employeeid}")
    public String editEmpPassWord(ModelMap map,@PathVariable int employeeid) {
        Employees employees=employeesService.getEmployees(employeeid);
        map.put("Employees", employees);
        return "empPassWord";
    }

    @RequestMapping(value = "/editEmpRsource/{employeeid}")
    public String editEmpRsource(ModelMap map,@PathVariable int employeeid){
        Employees employees=employeesService.getEmployees(employeeid);
        map.put("Employees", employees);
        map.put("sourcenameList",rsourcestabService.getRsourcesByEmp(employeeid));
        return "empResource";
    }

    @ResponseBody
    @RequestMapping(value = "/empUpdate")
    public Object editEmployees(@RequestBody Employees employees) {
        try{
            return employeesService.editEmployees(employees);
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("服务器错误","500");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/empDel")
    public Object delEmployees(@RequestBody Employees employees) {
        try{
            return employeesService.prohibitEmployees(employees);
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("服务器错误","500");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/updatePassWord")
    public Object updatePassWord(@RequestBody Employees employees) {
        try{
            return employeesService.editEmpPassWord(employees);
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("服务器错误","500");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/blackEmp")
    public Object blacklistEmp(@RequestBody Employees employees) {
        try{
            return employeesService.blacklistEmployees(employees);
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("服务器错误","500");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/cancelBlackEmp")
    public Object cancelBlackEmp(@RequestBody Employees employees) {
        try{
            employeesService.cancelBlacklist(employees);
            return new AjaxResult(true,"200","操作成功");
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("服务器错误","500");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/saveResource")
    public Object saveResource(@RequestBody Map paramMap) {
        try{
           return employeesService.saveResource(paramMap);
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("服务器错误","500");
        }
    }


    /**
     * 根据公司名称获取公司编号判断是否生成字符串
     * @param companyname
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/selectCompany")
    public Object selectCompany(String companyname) {
        try{
            return employeesService.getCompanyCodeByName(companyname);
        }catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.error("服务器错误","500");
        }
    }
}
