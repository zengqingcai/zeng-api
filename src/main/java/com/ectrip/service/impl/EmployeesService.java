package com.ectrip.service.impl;


import com.ectrip.ajax.AjaxResult;
import com.ectrip.ajax.CommonPage;
import com.ectrip.mapper.EmployeesMapper;
import com.ectrip.mapper.EmppushMapper;
import com.ectrip.mapper.RsourcestabMapper;
import com.ectrip.mapper.SyslogMapper;
import com.ectrip.model.Employees;
import com.ectrip.model.Emppush;
import com.ectrip.model.Rsourcestab;
import com.ectrip.model.Syslog;
import com.ectrip.service.IEmployeesService;
import com.ectrip.utils.CharacterUtils;
import com.ectrip.utils.CommonUtil;
import com.ectrip.utils.DateUtils;
import com.ectrip.utils.Encrypt;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

@Service
public class EmployeesService implements IEmployeesService {

    @Autowired
    private EmployeesMapper employeesMapper;

    @Autowired
    private SyslogMapper syslogMapper;

    @Autowired
    private RsourcestabMapper rsourcestabMapper;

    @Autowired
    private EmppushMapper emppushMapper;

    //新增用户
    @Override
    public void addEmployess(Employees employees) {
        employeesMapper.insert(employees);
    }

    //根据用户ID删除用户
    @Override
    public void delEmployees(Integer employeeid) {
        Employees employees = employeesMapper.getEmployees(employeeid);
        Syslog syslog = new Syslog();
        syslog.setEmployeeid(1);
        syslog.setStlg("0021");
        syslog.setBrief("用户：" + employees.getEmployeeid());
        syslog.setNote("删除用户：" + employees.getEmpid());
        syslog.setLogdatetime(DateUtils.getTodayStr());
        employeesMapper.delEmployees(employeeid);
        syslogMapper.insertSelective(syslog);
    }

    //修改用户
    @Override
    public Object editEmployees(Employees employees) {
        //获取当前登陆人
        Employees loginEmp= CommonUtil.getLoGinEmployees();
        if(loginEmp==null){
            return AjaxResult.error("未登陆用户无法进行此操作!", "400");
        }
        StringBuffer error = new StringBuffer("");
        Employees old = null;
        if (employees == null || employees.getEmployeeid() == null) {
            error.append("请选择一个用户修改;");
        } else {
            old = employeesMapper.getEmployees(employees.getEmployeeid());
            if (old == null) {
                error.append("用户不存在;");
            } else {
                if (old.getByisuse() != 2 && old.getByisuse() != 3) {
                    //判断员工名
                    if (StringUtils.isBlank(employees.getEmpname())) {
                        error.append("用户姓名不能为空;");
                    } else {
                        if (employees.getEmpname().length() > 50) {
                            error.append("用户姓名长度不能大于50;");
                        }
                    }
                    //判断联系电话
                    if (StringUtils.isBlank(employees.getMobile())) {
                        error.append("联系电话不能为空;");
                    } else {
                        Pattern p = Pattern.compile("^ \\d{3}-\\d{8}|\\d{4}-\\d{7}$");
                        Pattern p1 = Pattern.compile("^1[1-9][0-9]\\d{8}$");
                        boolean b = p.matcher(employees.getMobile()).matches();
                        boolean c = p1.matcher(employees.getMobile()).matches();
                        //联系电话格式不正确，如13288888888或023-55328751或0755-2423354
                        if (!b && !c) {
                            error.append("联系电话格式不正确;");
                        }
                    }
                    //判断电子邮箱
                    if (!StringUtils.isBlank(employees.getEmail())) {
                        String check = "^([\\w-\\.]+)@[\\w-.]+(\\.?[a-zA-Z]{2,4}$)";
                        Pattern regex = Pattern.compile(check);
                        boolean b = regex.matcher(employees.getEmail()).matches();
                        if (!b) {
                            error.append("邮箱地址格式错误;");
                        }
                        if (employees.getEmail().length() > 25) {
                            error.append("邮箱地址长度不能大于25;");
                        }
                    }
                    //判断备注长度
                    if (!StringUtils.isBlank(employees.getSznote())) {
                        if (employees.getEmail().length() > 500) {
                            error.append("备注长度不能大于500;");
                        }
                    }
                } else {
                    if (old.getByisuse() == 2) {
                        error.append("该用户是黑名单,不能进行此操作;");
                    } else if (old.getByisuse() == 3) {
                        error.append("该用户需审核后才进行操作;");
                    }
                }
            }
        }
        if (!StringUtils.isBlank(error.toString())) {
            return AjaxResult.error(error.toString().substring(0, error.length() - 1), "400");
        } else {
            old.setEmpname(employees.getEmpname());
            old.setMobile(employees.getMobile());
            old.setEmail(employees.getEmail());
            old.setByisuse(employees.getByisuse());
            old.setSznote(employees.getSznote());
            employeesMapper.editEmployees(old);
            Syslog syslog = new Syslog();
            syslog.setEmployeeid(loginEmp.getEmployeeid());
            syslog.setStlg("0003");
            syslog.setBrief("用户：" + old.getEmployeeid());
            syslog.setNote("修改用户：" + old.getEmpid()+"信息");
            syslog.setLogdatetime(DateUtils.getTodayStr());
            syslogMapper.insertSelective(syslog);
            return new AjaxResult(true, "200", "用户修改成功");
        }
    }

    @Override
    public Object editEmpPassWord(Employees employees) {
        //获取当前登陆人
        Employees loginEmp= CommonUtil.getLoGinEmployees();
        if(loginEmp==null){
            return AjaxResult.error("未登陆用户无法进行此操作!", "400");
        }
        StringBuffer error = new StringBuffer("");
        String password = "";
        Employees old = null;
        if (employees == null || employees.getEmployeeid() == null) {
            error.append("请选择一个用户;");
        } else {
            old = employeesMapper.getEmployees(employees.getEmployeeid());
            if (old == null) {
                error.append("用户不存在;");
            } else {
                if (old.getByisuse() != 2 && old.getByisuse() != 3) {
                    Encrypt encrypt = new Encrypt();
                    //判断登陆密码是否符合规格
                    if (StringUtils.isBlank(employees.getPassword())) {
                        error.append("新初始密码不能为空;");
                    } else {
                        password = encrypt.passwordEncrypt(employees.getPassword());
                        if (password.equals(old.getPassword())) {
                            error.append("新初始密码与原登陆密码重复;");
                        }
                        if (!employees.getQrpassword().equals(employees.getPassword())) {
                            error.append("两次密码输入不相同,请重新确认;");
                        }
                        Pattern p = Pattern.compile("^[a-zA-Z0-9]{8,25}$");
                        boolean b = p.matcher(employees.getPassword()).matches();
                        if (!b) {
                            error.append("新初始密码输入有误，请输入[8-25]数字、字母(大小写不限)组成的密码;");
                        }
                        if (employees.getPassword().length() > 25) {
                            error.append("新初始密码长度不能超过25;");
                        }
                    }
                } else {
                    if (old.getByisuse() == 2) {
                        error.append("该用户是黑名单,不能进行此操作;");
                    } else if (old.getByisuse() == 3) {
                        error.append("该用户需审核后才进行操作;");
                    }
                }
            }
        }
        if (!StringUtils.isBlank(error.toString())) {
            return AjaxResult.error(error.toString().substring(0, error.length() - 1), "400");
        } else {
            old.setPassword(password);
            employeesMapper.editEmployees(old);
            Syslog syslog = new Syslog();
            syslog.setEmployeeid(loginEmp.getEmployeeid());
            syslog.setStlg("0005");
            syslog.setBrief("用户：" + old.getEmployeeid());
            syslog.setNote("初始化用户：" + old.getEmpid()+"密码");
            syslog.setLogdatetime(DateUtils.getTodayStr());
            syslogMapper.insertSelective(syslog);
            return new AjaxResult(true, "200", "用户修改密码成功");
        }
    }

    @Override
    public Object prohibitEmployees(Employees employees) {
        //获取当前登陆人
        Employees loginEmp= CommonUtil.getLoGinEmployees();
        if(loginEmp==null){
            return AjaxResult.error("未登陆用户无法进行此操作!", "400");
        }
        StringBuffer error = new StringBuffer("");
        Employees old = null;
        if (employees == null || employees.getEmployeeid() == null) {
            error.append("请选择一个用户禁用;");
        } else {
            old = employeesMapper.getEmployees(employees.getEmployeeid());
            if (old == null) {
                error.append("用户不存在;");
            } else {
                if (old.getByisuse() == 2) {
                    error.append("该用户是黑名单,不能进行此操作;");
                } else if (old.getByisuse() == 3) {
                    error.append("该用户需审核后才进行操作;");
                }
            }
        }
        if (!StringUtils.isBlank(error.toString())) {
            return AjaxResult.error(error.toString().substring(0, error.length() - 1), "400");
        } else {
            //删除只将用户禁用
            old.setByisuse(0);
            employeesMapper.editEmployees(old);
            Syslog syslog = new Syslog();
            syslog.setEmployeeid(loginEmp.getEmployeeid());
            syslog.setStlg("0004");
            syslog.setBrief("用户：" + old.getEmployeeid());
            syslog.setNote("禁用用户：" + old.getEmpid());
            syslog.setLogdatetime(DateUtils.getTodayStr());
            syslogMapper.insertSelective(syslog);
            return new AjaxResult(true, "200", "用户禁用成功");
        }
    }

    @Override
    public Object blacklistEmployees(Employees employees) {
        //获取当前登陆人
        Employees loginEmp= CommonUtil.getLoGinEmployees();
        if(loginEmp==null){
            return AjaxResult.error("未登陆用户无法进行此操作!", "400");
        }
        StringBuffer error = new StringBuffer("");
        Employees old = null;
        if (employees == null || employees.getEmployeeid() == null) {
            error.append("请选择一个用户加入黑名单;");
        } else {
            old = employeesMapper.getEmployees(employees.getEmployeeid());
            if (old == null) {
                error.append("用户不存在;");
            } else {
                if (old.getByisuse() == 2) {
                    error.append("该用户已经是黑名单用户;");
                } else if (old.getByisuse() == 3) {
                    error.append("该用户需审核后才进行操作;");
                }
            }
        }
        if (!StringUtils.isBlank(error.toString())) {
            return AjaxResult.error(error.toString().substring(0, error.length() - 1), "400");
        } else {
            //加入黑名单
            old.setByisuse(2);
            employeesMapper.editEmployees(old);
            Syslog syslog = new Syslog();
            syslog.setEmployeeid(loginEmp.getEmployeeid());
            syslog.setStlg("0006");
            syslog.setBrief("用户：" + old.getEmployeeid());
            syslog.setNote("将用户：" + old.getEmpid()+"加入黑名单");
            syslog.setLogdatetime(DateUtils.getTodayStr());
            syslogMapper.insertSelective(syslog);
            return new AjaxResult(true, "200", "用户加入黑名单成功");
        }
    }

    @Override
    public Object cancelBlacklist(Employees employees) {
        //获取当前登陆人
        Employees loginEmp= CommonUtil.getLoGinEmployees();
        if(loginEmp==null){
            return AjaxResult.error("未登陆用户无法进行此操作!", "400");
        }
        StringBuffer error = new StringBuffer("");
        Employees old = null;
        if (employees == null || employees.getEmployeeid() == null) {
            error.append("请选择一个黑名单用户;");
        } else {
            old = employeesMapper.getEmployees(employees.getEmployeeid());
            if (old == null) {
                error.append("用户不存在;");
            } else {
                if (old.getByisuse() != 2) {
                    error.append("该用户不是黑名单用户;");
                }
            }
        }
        if (!StringUtils.isBlank(error.toString())) {
            return AjaxResult.error(error.toString().substring(0, error.length() - 1), "400");
        } else {
            //取消黑名单后 默认状态为0 禁用
            old.setByisuse(0);
            employeesMapper.editEmployees(old);
            Syslog syslog = new Syslog();
            syslog.setEmployeeid(loginEmp.getEmployeeid());
            syslog.setStlg("0007");
            syslog.setBrief("用户：" + old.getEmployeeid());
            syslog.setNote("取消用户：" + old.getEmpid()+"黑名单");
            syslog.setLogdatetime(DateUtils.getTodayStr());
            syslogMapper.insertSelective(syslog);
            return new AjaxResult(true, "200", "取消用户黑名单成功");
        }
    }

    //根据用户ID读取用户信息
    @Override
    public Employees getEmployees(Integer employeeid) {
        return employeesMapper.getEmployees(employeeid);
    }

    //根据条件分页获取用户
    @Override
    public List<Employees> getEmployeesByPage(Employees employees) {
        PageHelper.startPage(3, 2);
        List<Employees> employeeslist = employeesMapper.getEmployeesByPage(employees);
        PageInfo<Employees> pageInfo = new PageInfo(employeeslist);
        pageInfo.getPageNum();
        pageInfo.getPages();
        pageInfo.getTotal();
        pageInfo.getPageSize();
        return pageInfo.getList();
    }

    @Override
    public Object saveResource(Map paramMap){
        if(paramMap.get("employeeid")==null){
            return AjaxResult.error("没有选择用户","500");
        }
        Integer employeeid=Integer.parseInt(paramMap.get("employeeid").toString());
        List<Rsourcestab> empResources= rsourcestabMapper.getRsourcesByEmp(employeeid);
        List<Map> resources=(List) paramMap.get("resources");
        if(resources!=null){
            for(Map map:resources){
                String resourcename=map.get("name").toString();
                Integer byisuse=Integer.parseInt(map.get("value").toString());
                for(Rsourcestab rsourcestab:empResources){
                    if(resourcename.equals(rsourcestab.getSourcename())){
                        if(byisuse!=rsourcestab.getByisuse()){
                            rsourcestab.setByisuse(byisuse);
                            rsourcestab.setDtmakedate(DateUtils.getTodayStr());
                            rsourcestabMapper.editResource(rsourcestab);
                            //修改系统状态推送
                            Emppush emppush = new Emppush();
                            emppush.setEmployeeid(employeeid);
                            emppush.setDtmakedate(DateUtils.getTodayStr());
                            if(resourcename.equals("saas"))
                                emppush.setSource(1);
                            if(resourcename.equals("zfq"))
                                emppush.setSource(2);
                            if(resourcename.equals("zhyx"))
                                emppush.setSource(3);
                            emppush.setByisuse(byisuse);
                            emppushMapper.insertSelective(emppush);
                        }
                    }
                }

            }
        }
        return new AjaxResult(true,"200","操作成功");
    }

    //根据用户名获取用户
    @Override
    public Employees getEmployess(String empid) {
        return employeesMapper.getEmployeesByEmpId(empid);
    }

    @Override
    public List<Employees> getEmpByParams(Map<String,Object> params) {
        return employeesMapper.getEmpBySource(params);
    }

    //根据公司名称获取公司编号判断是否生成字符串
    @Override
    public String getCompanyCodeByName(String companyname){
        String companycode="";
        String code=employeesMapper.getCompanyCodeByName(companyname);
        if (code == null){
            companycode= CharacterUtils.getRandomString(8);
        }
        return companycode;
    }

    @Override
    public List<Employees> getEmpByParamsTwo(Map<String,Object> params) {
        return employeesMapper.getEmpBySourceTwo(params);
    }

    @Override
    public Integer getIcompanyinfoid() {
        return employeesMapper.getIcompanyinfoid();
    }

    @Override
    public int insertSelective(Employees record) {
        return employeesMapper.insertSelective(record);
    }

    @Override
    public Map<String, Object> getEmpByPage(Employees employees) {
        Map<String,Object> result = new HashMap<>();
        PageHelper.startPage(employees.getCurrentPage(), employees.getPageSize());
        List<Employees> employeeslist = employeesMapper.getEmployeesByPage(employees);
        PageInfo<Employees> pageInfo = new PageInfo(employeeslist);
        CommonPage commonPage = new CommonPage();
        commonPage.setCurrentPage(pageInfo.getPageNum());
        commonPage.setPages(pageInfo.getPages());
        commonPage.setTotals(pageInfo.getTotal());
        commonPage.setPageSize(pageInfo.getPageSize());
        result.put("page",commonPage);
        result.put("emplist",pageInfo.getList());
        return result;

    }


}
