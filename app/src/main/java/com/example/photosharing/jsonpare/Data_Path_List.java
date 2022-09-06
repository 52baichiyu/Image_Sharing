package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/9/5
 *@substance:
 */

import java.util.List;


public class Data_Path_List {

    private String imageCode;

    private List<String> imageUrlList;

    public Data_Path_List() {

    }

    public Data_Path_List(String imageCode, List<String> imageUrlList) {
        this.imageCode = imageCode;
        this.imageUrlList = imageUrlList;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public List<String> getImageUrlList() {
        return imageUrlList;
    }

    public void setImageUrlList(List<String> imageUrlList) {
        this.imageUrlList = imageUrlList;
    }
}
