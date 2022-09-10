package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/9/10
 *@substance:
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Data_pubilish {

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private Object data;

    public Data_pubilish() {

    }

    @Override
    public String toString() {
        return "Data_pubilish{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
