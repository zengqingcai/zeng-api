package com.ectrip.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.ectrip.model.Employees;
import com.ectrip.model.Emppush;
import com.ectrip.service.IEmployeesService;
import com.ectrip.service.IEmppushService;
import com.ectrip.utils.DataTrans;
import com.ectrip.utils.DataUtils;
import com.ectrip.utils.DateUtils;
import com.ectrip.utils.HttpClientUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class JobController {

    @Autowired
    private IEmppushService emppushService;

    @Autowired
    private IEmployeesService employeesService;


    protected final static Type type = new TypeReference<Map<String, String>>() {}.getType();


    @Value("${emp-key:123456}")
    private String secretKey;

    @Value("${saas-domain}")
    private String saasDomain;

    @Value("${payBridge-domain}")
    private String payBridgeDomain;

    @Value("${market-domain}")
    private String markDomain;



    /**
     * 通过审核推送
     * @throws Exception
     */
    @Scheduled(cron = "0/60 * * * * *")
    @RequestMapping(value = "/empApiTest/assa",method = RequestMethod.GET)
    public void pushAssa()throws Exception{
        timer(1,saasDomain + "/data/dataSystemTest.action");//saas推送
    }

    //@Scheduled(cron = "0/30 * * * * *")
    public void pushPay()throws Exception{
        timer(2,"");//支付桥推送
    }

    //@Scheduled(cron = "0/30 * * * * *")
    public void pushMarkt()throws Exception{
        timer(3,"");//整合营销推送
    }

    //推送审核通过的数据   params.put("byisuse",1);
    private void timer(Integer source,String url)throws Exception{
        //查找数据把没有推送的数据查找出来
        Map<String,Object> params = new HashMap<>();
        params.put("source",source);
        params.put("byisuse",1);
        List<Employees> list = employeesService.getEmpByParams(params);
        if(list!=null && list.size()>0) {
            for (Employees employees : list) {
                String method = "push";
                String response = request(employees, url, method);
                DataTrans dataTrans = JSON.parseObject(response, DataTrans.class);
                //加密后的数据
                String data = dataTrans.getData();
                //带&的有顺序的参数字符串
                String prestr = DataUtils.decodeData(data);
                //标签
                String signature = dataTrans.getSign();
                //验签动作
                if (DataUtils.verifySign(signature, secretKey, prestr, dataTrans.method) != null) {
                    System.out.println("验签不通过");
                } else {
                    System.out.println("验证通过");
                    //获取是否对方成功处理数据 修改推送记录表状态
                    String[] strs = prestr.split("&");
                    //查询到数据
                    if ("code=0000".equals(strs[0])) {
                        Emppush emppush = new Emppush();
                        emppush.setPushstatus(1);
                        emppush.setSznote("已推送");
                        emppush.setDtmakedate(DateUtils.getTodayStr());
                        emppush.setEmployeeid(employees.getEmployeeid());
                        emppush.setSource(source);
                        emppush.setByisuse(1);
                        emppushService.updateStatus(emppush);
                    }else {

                    }
                }

            }
        }


    }

    //推送审核未通过的数据   params.put("byisuse",1);
    private void timerTwo(Integer source,String partUrl)throws Exception{
        //查找数据把没有推送的数据查找出来
        Map<String,Object> params = new HashMap<>();
        params.put("source",source);
        params.put("byisuse",1);
        List<Employees> list = employeesService.getEmpByParamsTwo(params);
        if(list!=null && list.size()>0) {
            for (Employees employees : list) {
                String url = "http://localhost:8081/empAuditPass/push/" + partUrl;
                String method = "push";
                String response = request(employees, url, method);
                DataTrans dataTrans = JSON.parseObject(response, DataTrans.class);
                //加密后的数据
                String data = dataTrans.getData();
                //带&的有顺序的参数字符串
                String prestr = DataUtils.decodeData(data);
                //标签
                String signature = dataTrans.getSign();
                //验签动作
                if (DataUtils.verifySign(signature, secretKey, prestr, dataTrans.method) != null) {
                    System.out.println("验签不通过");
                } else {
                    System.out.println("验证通过");
                    //获取是否对方成功处理数据 修改推送记录表状态
                    String[] strs = prestr.split("&");
                    //查询到数据
                    if ("code=0000".equals(strs[0])) {
                        Emppush emppush = new Emppush();
                        emppush.setPushstatus(1);
                        emppush.setSznote("已推送");
                        emppush.setDtmakedate(DateUtils.getTodayStr());
                        emppush.setEmployeeid(employees.getEmployeeid());
                        emppush.setSource(source);
                        emppush.setByisuse(1);
                        emppushService.updateStatus(emppush);
                    }
                }
            }
        }
    }

    private String request(Employees employees,String url,String method) throws Exception {

        DataTrans request = new DataTrans();
        //组装签名数据
        Map<String, String> params = JSON.parseObject(JSON.toJSONString(employees), type);
        //带&的有顺序的参数字符串
        String prestr = DataUtils.buildSignString(params);
        //加密
        String encryData = DataUtils.encodeData(prestr);
        request.setData(encryData);
        //用“&”字符拼接成字符串+ key+method 获取sign
        String sign = DataUtils.sign(secretKey, params,method, "UTF-8");
        // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
        request.setSign(sign);
        request.setMethod(method);
        String responseJson = HttpClientUtil.httpPostJson(url, JSON.toJSONString(request), null);
        if (StringUtils.isBlank(responseJson)) {
            throw new Exception("");
        }
        return responseJson;
    }
}
