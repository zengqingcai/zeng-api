package com.ectrip.utils;

/**
 * Created by huangxinguang on 2017/3/27 ����9:10.
 * </p>
 * Desc:
 */
public class HttpClientConfig {
    /**
     * �����ʽ
     */
    public static String ENCODING = "UTF-8";

    public static String CONTENT_TYPE="application/x-www-form-urlencoded";
    /**
     * �������ӳ����������
     */
    public static final int MAX_TOTAL = 60;
    /**
     * ÿ·�������������Ĭ��ֵ��2
     */
    public static final int MAX_PER_ROUTE = 5;
    /**
     * ����ʱ��
     */
    public static final int CONNECT_TIMEOUT = 20 * 1000;
    /**
     * socket����ʱ��
     */
    public static final int SOCKET_TIMEOUT = 20 * 1000;
    /**
     * ��������
     */
    public static final int RETRY_NUM = 8;
}
