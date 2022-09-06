package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/9/5
 *@substance:
 */


import androidx.annotation.NonNull;

public class Data_Path {


    private Integer code;

    private String msg;

    private Data_Path_List data;

    public Data_Path() {

    }

    @NonNull
    @Override
    public String toString() {
        return "Data_Path{" +
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

    public Data_Path_List getData() {
        return data;
    }

    public void setData(Data_Path_List data) {
        this.data = data;
    }
}
