package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/8/25
 *@substance:
 */

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

//@lombok.NoArgsConstructor
//@lombok.Data
public class RecordsDTO {
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
    private String username;

    public RecordsDTO(String id, String pUserId, String imageCode, String title, String content, String createTime, List<String> imageUrlList, Object likeId, Object likeNum, Boolean hasLike, Object collectId, Object collectNum, Boolean hasCollect, Boolean hasFocus, String username) {
        this.id = id;
        this.pUserId = pUserId;
        this.imageCode = imageCode;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.imageUrlList = imageUrlList;
        this.likeId = likeId;
        this.likeNum = likeNum;
        this.hasLike = hasLike;
        this.collectId = collectId;
        this.collectNum = collectNum;
        this.hasCollect = hasCollect;
        this.hasFocus = hasFocus;
        this.username = username;
    }

    @Override
    public String toString() {
        return "RecordsDTO{" +
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
                ", username='" + username + '\'' +
                '}';
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setpUserId(String pUserId) {
        this.pUserId = pUserId;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }

    public void setLikeId(Object likeId) {
        this.likeId = likeId;
    }

    public void setLikeNum(Object likeNum) {
        this.likeNum = likeNum;
    }

    public void setHasLike(Boolean hasLike) {
        this.hasLike = hasLike;
    }

    public void setCollectId(Object collectId) {
        this.collectId = collectId;
    }

    public void setCollectNum(Object collectNum) {
        this.collectNum = collectNum;
    }

    public void setHasCollect(Boolean hasCollect) {
        this.hasCollect = hasCollect;
    }

    public void setHasFocus(Boolean hasFocus) {
        this.hasFocus = hasFocus;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getpUserId() {
        return pUserId;
    }

    public String getImageCode() {
        return imageCode;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getCreateTime() {
        return createTime;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public Object getLikeId() {
        return likeId;
    }

    public Object getLikeNum() {
        return likeNum;
    }

    public Boolean getHasLike() {
        return hasLike;
    }

    public Object getCollectId() {
        return collectId;
    }

    public Object getCollectNum() {
        return collectNum;
    }

    public Boolean getHasCollect() {
        return hasCollect;
    }

    public Boolean getHasFocus() {
        return hasFocus;
    }

    public String getUsername() {
        return username;
    }
}
