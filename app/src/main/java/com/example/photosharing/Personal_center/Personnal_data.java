package com.example.photosharing.Personal_center;/*
 *@author: 余
 *@date: 2022/10/17
 *@substance:
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.photosharing.ActivityCollector;
import com.example.photosharing.App_close;
import com.example.photosharing.R;
import com.example.photosharing.jsonpare.data_login;

public class Personnal_data extends App_close {
    private TextView _userName;
    private TextView _userId;
    private TextView _userSex;
    private TextView _userItron;
    private ImageView _reBackCenter;
    private data_login _data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_personnal_data);

        _userName = findViewById(R.id.user_name_2);
        _userId = findViewById(R.id.user_id_2);
        _userSex = findViewById(R.id.user_sex_2);
        _userItron = findViewById(R.id.user_content_2);
        _reBackCenter = findViewById(R.id.reBack_center2);

        Intent intent = getIntent();
        _data = (data_login) intent.getSerializableExtra("data");

        _userName.setText(_data.getData().getUsername());
        _userId.setText(_data.getData().getId());

        if (_data.getData().getSex()!=null)
            if (_data.getData().getSex().equals("1"))
                _userSex.setText("男");
            else _userSex.setText("女");
        _userItron.setText(_data.getData().getIntroduce());

        _reBackCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ActivityCollector.removeActivity(Personnal_data.this);
                finish();
            }
        });
    }
}
