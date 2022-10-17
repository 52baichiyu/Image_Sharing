package com.example.photosharing;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.photosharing.login.App_Login;

public class MainActivity extends App_close {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent = new Intent(MainActivity.this, App_Login.class);
        finish();
        startActivity(intent);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



    }



}