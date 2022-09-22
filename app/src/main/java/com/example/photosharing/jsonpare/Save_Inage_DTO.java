package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/9/16
 *@substance:
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Save_Inage_DTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("pUserId")
    private String pUserId;
    @JsonProperty("imageCode")
    private String imageCode;
    @JsonProperty("title")
    private String title;
    @JsonProperty("content")
    private String content;
    @JsonProperty("createTime")
    private String createTime;
    @JsonProperty("imageUrlList")
    private List<String> imageUrlList;
    @JsonProperty("likeId")
    private Object likeId;
    @JsonProperty("likeNum")
    private Object likeNum;
    @JsonProperty("hasLike")
    private Boolean hasLike;
    @JsonProperty("collectId")
    private Object collectId;
    @JsonProperty("collectNum")
    private Object collectNum;
    @JsonProperty("hasCollect")
    private Boolean hasCollect;
    @JsonProperty("hasFocus")
    private Boolean hasFocus;
    @JsonProperty("username")
    private Object username;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getpUserId() {
        return pUserId;
    }

    public void setpUserId(String pUserId) {
        this.pUserId = pUserId;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public Object getLikeId() {
        return likeId;
    }

    public void setLikeId(Object likeId) {
        this.likeId = likeId;
    }

    public Object getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Object likeNum) {
        this.likeNum = likeNum;
    }

    public Boolean getHasLike() {
        return hasLike;
    }

    public void setHasLike(Boolean hasLike) {
        this.hasLike = hasLike;
    }

    public Object getCollectId() {
        return collectId;
    }

    public void setCollectId(Object collectId) {
        this.collectId = collectId;
    }

    public Object getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(Object collectNum) {
        this.collectNum = collectNum;
    }

    public Boolean getHasCollect() {
        return hasCollect;
    }

    public void setHasCollect(Boolean hasCollect) {
        this.hasCollect = hasCollect;
    }

    public Boolean getHasFocus() {
        return hasFocus;
    }

    public void setHasFocus(Boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
    }

    public Save_Inage_DTO() {

    }

    @Override
    public String toString() {
        return "Save_Inage_DTO{" +
                "id='" + id + '\'' +
                ", pUserId='" + pUserId + '\'' +
                ", imageCode='" + imageCode + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", createTime='" + createTime + '\'' +
                ", imageUrlList=" + imageUrlList +
                ", likeId=" + likeId +
                ", likeNum=" + likeNum +
                ", hasLike=" + hasLike +
                ", collectId=" + collectId +
                ", collectNum=" + collectNum +
                ", hasCollect=" + hasCollect +
                ", hasFocus=" + hasFocus +
                ", username=" + username +
                '}';
    }
}
