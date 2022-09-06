package com.example.photosharing.jsonpare;/*
 *@author: 余
 *@date: 2022/9/3
 *@substance:
 */

import android.graphics.Bitmap;

public class data_image {

    public data_image(Bitmap image) {
        Image = image;
    }

    public Bitmap getImage() {
        return Image;
    }

    public void setImage(Bitmap image) {
        Image = image;
    }

    private Bitmap Image;

}
