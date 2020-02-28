package com.ectrip.utils;

import com.ectrip.model.Employees;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class CommonUtil {
        /**
         * SpringMvc下获取request
         *
         * @return
         */
        public static HttpServletRequest getRequest() {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            return request;

        }

        /**
         * 获取session
         *
         * @return
         */
        public static HttpSession getSession() {
            HttpServletRequest request = getRequest();
            HttpSession session = request.getSession();
            return session;
        }

        public static Employees getLoGinEmployees(){
            HttpSession session=getSession();
            Employees employees=(Employees)session.getAttribute("employees");
            return employees;
        }
    }
