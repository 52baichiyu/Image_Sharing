package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/8/25
 *@substance:
 */

import com.fasterxml.jackson.annotation.JsonProperty;


public class DataDTOX {
    @JsonProperty("id")
    private String id;
    @JsonProperty("appKey")
    private String appKey;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private Object password;
    @JsonProperty("sex")
    private Object sex;
    @JsonProperty("introduce")
    private Object introduce;
    @JsonProperty("avatar")
    private Object avatar;
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("lastUpdateTime")
    private String lastUpdateTime;

    public DataDTOX() {
    }

    public DataDTOX(String id, String appKey, String username, Object password, Object sex, Object introduce, Object avatar, String createTime, String lastUpdateTime) {
        this.id = id;
        this.appKey = appKey;
        this.username = username;
        this.password = password;
        this.sex = sex;
        this.introduce = introduce;
        this.avatar = avatar;
        this.createTime = createTime;
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Object getPassword() {
        return password;
    }

    public void setPassword(Object password) {
        this.password = password;
    }

    public Object getSex() {
        return sex;
    }

    public void setSex(Object sex) {
        this.sex = sex;
    }

    public Object getIntroduce() {
        return introduce;
    }

    public void setIntroduce(Object introduce) {
        this.introduce = introduce;
    }

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
