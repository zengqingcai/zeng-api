package com.ectrip.ajax;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;

/**
 * 处理ajax返回的结果信息
 *
 * @author wenqiang.luo date:15-9-7
 */
public class AjaxResult implements Serializable {

    private static final long serialVersionUID = 5837409853066710012L;

    private static final String SUCCESS_DESCRIPTION = "success";

    /**
     * ajax 交互结果
     */
    private boolean success;

    /**
     * 错误码
     */
    private String code;

    /**
     * 交互描述信息
     */
    private String description;

    /**
     * 上次请求url地址
     */
    private String lastRequestUrl;

    /**
     * 本次请求url地址
     */
    private String currentRequestUrl;

    /**
     * 下次跳转的url地址
     */
    private String nextRequestUrl;

    /**
     * 交互数据
     */
    private Object data;

    /**
     * 分页模型
     */
    private PageInfo pageInfo;


    //construct////////////////////////////////////////////////////////////////

    public AjaxResult() {
        this(false);
    }

    public AjaxResult(boolean success) {
        this(success, SUCCESS_DESCRIPTION);
    }

    public AjaxResult(boolean success, String description) {
        this(success, description, null, null);
    }

    public AjaxResult(boolean success, String description, Object data, PageInfo pageInfo) {
        this.success = success;
        this.description = description;
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public AjaxResult(boolean success, String code, String description) {
        this.success = success;
        this.code = code;
        this.description = description;
    }

    public AjaxResult(boolean success, String code, String description, Object data, PageInfo pageInfo) {
        this.success = success;
        this.code = code;
        this.description = description;
        this.data = data;
        this.pageInfo = pageInfo;
    }

    public static AjaxResult error(String description, String code) {
        return new AjaxResult(false, code, description);
    }

    public String getCode() {
        return code;
    }
    //get and set//////////////////////////////////////////////////////////////

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLastRequestUrl() {
        return lastRequestUrl;
    }

    public void setLastRequestUrl(String lastRequestUrl) {
        this.lastRequestUrl = lastRequestUrl;
    }

    public String getCurrentRequestUrl() {
        return currentRequestUrl;
    }

    public void setCurrentRequestUrl(String currentRequestUrl) {
        this.currentRequestUrl = currentRequestUrl;
    }

    public String getNextRequestUrl() {
        return nextRequestUrl;
    }

    public void setNextRequestUrl(String nextRequestUrl) {
        this.nextRequestUrl = nextRequestUrl;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public AjaxResult withData(Object data) {
        this.data = data;
        return this;
    }


    //public static method/////////////////////////////////////////////////////

    public static AjaxResult error(String description) {
        return new AjaxResult(false, description);
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static AjaxResult success(String description) {
        return new AjaxResult(true, description);
    }

    public static AjaxResult success(Object data) {
        return new AjaxResult(true, null, data, null);
    }

    public static AjaxResult success(Object data, PageInfo pageInfo) {
        return new AjaxResult(true, null, data, pageInfo);
    }

    public static AjaxResult success(String description, Object data) {
        return new AjaxResult(true, description, data, null);
    }

}
