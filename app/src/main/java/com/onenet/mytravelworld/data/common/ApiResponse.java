package com.onenet.mytravelworld.data.common;

/**
 * ApiResponse
 *
 * @author HaiYinLong
 * @version 2025/05/23 14:40
 **/
public class ApiResponse<T> {
    private int code;
    private String msg;
    private T data;

    public ApiResponse() {}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return code == 0;
    }

    public T getData() {
        return data;
    }

}
