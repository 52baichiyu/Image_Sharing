package com.example.photosharing;/*
 *@author: 余
 *@date: 2022/10/17
 *@substance:
 */

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class App_close extends AppCompatActivity {
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //从活动管理器结束当前Activity
        ActivityCollector.removeActivity(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }
}
