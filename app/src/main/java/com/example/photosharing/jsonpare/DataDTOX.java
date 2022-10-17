package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/8/25
 *@substance:
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;


public class DataDTOX implements Serializable {
    @JsonProperty("id")
    private String id;
    @JsonProperty("appKey")
    private String appKey;
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;
    @JsonProperty("sex")
    private String sex;
    @JsonProperty("introduce")
    private String introduce;
    @JsonProperty("avatar")
    private String avatar;
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("lastUpdateTime")
    private String lastUpdateTime;

    public DataDTOX() {
    }

    public DataDTOX(String id, String appKey, String username, String password, String sex, String introduce, String avatar, String createTime, String lastUpdateTime) {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
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
