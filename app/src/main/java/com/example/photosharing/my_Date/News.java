package com.example.photosharing.my_Date;

public class News {
    private String title;
    private String content;
    private String image;
    private String shareId;
    private String likeId;
    private String username;
    private String createTime;
    private String hasFocus;
    private String [] imageArray;

    public String[] getImageArray() {
        return imageArray;
    }

    public void setImageArray(String[] imageArray) {
        this.imageArray = imageArray;
    }

    public String getHasFocus() {
        return hasFocus;
    }

    public void setHasFocus(String hasFocus) {
        this.hasFocus = hasFocus;
    }

    public String getFocusUserId() {
        return focusUserId;
    }

    public void setFocusUserId(String focusUserId) {
        this.focusUserId = focusUserId;
    }

    private String focusUserId;
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public String getShareId() {
        return shareId;
    }

    public void setShareId(String shareId) {
        this.shareId = shareId;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}