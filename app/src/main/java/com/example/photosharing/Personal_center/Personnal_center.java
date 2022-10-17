package com.example.photosharing.Personal_center;/*
 *@author: 余
 *@date: 2022/10/17
 *@substance:
 */

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.photosharing.ActivityCollector;
import com.example.photosharing.App_close;
import com.example.photosharing.R;
import com.example.photosharing.jsonpare.data_login;
import com.example.photosharing.login.App_Login;
import com.example.photosharing.main.App_Main;

public class Personnal_center extends App_close {
    private data_login _data;
    private View _personnalData;
    private View _personnalDataChange;
    private ImageView _reBackCenter;
    private Button _unLogin;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_personnal);

        _personnalData = findViewById(R.id.personal_data);
        _personnalDataChange = findViewById(R.id.personal_data_change);
        _reBackCenter = findViewById(R.id.reBack_center);
        _unLogin = findViewById(R.id.un_login);
        Button button = findViewById(R.id.save);

        Intent intent = getIntent();
        _data = (data_login) intent.getSerializableExtra("data");

        //进入修改个人信息页面
        _personnalDataChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Personnal_center.this, Edit_imformation.class);
                intent.putExtra("data", _data);
                activityResultLauncher.launch(intent);
            }
        });
        //返回
        _reBackCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               ActivityCollector.removeActivity(Personnal_center.this);
                finish();
            }
        });
        //个人信息页面入口
        _personnalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Personnal_center.this, Personnal_data.class);
                intent.putExtra("data", _data);
                startActivity(intent);
            }
        });
         //
        _unLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Personnal_center.this, App_Login.class);
                //  intent.putExtra("apkId", data.getData().getAppKey());

                ActivityCollector.finishAll();
                finish();
                startActivity(intent);
            }
        });
    }

    /*
     * @description 更新数据
     * @param
    */
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        //获取返回的数据
//                        assert data != null;
                        Bundle bundle = data.getExtras();

                        boolean flag = bundle.getBoolean("flag");
                        if(flag){
                            String temp_userName = bundle.getString("userName");
                            int temp_sex = bundle.getInt("sex");
                            String temp_introduction = bundle.getString("introduction");

                            //修改data类里保存的数据
                            _data.getData().setUsername(temp_userName);
                            _data.getData().setSex(String.valueOf(temp_sex));
                            _data.getData().setIntroduce(temp_introduction);
                            _data.getData().setAvatar(bundle.getString("avatar"));





                        }

                    }
                }

            });

}
