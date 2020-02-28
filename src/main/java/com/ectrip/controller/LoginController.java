package com.ectrip.controller;

import com.alibaba.fastjson.JSON;
import com.ectrip.model.Employees;
import com.ectrip.service.IEmployeesService;
import com.ectrip.utils.DateUtils;
import com.ectrip.utils.Encrypt;
import com.ectrip.utils.VerifyUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {
    @Resource
    private IEmployeesService employeesService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/emplogin")
    public String emplogin(HttpServletRequest request, ModelMap map, Employees employees) {
        HttpSession session=request.getSession();
            String CAPTCHA = (String) session.getAttribute("CAPTCHA");
            if (StringUtils.isBlank(CAPTCHA)) {
                map.put("error", "输入验证码不能为空!");
                return "login";
            }
            if (!CAPTCHA.equals(employees.getCAPTCHA())) {
                map.put("error", "验证码不匹配!");
                return "login";
            }
            if (StringUtils.isBlank(employees.getEmpid()) || StringUtils.isBlank(employees.getPassword())) {
                map.put("error", "输入用户名和密码均不能为空!");
                return "login";
            }
            Employees loginEmp = employeesService.getEmployess(employees.getEmpid());
            if (loginEmp == null) {
                map.put("error", "该用户不存在请重新输入账号!");
                return "login";
            }
            if(loginEmp.getEmptype()!=0){
            map.put("error", "该用户不是平台用户,不能登陆!");
            return "login";
            }
            if(loginEmp.getByisuse()!=1){
                map.put("error", "该用户状态不是有效状态,请联系管理员!");
                return "login";
            }
            if (loginEmp.getZtimes() <= 5) {
                Encrypt encrypt = new Encrypt();
                String password = encrypt.passwordEncrypt(employees.getPassword());
                if (!password.equals(loginEmp.getPassword())) {
                    map.put("error", "密码错误请重新输入!");
                    loginEmp.setZtimes(loginEmp.getZtimes() + 1);
                    loginEmp.setDtmakedate(DateUtils.getTodayStr());
                    employeesService.editEmployees(loginEmp);
                    return "login";
                }
            } else {
                map.put("error", "密码输错次数大于5次,请等次日再登陆!");
                return "login";
            }
            //登陆成功将登陆员工信息放入session
            session.setAttribute("employees", loginEmp);
        return "index";
    }

    @RequestMapping("/loginout")
    public String loginOut(HttpServletRequest request) {
        request.getSession().invalidate();
        return "login";
    }

    @RequestMapping("/gainCAPTCHA")
    public void gainCAPTCHA(HttpServletResponse response, HttpServletRequest request) throws Exception{
        HttpSession session=request.getSession();
        //利用图片工具生成图片
        //第一个参数是生成的验证码，第二个参数是生成的图片
        Object[] objs = VerifyUtil.createImage();
        //将验证码存入Session
        session.setAttribute("CAPTCHA",objs[0]);
        //将图片输出给浏览器
        BufferedImage image = (BufferedImage) objs[1];
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "png", os);
    }


}
