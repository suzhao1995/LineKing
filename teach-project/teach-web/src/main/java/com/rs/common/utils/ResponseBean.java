package com.rs.common.utils;

import java.io.Serializable;

/**
* ResponseBen.java
* @Description:接口统一状态返回bean
* @author: suzhao
* @date: 2019年7月22日 下午4:41:52
* @version: V1.0
*/
public class ResponseBean implements Serializable{
	private static final long serialVersionUID = 3593827217136880822L;
	
    private static final String DEFAULT_MESSAGE_SUCCESS = "成功";
    private static final String DEFAULT_MESSAGE_ERROR = "失败";
    public static final String DEFAULT_MESSAGE_PARAMETER_ERROR = "请求参数不合法";
    public static final String DEFAULT_MESSAGE_SYS_ERROR = "系统异常";
    private static final String CODE_SUCCESS = "0";
    private static final String CODE_ERROR = "1";
    public static final String CODE_PARAMETER_ERROR = "1000";
    public static final String CODE_SYS_ERROR = "1005";
    public static final String CODE_LIMIT_ERROR = "1010";
    public static final String CODE_SHOP_ERROR = "1020";
    public static final String CODE_DECRYPT_ERROR = "1006";
    private String code = "0";
    private String message = "成功";
    private Object data = null;
    
    public ResponseBean() {
    	
    }

    public void addDefaultError() {
        this.code = CODE_SYS_ERROR;
        this.message = "失败";
    }

    public void addError(String message) {
        this.code = CODE_SYS_ERROR;
        this.message = message;
    }

    public void addError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addSuccess() {
        this.code = "0";
        this.message = "成功";
    }

    public void addSuccess(Object data) {
        this.code = "0";
        this.message = "成功";
        this.data = data;
    }

    public Object getData() {
         return this.data;
    }

    public final String getCode() {
        return this.code;
    }

    public final String getMessage() {
        return this.message;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("ResponseBean [code=");
        builder.append(this.code);
        builder.append(", message=");
        builder.append(this.message);
        builder.append(", data=");
        builder.append(this.data);
        builder.append("]");
        return builder.toString();
    }
}
