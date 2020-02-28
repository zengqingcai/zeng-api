package com.ectrip.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by chenzhaojie on 2019-09-03.
 */
public class HttpClientUtil {

    private static PoolingHttpClientConnectionManager phccm;

    public static String EMPTY_STR_RESULT = "";

    /**
     * 初始化
     */
    private static void init() {
        if (phccm == null) {
            phccm = new PoolingHttpClientConnectionManager();
            phccm.setMaxTotal(HttpClientConfig.MAX_TOTAL);// 整个连接池最大连接数
            phccm.setDefaultMaxPerRoute(HttpClientConfig.MAX_PER_ROUTE);// 每路由最大连接数
        }
    }

    /**
     * 通过连接池获取HttpClient
     *
     * @return
     */
    private static CloseableHttpClient getHttpClient() {
        init();
        // 请求重试处理
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
                if (executionCount >= HttpClientConfig.RETRY_NUM) {// 如果已经重试了8次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    return false;
                }
                if (exception instanceof SSLException) {// SSL握手异常
                    return false;
                }

                HttpClientContext clientContext = HttpClientContext.adapt(context);
                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试
                if (!(request instanceof HttpEntityEnclosingRequest)) {
                    return true;
                }
                return false;
            }
        };

        // 创建全局的requestConfig
        RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(HttpClientConfig.CONNECT_TIMEOUT)
                .setSocketTimeout(HttpClientConfig.SOCKET_TIMEOUT).setCookieSpec(CookieSpecs.BEST_MATCH).build();

        // 声明重定向策略对象
        LaxRedirectStrategy redirectStrategy = new LaxRedirectStrategy();

        return HttpClients.custom().setConnectionManager(phccm).setDefaultRequestConfig(requestConfig)
                .setRedirectStrategy(redirectStrategy).setRetryHandler(httpRequestRetryHandler).build();
    }

    /**
     * get请求
     *
     * @param requestUrl
     * @return
     */
    public static String httpGetRequest(String requestUrl) {
        HttpGet httpGet = new HttpGet(requestUrl);
        return getResult(httpGet);
    }

    /**
     * 带参数的get请求
     *
     * @param requestUrl
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static String httpGetRequest(String requestUrl, Map<String, String> params) throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(requestUrl);

        ArrayList<NameValuePair> pairs = covertParamsToNVPList(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        return getResult(httpGet);
    }

    /**
     * 带header的get请求
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws URISyntaxException
     */
    public static String httpGetRequest(String url, Map<String, String> headers, Map<String, String> params)
            throws URISyntaxException {
        URIBuilder ub = new URIBuilder();
        ub.setPath(url);

        ArrayList<NameValuePair> pairs = covertParamsToNVPList(params);
        ub.setParameters(pairs);

        HttpGet httpGet = new HttpGet(ub.build());
        for (Map.Entry<String, String> param : headers.entrySet()) {
            httpGet.addHeader(param.getKey(), String.valueOf(param.getValue()));
        }
        return getResult(httpGet);
    }

    /**
     * post请求
     *
     * @param url
     * @return
     */
    public static String httpPostRequest(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        return getResult(httpPost);
    }

    /**
     * 带参数的post请求
     *
     * @param url
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String httpPostRequest(String url, Map<String, String> params) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        ArrayList<NameValuePair> pairs = covertParamsToNVPList(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, HttpClientConfig.ENCODING));
        return getResult(httpPost);
    }

    /**
     * 带header的post请求
     *
     * @param url
     * @param headers
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String httpPostRequest(String url, Map<String, String> headers, Map<String, String> params)
            throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);

        for (Map.Entry<String, String> param : headers.entrySet()) {
            httpPost.addHeader(param.getKey(), param.getValue());
        }

        ArrayList<NameValuePair> pairs = covertParamsToNVPList(params);
        httpPost.setEntity(new UrlEncodedFormEntity(pairs, HttpClientConfig.ENCODING));

        return getResult(httpPost);
    }

    public static String httpPostJson(String url, String json, Map<String, String> headers) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(url);
        if(headers != null){
            for (Map.Entry<String, String> param : headers.entrySet()) {
                httpPost.addHeader(param.getKey(), param.getValue());
            }
        }
        httpPost.setEntity(new StringEntity(json, ContentType.APPLICATION_JSON.getMimeType(), HttpClientConfig.ENCODING));
        return getResult(httpPost);
    }

    /**
     * 参数转换
     *
     * @param params
     * @return
     */
    private static ArrayList<NameValuePair> covertParamsToNVPList(Map<String, String> params) {
        ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, String> param : params.entrySet()) {
                pairs.add(new BasicNameValuePair(param.getKey(), param.getValue()));
            }
        }

        return pairs;
    }

    /**
     * 处理Http请求
     *
     * @param request
     * @return
     */
    private static String getResult(HttpRequestBase request) {
        CloseableHttpClient httpClient = getHttpClient();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                throw new RuntimeException(
                        "请求异常,状态码：" + statusCode + "  描述: " + response.getStatusLine().getReasonPhrase());
            }
            String url = request.getRequestLine().getUri();
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return EMPTY_STR_RESULT;
    }
}
