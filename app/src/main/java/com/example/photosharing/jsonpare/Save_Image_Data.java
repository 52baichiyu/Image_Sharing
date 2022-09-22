package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/9/16
 *@substance:
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class Save_Image_Data {

    @JsonProperty("code")
    private Integer code;
    @JsonProperty("msg")
    private String msg;
    @JsonProperty("data")
    private Save_Image_Records data;

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

    public Save_Image_Records getData() {
        return data;
    }

    public void setData(Save_Image_Records data) {
        this.data = data;
    }

    public Save_Image_Data() {

    }

    @Override
    public String toString() {
        return "Save_Image_Data{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
