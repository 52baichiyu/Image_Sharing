package com.example.photosharing.login;/*
 *@author: 余
 *@date: 2022/10/20
 *@substance:
 */

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.photosharing.App_close;
import com.example.photosharing.R;

public class App_jump extends App_close {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_jump);
    }
}
