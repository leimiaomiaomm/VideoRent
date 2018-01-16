package com.heda.video.util;

/**
 * Created by leimiaomiao on 2017/12/27.
 * 返回实体类
 */
public class Response {

    //参数错误
    public static final int PARAMETER = -2;
    //内部错误
    public static final int PARAMETEREXPECTION = -3;

    public Response() {
    }

    public Response(int code, String type, Object result) {
        this.code = code;
        this.type = type;
        this.result = result;
    }

    //0成功，其他失败
    private int code =0;
    //返回结果，失败返回空
    private Object result="";
    //返回失败信息，正确为空
    private String type = "";

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
