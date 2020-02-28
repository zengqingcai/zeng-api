package com.ectrip.utils;

/**
 * Created by cxh on 2016/09/01.
 */
public class DataTrans {

    public String data;
    public String sign;// 签名
    public String method;// 方法名

    public DataTrans() {
    }

    public DataTrans(String data, String sign, String method) {
        this.data = data;
        this.sign = sign;
        this.method = method;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}