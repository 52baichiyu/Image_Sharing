package com.example.photosharing.login;/*
 *@author: 余
 *@date: 2022/8/24
 *@substance:
 */

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.photosharing.ActivityCollector;
import com.example.photosharing.App_close;
import com.example.photosharing.R;
import com.example.photosharing.constant.Constant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class App_UserRegister  extends App_close {

    private final String url = "http://47.107.52.7:88";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_user_register);


        Button bt_rebackLogin = findViewById(R.id.bt_reLogin);
        Button bt_startregister = findViewById(R.id.bt_S_Register);

        EditText password=findViewById(R.id.bt_Password);
        EditText account=findViewById(R.id.bt_Account);
        EditText password2=findViewById(R.id.bt_Password2);

        /**
         * @description reback Login
         * @param
         * @return null
         * @author 余
         * @time 2022/8/24 11:40
         */
      bt_rebackLogin.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              ActivityCollector.removeActivity(App_UserRegister.this);
              finish();
          }
      });


        /**
         * @description register
         * @param
         * @return
         * @author 余
         * @time 2022/8/24 12:02
         */

        bt_startregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String acc = account.getText().toString();
                String pas = password.getText().toString();
                String pas2 = password2.getText().toString();



                if(pas2.equals(pas))
                {
                    OkHttpClient client = new OkHttpClient();

                    Headers headers = new Headers.Builder()
                            .add("appId", Constant.APP_ID)
                            .add("appSecret",Constant.APP_SECRET)
                            .build();

                    FormBody formBody  = new FormBody.Builder()
                            .add("password",pas)
                            .add("username",acc)
                            .build();

                    JSONObject json = new JSONObject();
                    try {
                        json.put("password",pas);
                        json.put("username",acc);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }



                    RequestBody requestBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), String.valueOf(json));

                    Request request = new Request.Builder()
                            .post(requestBody)
                            .url(url+"/member/photo/user/register")
                            .headers(headers)
                            .build();

                    Call call = client.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            Toast.makeText(App_UserRegister.this, "no fail", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                /*
                                 * @description 提取数据
                                 * @param
                                 */
                                public void run() {
                                    int a = 0 ,c=0;
                                    String b="200" ,attr = null;

                                    System.out.println(res);
                                    for(String att : res.split("\"|:|,| "))
                                    {
                                        a++;
                                        System.out.println(att);
                                        switch (a) {
                                            case 4: b = att;break;
                                            case 9: ; attr = att;System.out.println(attr);break;
                                            default:break;
                                         }

                                    }

                                    if(b.equals("200")) {
                                        Toast.makeText(App_UserRegister.this, "注册成功！", Toast.LENGTH_SHORT).show();
                                        ActivityCollector.removeActivity(App_UserRegister.this);
                                        finish();
                                    }
                                    else
                                    {
                                        if(attr.equals("username"))
                                            Toast.makeText(App_UserRegister.this, "用户名不能为空！", Toast.LENGTH_SHORT).show();
                                        else if(attr.equals("password"))
                                            Toast.makeText(App_UserRegister.this, "密码不能为空！", Toast.LENGTH_SHORT).show();
                                        else
                                        Toast.makeText(App_UserRegister.this, "该用户名已被注册！", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });

                }

                else
                {
                    Toast.makeText(App_UserRegister.this, "请检查两次输入密码是否一致！", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
