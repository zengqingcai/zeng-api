package com.ectrip.utils;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by cxh on 2016/10/11.
 */
public class DataUtils {

    /**
     * 验证签名错误信息
     * 如果为null,表示无错误信息,验证通过; 如果非空,则为验证失败的描述信息
     *
     * @param sign        签名信息
     * @param key         KEY
     * @param encryptData 加密后的数据字符串
     * @param method      方法名
     * @return
     */
    public static String verifySign(String sign, String key, String encryptData, String method) {
        if (null == sign || "".equals(sign.trim())) {
            return "签名信息不能为空!";
        }
        if (null == key || "".equals(key.trim())) {
            return "KEY不能为空!";
        }
        if (null == encryptData || "".equals(encryptData.trim())) {
            return "数据串不能为空!";
        }
        if (null == method || "".equals(method.trim())) {
            return "方法不能为空!";
        }

        String testSign = getSign(key, encryptData, method);
        if (null != testSign && sign.equals(testSign)) {
            return null;
        } else {
            return "签名验证不通过!";
        }
    }

    /**
     * 获得签名
     *
     * @param key    KEY
     * @param data   加密后的数据字符串
     * @param method 方法名
     * @return 签名信息
     */
    public static String getSign(String key, String data, String method) {
        try {
            return MD5Hex(key + data + method.toUpperCase());
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * 加密基础交互数据
     *
     * @param jsonStr json数据
     * @return 加密后的BASE64数据
     */
    public static String encodeData(String jsonStr) {
        try {
            return Base64.encodeBase64String(jsonStr.getBytes("UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密交互数据
     *
     * @param enryptData 加密的BASE 64数据
     * @return 解密后的json字符串
     */
    public static String decodeData(String enryptData) throws Exception {

        byte[] bytes = Base64.decodeBase64(enryptData.getBytes());
        return new String(bytes, "UTF-8");
    }

    public static DataTrans combineDataTrans(String key, String data, String method) {
        data = encodeData(data);
        String sign = getSign(key, data, method);
        return new DataTrans(data, sign, method);
    }

    public final static String MD5Hex(String input) {
        String s = null;
        char hexDigits[] = { // 用来将字节转换成 16 进制表示的字码?
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',
                'e', 'f'};
        try {
            java.security.MessageDigest md = java.security.MessageDigest
                    .getInstance("MD5");
            md.update(input.getBytes("utf-8"));
            byte tmp[] = md.digest();

            char str[] = new char[16 * 2];

            int k = 0;
            for (int i = 0; i < 16; i++) {

                byte byte0 = tmp[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            s = new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    //签名动作
    public static String sign(String key, Map<String, String> params,String method, String charset) {
        String prestr = buildSignString(params);
        String text = key + prestr + method.toUpperCase();
        return DigestUtils.md5Hex(getContentBytes(text, charset));
    }

    private static byte[] getContentBytes(String content, String charset) {
        if (charset == null || "".equals(charset)) {
            return content.getBytes();
        }
        try {
            return content.getBytes(charset);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("MD5签名过程中出现错误,指定的编码集不对,您目前指定的编码集是:"
                    + charset);
        }
    }

    public static String getJsonStrByQueryUrl(String paramStr){
        //String paramStr = "a=a1&b=b1&c=c1";
        String[] params = paramStr.split("&");
        JSONObject obj = new JSONObject();
        for (int i = 0; i < params.length; i++) {
            String[] param = params[i].split("=");
            if (param.length >= 2) {
                String key = param[0];
                String value = param[1];
                for (int j = 2; j < param.length; j++) {
                    value += "=" + param[j];
                }
                try {
                    obj.put(key,value);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        return obj.toString();
    }


    public static String buildSignString(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.size());

        for (String key : params.keySet()) {
            if ("sign".equals(key) || "sign_type".equals(key)) {
                continue;
            }
            if (StringUtils.isEmpty(params.get(key))) {
                continue;
            }
            keys.add(key);
        }

        Collections.sort(keys);

        StringBuilder buf = new StringBuilder();

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                buf.append(key + "=" + value);
            } else {
                buf.append(key + "=" + value + "&");
            }
        }
        return buf.toString();

    }
}

