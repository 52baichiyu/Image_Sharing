package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/9/17
 *@substance:
 */

import android.graphics.Bitmap;

public class Path_to_Bitmap {
    Bitmap bitmap;
    String path;

    public Path_to_Bitmap(Bitmap bitmap, String path) {
        this.bitmap = bitmap;
        this.path = path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public Path_to_Bitmap() {

    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
