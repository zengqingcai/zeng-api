package com.ectrip.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ectrip.ajax.AjaxResult;
import com.ectrip.mapper.*;
import com.ectrip.model.*;
import com.ectrip.service.IEmpProviderService;
import com.ectrip.service.IEmployeesService;
import com.ectrip.utils.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

@Service
public class EmpProviderService implements IEmpProviderService {

    @Value("${empkey}")
    private String secretKey;

    @Autowired
    private EmployeesMapper employeesMapper;

    @Autowired
    private RsourcestabMapper rsourcestabMapper;

    @Autowired
    private SyslogMapper syslogMapper;

    @Autowired
    private Sysparv5Mapper sysparv5Mapper;

    @Autowired
    private EmppushMapper emppushMapper;

    @Autowired
    private IEmployeesService employeesService;



    @Override
    @Transactional
    public String saveEmployees(String requestStr,Integer sources) throws Exception{
        //验签操作
        Map<String,String> result = validateSign(requestStr);
        if(result != null){
            return getResult(result,"saveEmp");
        }
        DataTrans dataTrans = JSON.parseObject(requestStr,DataTrans.class);
        //加密后的数据
        String data = dataTrans.getData();
        //带&的有顺序的参数字符串
        String prestr = DataUtils.decodeData(data);
        //通过url获取json字符串
        String jsonString = DataUtils.getJsonStrByQueryUrl(prestr);
        Employees employees = JSON.parseObject(jsonString,Employees.class);
        if(employees == null)
            return null;
        //判断上级用户的判断
        result = validateSEmployee(employees);
        if(result!=null){
            return getResult(result,dataTrans.getMethod());
        }
        //默认设置用户状态：有效
        employees.setByisuse(1);
        //设置注册来源
        employees.setSources(""+sources);
        employees.setDtmakedate(DateUtils.getTodayStr());
       //employees.setCompanycode(getRandom(employees.getEmployeeid()));
        //employees.setIcompanyinfoid(employeesMapper.getIcompanyinfoid());
        employees.setIcompanyinfoid(1);//写死的企业id
        result = validateEmployee(employees);
        if(result != null){
            return getResult(result,dataTrans.getMethod());
        }
        String password = new Encrypt().passwordEncrypt(employees.getPassword());
        employees.setPassword(password);
        result= new HashMap<>();
        Integer i = employeesService.insertSelective(employees);
        //返回值
        if(i==1) {
            //日志表的使用
            Syslog syslog = new Syslog();
            syslog.setStlg(getLogTypeCode("用户注册"));
            String who = sources==1?"saas":(sources==2?"支付桥":"整合营销");
            syslog.setBrief(who+"用户注册。。");
            syslog.setNote("用户:"+employees.getEmpid()+"注册成功！");
            syslog.setLogdatetime(DateUtils.getTodayStr());
            syslogMapper.insertSelective(syslog);
            result.put("msg","添加成功");
            result.put("code","0000");
        }else {
            result.put("msg","添加失败");
            result.put("code","0001");
        }
        return getResult(result,dataTrans.getMethod());
    }


    @Override
    public AjaxResult saveFlatEmployees(Employees employees,Integer currentEmployeesId) throws Exception {
        Map<String,String> result;
        //默认设置用户状态：需审核
        employees.setByisuse(3);
        //设置注册来源平台
        employees.setSources("0");
        employees.setDtmakedate(DateUtils.getTodayStr());
        if(StringUtils.isBlank(employees.getCompanyname())){
            return new AjaxResult(false,"-1","企业名称不能为空");
        }
        employees.setCompanycode(employeesService.getCompanyCodeByName(employees.getCompanyname()));
        employees.setIcompanyinfoid(employeesService.getIcompanyinfoid());//序列号
        result = validateEmployee(employees);
        if(result != null){
            return new AjaxResult(false,result.get("code"),result.get("msg"));
        }
        result= new HashMap<>();
        Encrypt encrypt = new Encrypt();
        String password = encrypt.passwordEncrypt(employees.getPassword());
        employees.setPassword(password);
        Integer i = employeesService.insertSelective(employees);
        //返回值
        if(i==1) {
            //日志表的使用
            Syslog syslog = new Syslog();
            syslog.setEmployeeid(currentEmployeesId);
            syslog.setStlg(getLogTypeCode("用户注册"));
            syslog.setBrief("注册用户姓名"+employees.getEmpname());
            syslog.setNote("用户:"+employees.getEmpid()+"注册成功！");
            syslog.setLogdatetime(DateUtils.getTodayStr());
            syslogMapper.insertSelective(syslog);
            return new AjaxResult(true,"200","添加成功!");
        }
        return new AjaxResult(true,"400","添加失败!");

    }

    @Override
    public String getEmployees(String requestStr, Integer sources) throws Exception {
        Map<String,String> result = validateSign(requestStr);
        //验签不通过
        if(result != null){
            return getResult(result,"findEmp");
        }
        DataTrans dataTrans = JSON.parseObject(requestStr,DataTrans.class);
        //加密后的数据
        String data = dataTrans.getData();
        //带&的有顺序的参数字符串
        String prestr = DataUtils.decodeData(data);
        //通过url获取json字符串
        String jsonString = DataUtils.getJsonStrByQueryUrl(prestr);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String empid = jsonObject.get("empid")!=null?jsonObject.get("empid").toString():null;
        Employees employees1 = employeesMapper.getEmployeesByEmpId(empid);
        String json = JSON.toJSONString(employees1);
        result= new HashMap<>();
        result.put("msg","查找成功");
        result.put("code","0000");
        result.put("data",json);
        return getResult(result,dataTrans.getMethod());
    }

    @Override
    @Transactional
    public AjaxResult reviewEmp(JSONObject object,Employees employees) {
        boolean auditType=object.getBoolean("auditType");
        String shnote=object.getString("shnote");
        String sourcenames=object.getString("sourcenames");
        Integer employeeid=object.getInteger("employeeid");
        //审核说明不能为空
        if(StringUtils.isBlank(shnote)){
            return AjaxResult.error("审核说明不能为空","400");
        }
        //当类型为审核通过时,至少选择一种系统
        if(StringUtils.isBlank(sourcenames)&&auditType){
            return AjaxResult.error("至少选择一种系统","400");
        }
        //查询注册来源是否是集成用户
        Employees oldEmp = employeesMapper.getEmployees(employeeid);
        if(!"0".equals(oldEmp.getSources())){
            return AjaxResult.error("用户注册来源不是集成用户，不需要审核","400");
        }
        // 0禁用 1有效 2黑名单 3需审核
        Integer byisuse = oldEmp.getByisuse();
        if(byisuse == 0||byisuse == 1||byisuse == 2)
            return AjaxResult.error("当前用户状态不可审核","400");
        //TODO 登陆人id
        oldEmp.setShempid(employees.getEmployeeid());
        oldEmp.setEmployeeid(employeeid);
        oldEmp.setShnote(shnote);
        //日志表
        String msg="";
        Syslog syslog = new Syslog();
        syslog.setEmployeeid(employees.getEmployeeid());
        syslog.setStlg(getLogTypeCode("用户审核"));
        syslog.setBrief("审核用户:"+oldEmp.getEmployeeid());
        syslog.setLogdatetime(DateUtils.getTodayStr());
        if(auditType){
            //审核通过后 将用户状态改为有效
            oldEmp.setByisuse(1);
            //审核通过后 将用户审核状态改为已审核
            oldEmp.setShbyisuse(1);
            for(String sourcename:sourcenames.split(",")){
                Rsourcestab rsourcestab=new Rsourcestab();
                rsourcestab.setEmployeeid(employeeid);//添加系统
                rsourcestab.setByisuse(1);//系统状态默认为启用
                rsourcestab.setSourcename(sourcename);
                rsourcestab.setDtenddate(LocalDate.now().toString());
                rsourcestab.setDtmakedate(DateUtils.getTodayStr());
                rsourcestabMapper.insertSelective(rsourcestab);
            }
            //添加或者修改通用表
            msg="用户审核通过";
            syslog.setNote("用户:"+oldEmp.getEmpid()+"审核通过！");
            //往推送记录表添加数据
            saveEmpPush(sourcenames,employeeid);
        }else{
            //审核不通过后 将用户审核状态改为审核不通过 用户状态不变
            oldEmp.setShbyisuse(2);
            msg="用户审核不通过";
            syslog.setNote("用户:"+oldEmp.getEmpid()+"审核不通过！");
        }
        employeesMapper.editEmployees(oldEmp);
        syslogMapper.insertSelective(syslog);
        return new AjaxResult(true,"200",msg);
    }

    @Override
    public String doLogin(String requestStr) throws Exception {
        //验签操作
        Map<String,String> result = validateSign(requestStr);
        if(result != null){
            return getResult(result,"dologin");
        }
        DataTrans dataTrans = JSON.parseObject(requestStr,DataTrans.class);
        //加密后的数据
        String data = dataTrans.getData();
        //带&的有顺序的参数字符串
        String prestr = DataUtils.decodeData(data);
        //通过url获取json字符串
        String jsonString = DataUtils.getJsonStrByQueryUrl(prestr);
        Employees employees = JSON.parseObject(jsonString,Employees.class);

        if (StringUtils.isBlank(employees.getEmpid()) || StringUtils.isBlank(employees.getPassword())) {
            return getResult(result("输入账号和密码均不能为空!","1000"),dataTrans.getMethod());
        }
        Employees loginEmp = employeesMapper.getEmployeesByEmpId(employees.getEmpid());
        if (loginEmp == null) {
            return getResult(result("该用户不存在请重新输入用户名!","1000"),dataTrans.getMethod());
        }
        if(loginEmp.getByisuse()!=1){
            return getResult(result("该用户状态不是有效状态,请联系管理员!","1000"),dataTrans.getMethod());
        }
        if (loginEmp.getZtimes() <= 5) {
            Encrypt encrypt = new Encrypt();
            String password = encrypt.passwordEncrypt(employees.getPassword());
            if (!password.equals(loginEmp.getPassword())) {
                loginEmp.setZtimes(loginEmp.getZtimes() + 1);
                loginEmp.setDtmakedate(DateUtils.getTodayStr());
                employeesService.editEmployees(loginEmp);
                return getResult(result("密码错误请重新输入!","1000"),dataTrans.getMethod());
            }
        } else {
            return getResult(result("密码输错次数大于5次,请等次日再登陆!","1000"),dataTrans.getMethod());
        }
        //如果是二级用户，要查询上级用户是否有效
        if(loginEmp.getSempid()!=null){
            Map<String,Object> params = new HashMap<>();
            params.put("empid",loginEmp.getSempid());
            params.put("byisuse",1);
            Employees supEmp = employeesMapper.getSupEmpByParams(params);
            if(supEmp==null)
                return getResult(result("上级用户状态不是有效","1000"),dataTrans.getMethod());
        }
        return getResult(result("成功登陆","0000"),dataTrans.getMethod());
    }

    @Override
    public String updatePassword(String requestStr) throws Exception {
        //验签操作
        Map<String,String> result = validateSign(requestStr);
        if(result != null){
            return getResult(result,"updatePassword");
        }
        DataTrans dataTrans = JSON.parseObject(requestStr,DataTrans.class);
        //加密后的数据
        String data = dataTrans.getData();
        //带&的有顺序的参数字符串
        String prestr = DataUtils.decodeData(data);
        //通过url获取json字符串
        String jsonString = DataUtils.getJsonStrByQueryUrl(prestr);
        Employees employees = JSON.parseObject(jsonString,Employees.class);

        String password = "";
        Employees old = employeesMapper.getEmployeesByEmpId(employees.getEmpid());
        if (old == null) {
            return getResult(result("用户不存在","0001"),dataTrans.getMethod());
        } else {
            if (old.getByisuse() != 2 && old.getByisuse() != 3) {
                Encrypt encrypt = new Encrypt();
                //原密码判断
                if(!old.getPassword().equals(encrypt.passwordEncrypt(employees.getOldPassword()))){
                    return getResult(result("输入的原密码不正确","0001"),dataTrans.getMethod());
                }
                //判断登陆密码是否符合规格
                if (StringUtils.isBlank(employees.getPassword())) {
                    return getResult(result("新密码不能为空","0001"),dataTrans.getMethod());
                } else {
                    password = encrypt.passwordEncrypt(employees.getPassword());
                    if (password.equals(old.getPassword())) {
                        return getResult(result("新初始密码与原登陆密码重复","0001"),dataTrans.getMethod());
                    }
                    if (!employees.getQrpassword().equals(employees.getPassword())) {
                        return getResult(result("两次密码输入不相同,请重新确认;","0001"),dataTrans.getMethod());
                    }
                    Pattern p = Pattern.compile("^[a-zA-Z0-9]{8,25}$");
                    boolean b = p.matcher(employees.getPassword()).matches();
                    if (!b) {
                        return getResult(result("新初始密码输入有误，请输入[8-25]数字、字母(大小写不限)组成的密码;","0001"),dataTrans.getMethod());
                    }
                    if (employees.getPassword().length() > 25) {
                        return getResult(result("新初始密码长度不能超过25","0001"),dataTrans.getMethod());
                    }
                }
            } else {
                if (old.getByisuse() == 2) {
                    return getResult(result("该用户是黑名单,不能进行此操作;","0001"),dataTrans.getMethod());
                } else if (old.getByisuse() == 3) {
                    return getResult(result("该用户需审核后才进行操作;","0001"),dataTrans.getMethod());
                }
            }
        }

        old.setPassword(password);
        employeesMapper.editEmployees(old);
        Syslog syslog = new Syslog();
        syslog.setStlg("0005");
        syslog.setBrief("用户：" + old.getEmployeeid());
        syslog.setNote("初始化用户：" + old.getEmpid()+"密码");
        syslog.setLogdatetime(DateUtils.getTodayStr());
        syslogMapper.insertSelective(syslog);
        return getResult(result("用户修改密码成功;","0000"),dataTrans.getMethod());

    }

    //往推送表上添加数据
    private void saveEmpPush(String sourcenames,Integer employeeid){
        String[] strings = sourcenames.split(",");
        for(String s:strings){
            Emppush emppush = new Emppush();
            emppush.setEmployeeid(employeeid);
            emppush.setDtmakedate(DateUtils.getTodayStr());
            if(s.equals("saas"))
                emppush.setSource(1);
            if(s.equals("zfq"))
                emppush.setSource(2);
            if(s.equals("zhyx"))
                emppush.setSource(3);
            emppush.setByisuse(1);
            emppushMapper.insertSelective(emppush);
        }

    }


    //返回结果
    private String getResult(Map<String,String> result,String mothod){
        DataTrans dataTrans = new DataTrans();
        String prestr = DataUtils.buildSignString(result);
        String sign = DataUtils.getSign(secretKey,prestr,mothod);
        dataTrans.setData(DataUtils.encodeData(prestr));
        dataTrans.setMethod(mothod);
        dataTrans.setSign(sign);
        return JSON.toJSONString(dataTrans);
    }


    //验签
    private Map<String,String> validateSign(String requestStr) throws Exception{
        DataTrans dataTrans = JSON.parseObject(requestStr,DataTrans.class);
        if(StringUtils.isBlank(dataTrans.getSign())
                || StringUtils.isBlank(dataTrans.getData())
                || StringUtils.isBlank(dataTrans.getMethod())){
            return result("验签不通过！参数不能为空","-1");
        }
        //加密后的数据
        String data = dataTrans.getData();
        //带&的有顺序的参数字符串
        String prestr = DataUtils.decodeData(data);
        //标签
        String signature = dataTrans.getSign();
        //验签
        if(DataUtils.verifySign(signature,secretKey,prestr,dataTrans.method)!=null) {
            return result("验签不通过！","-1");
        }
        return null;
    }

    //校验上级账号是否合法
    private Map<String,String> validateSEmployee(Employees employees){
        //查找是否存在这个empid
        if(StringUtils.isBlank(employees.getSempid())){
            return result("父级账号不能为空！","1000");
        }
        Employees employees1 = employeesMapper.getEmployeesByEmpId(employees.getSempid());
        if(employees1 == null){
            return result("这个父级账号不存在！","1000");
        }
        return null;
    }


    //校验数据
    private Map<String,String> validateEmployee(Employees employees){
        //empid的判断空值 唯一判断
        if(StringUtils.isBlank(employees.getEmpid())){
            return result("账号不能为空！","1000");
        }
        if(employeesMapper.getEmployeesByEmpId(employees.getEmpid())!=null){
            return result("账号不能重复！","1001");
        }
        if(StringUtils.isBlank(employees.getPassword())){
            return result("密码不能为空！","1000");
        }
        if(StringUtils.isBlank(employees.getEmpname())){
            return result("用户姓名不能为空","1000");
        }else {
            if (employees.getEmpname().length() > 50) {
                return result("用户姓名长度不能大于50","1000");
            }
        }
        if(employees.getEmptype()==null){
            return result("用户类型 不能为空","1000");
        }
        if(StringUtils.isBlank(employees.getCompanycode())){
            return result("企业编号 不能为空","1000");
        }
        if(StringUtils.isBlank(employees.getCompanyname())){
            return result("企业名称不能为空","1000");
        }
        if(StringUtils.isBlank(employees.getMobile())){
            return result("手机号不能为空","1000");
        }else {
            Pattern p = Pattern.compile("^ \\d{3}-\\d{8}|\\d{4}-\\d{7}$");
            Pattern p1 = Pattern.compile("^1[1-9][0-9]\\d{8}$");
            boolean b = p.matcher(employees.getMobile()).matches();
            boolean c = p1.matcher(employees.getMobile()).matches();
            //联系电话格式不正确，如13288888888或023-55328751或0755-2423354
            if (!b && !c) {
                return result("联系电话格式不正确;","1000");
            }
        }
        //判断电子邮箱
        if (!StringUtils.isBlank(employees.getEmail())) {
            String check = "^([\\w-\\.]+)@[\\w-.]+(\\.?[a-zA-Z]{2,4}$)";
            Pattern regex = Pattern.compile(check);
            boolean b = regex.matcher(employees.getEmail()).matches();
            if (!b) {
                return result("邮箱地址格式错误;","1000");
            }
            if (employees.getEmail().length() > 25) {
                return result("邮箱地址长度不能大于25;","1000");
            }
        }
        //判断备注长度
        if (!StringUtils.isBlank(employees.getSznote())) {
            if (employees.getSznote().length() > 500) {
                return result("备注长度不能大于500;","1000");
            }
        }
        return null;
    }


    private Map<String,String> result(String msg,String code){
        Map<String,String> result = new HashMap<>();
        result.put("msg",msg);
        result.put("code",code);
        return result;
    }


    private String getRandom(){
        int length = 8; //流水号的16位随机数
        String base = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        String code = sb.toString();
        //判断是否又重复的企业编号
        Map<String,Object> params = new HashMap<>();
        params.put("companycode",code);
        params.put("sources","0");//集成用户才查询
       if((employeesMapper.getByParams(params)).size() != 0){
            return getRandom();
        }
        return code;
    }

    private String getLogTypeCode(String pmva){
        List<Sysparv5> list = sysparv5Mapper.getSysList("STLG");
        for(Sysparv5 sysparv5:list){
            if(pmva.equals(sysparv5.getPmva())){
                return sysparv5.getPmcd();
            }
        }
        return "";

    }
}
