package com.example.photosharing.login;/*
 *@author: 余
 *@date: 2022/8/23
 *@substance:Login
 */

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.photosharing.R;
import com.example.photosharing.jsonpare.data_login;
import com.example.photosharing.main.App_Main;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class App_Login extends AppCompatActivity {


    private final int SIG=1, SIG2=0;

    private  String url = "http://47.107.52.7:88";

    private int sig=1 , sig2=SIG2;

    SharedPreferences r_information;
    SharedPreferences.Editor r_information1;

    private String acc ,pas;

  //  private static String START_Main = "Start_Main";
    /**
     * @description Create View
     * @param   "avedInstanceState"
     * @return null
     * @author 余
     * @time 2022/8/23 10:58
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        r_information =getSharedPreferences("data",MODE_PRIVATE);
        r_information1=r_information.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_login);
        Intent intent = getIntent();
        //intent.getStringExtra(MainActivity.START_LOBIN);
        /*
         * @description View-Components geting data
         * @param {
         * "y_n_show"  Check_password
         * "password"  Get_password
         * "account"   Get_account
         * "bt_Login"  Login
         * "r_Passowrd" Check_password_show/not
         * }
         */
        ImageButton y_n_show=findViewById(R.id.checkpassword);
        EditText password=findViewById(R.id.password1);
        EditText account=findViewById(R.id.account);
        Button bt_Login=findViewById(R.id.bt_login);
        CheckBox r_Passowrd=findViewById(R.id.checkBox);
        Button bt_Register = findViewById(R.id.bt_Register);

        if(r_information.getString("account","0")!=null) {
            if(r_information.getString("passowrd","0")!=null)
            {
                account.setText(r_information.getString("account", ""));
                password.setText(r_information.getString("passowrd", ""));

            }
            else {
                account.setText(r_information.getString("account", ""));

            }
        }

        /**
         * @description Register
         * @param
         * @return null
         * @author 余
         * @time 2022/8/24 11:05
         */

        bt_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_Register = new Intent(App_Login.this, App_UserRegister.class);
                startActivity(intent_Register);
            }
        });



        /**
         * @description Yes/Not show password
         * @param  sig
         * @return null
         * @author 余
         * @time 2022/8/23 10:57
         */
        y_n_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sig==SIG)
                {
                    y_n_show.setImageResource(R.drawable.ic_baseline_visibility_off_24);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);

                    int textLength1 = password.getText().length();
                    password.setSelection(textLength1,textLength1);

                    sig=SIG2;
                }
                else
                {
                    y_n_show.setImageResource(R.drawable.ic_baseline_visibility_24);
                    password.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD |
                            InputType.TYPE_CLASS_TEXT
                    );
                    int textLength1 = password.getText().length();
                    password.setSelection(textLength1,textLength1);

                    sig=SIG;
                }
            }
        });


        /**
         * @description remember password
         * @param null
         * @return null
         * @author 余
         * @time 2022/8/23 10:53
         */
        r_Passowrd.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                if(r_Passowrd.isChecked())
                {
                     acc=account.getText().toString();
                    pas=password.getText().toString();

                    r_information1.putString("account",acc);
                    r_information1.putString("passowrd",pas);
                    r_information1.commit();

                    System.out.println(r_information.getString("account","0"));
                    //   Toast.makeText(getApplicationContext(), "口令设置成功" +r_information.getString("account","0"), Toast.LENGTH_LONG).show();

                    sig2 =SIG2;
                }
            }
        });

        /**
         * @description Login
         * @param
         * @return null
         * @author 余
         * @time 2022/8/23 11:06
         */
        bt_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                r_information1.putString("account",acc);
                if(sig2==SIG2)
                {
                    r_information1.putString("password",pas);
                }
                else
                    r_information1.putString("password",null);

                acc=account.getText().toString();
                pas=password.getText().toString();

                OkHttpClient client = new OkHttpClient();

                Headers headers = new Headers.Builder()
                        .add("appId","fdf96a0eb7fd451abcbd4f2509a3309f")
                        .add("appSecret","778851a88e4675f11429ea6aff0be2d99bf6a")
                        .build();

                FormBody formBody  = new FormBody.Builder()
                        .add("password",pas)
                        .add("username",acc)
                        .build();

                
                Request request = new Request.Builder()
                        .post(formBody)
                        .url(url+"/member/photo/user/login")
                        .headers(headers)
                        .build();

                //封装 request
                Call call = client.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Toast.makeText(App_Login.this, "请检查账号或密码", Toast.LENGTH_SHORT).show();
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



                                ObjectMapper objectMapper = new ObjectMapper();


                                    System.out.println("12");
                                     data_login data = null;
                                try {

                                     data = objectMapper.readValue(res, data_login.class);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                if(data!=null)
                                if(data.getCode()==200) {
                                    Intent intent = new Intent(App_Login.this, App_Main.class);
                                    intent.putExtra("Data", res);

                                  //  intent.putExtra("apkId", data.getData().getAppKey());
                                    finish();
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(App_Login.this, "请检查账号或密码", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });

            }
        });


    }

}
