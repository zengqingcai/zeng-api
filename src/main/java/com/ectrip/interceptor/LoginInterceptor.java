package com.ectrip.interceptor;


import com.ectrip.config.UserContext;
import com.ectrip.model.Employees;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class LoginInterceptor implements HandlerInterceptor{
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Object employees = request.getSession().getAttribute("employees");
        if (null == employees || !(employees instanceof Employees)) {
            response.sendRedirect("/login");
            return false;
        }else{
            UserContext.setEmployees((Employees)employees);
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
