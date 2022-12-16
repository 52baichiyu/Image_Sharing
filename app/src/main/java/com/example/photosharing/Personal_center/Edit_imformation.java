package com.example.photosharing.Personal_center;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.os.NetworkOnMainThreadException;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.bumptech.glide.Glide;
import com.example.photosharing.ActivityCollector;
import com.example.photosharing.App_close;
import com.example.photosharing.R;
import com.example.photosharing.Up_Data.App_up_Data;
import com.example.photosharing.constant.Constant;
import com.example.photosharing.jsonpare.Data_Path;
import com.example.photosharing.jsonpare.Data_pubilish;
import com.example.photosharing.jsonpare.data_image;
import com.example.photosharing.jsonpare.data_login;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Edit_imformation extends App_close {

    private final Gson gson = new Gson();


    private long mExitTime;//保存上次点击back键的系统时间
    //判断是否保存
    private boolean flag = false;

    private ImageView _headerImage;
    private EditText userName;
    //    private EditText sex;
    private RadioGroup radioGroup_gender;
    private RadioButton radioButton_gender;
    private RadioButton radioButton_male;
    private RadioButton radioButton_female;

    private EditText introduction;

    private data_login data;
    private String _image_Path;
    private String _uri;

    private Request request;

    Bitmap bitmap_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_imformation);
        /*
         * @description 动态申请权限
         * @param
         */
        //绑定控件
        _headerImage =findViewById(R.id.User_Pictrue1);
        userName = findViewById(R.id.userName);
        radioGroup_gender = findViewById(R.id.radioGroup_gender);
        radioButton_male = findViewById(R.id.radioButton_male);
        radioButton_female = findViewById(R.id.radioButton_female);
        introduction = findViewById(R.id.introduction);
        Button button = findViewById(R.id.save);


        Intent intent = getIntent();
        data = (data_login) intent.getSerializableExtra("data");
        _image_Path = data.getData().getAvatar();
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (Edit_imformation.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    Edit_imformation.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return ;
                } else
                {
                    //控件现在的值
                    Glide.with(this).load(data.getData().getAvatar()).into(_headerImage);
                    userName.setText(data.getData().getUsername());
                    if(data.getData().getSex()!=null)
                        if (data.getData().getSex().equals("0")) {
                            radioGroup_gender.clearCheck();
                            radioButton_female.setChecked(true);
                            radioButton_male.setChecked(false);
                        }
                        else {
                            radioGroup_gender.clearCheck();
                            radioButton_male.setChecked(true);
                            radioButton_female.setChecked(false);
                        }
                    else
                    {
                        radioGroup_gender.clearCheck();
                        radioButton_male.setChecked(true);
                        radioButton_female.setChecked(false);
                    };
                    introduction.setText(data.getData().getIntroduce());

                    ImageView back = findViewById(R.id.Back);

                    _headerImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(Intent.ACTION_PICK, null);
                            intent.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "image/*");
                            startActivityForResult(intent, 2);

                        }
                    });

                    back.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            reBack(_image_Path);


                        }
                    });

                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            post();

                        }
                    });
                }
            }
        }

    }
    /*
     * @description up image
     * @param
     */

    private static RequestBody getRequestBody(String file_Image)
    {
        if(file_Image!=null) {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            File file = new File(file_Image);

            builder.addFormDataPart(
                    "fileList",
                    file.getName(),
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), file)
            );
            return builder.build();
        }
        else return null;
    }





    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /*
     * @description 活动回调
     * @param
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2)
        {
            if(data != null)
            {

                _uri = getDataColumn(this,data.getData(),null,null) ;
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
                    if(bitmap!=null)
                    {
                        Glide.with(this).load(bitmap).into(_headerImage);
                    }

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }

        }
    }





    //点击手机屏幕上的返回键，进行回调
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent();
            //将需传回的数据用bundle进行封装
            Bundle bundle =new Bundle();
            if(flag){
                bundle.putBoolean("flag",flag);
                bundle.putString("userName",userName.getText().toString());
                radioButton_gender = findViewById(radioGroup_gender.getCheckedRadioButtonId());
                if(radioButton_gender.getText().toString().equals("男"))//sex.getText()并不是字符串，需先转化
                    bundle.putInt("sex",1);
                else if(radioButton_gender.getText().toString().equals("女"))
                    bundle.putInt("sex",0);
                bundle.putString("introduction",introduction.getText().toString());

                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                ActivityCollector.removeActivity(Edit_imformation.this);
                finish();
            }
            else{
                bundle.putBoolean("flag",flag);
                intent.putExtras(bundle);
                setResult(RESULT_OK,intent);
                ActivityCollector.removeActivity(Edit_imformation.this);
                finish();
            }

            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void post(){
        new Thread(() -> {

            /*
             * @description 上传图片获取uri
             * @param
             */

            OkHttpClient okHttpClient = new OkHttpClient();

            Headers headers = new Headers.Builder()
                    .add("appId", Constant.APP_ID)
                    .add("appSecret", Constant.APP_SECRET)
                    .build();

            String uri = "http://47.107.52.7:88/member/photo/image/upload";

            RequestBody requestBody = getRequestBody(_uri);

            if(requestBody !=null) {
                request = new Request.Builder()
                        .url(uri)
                        .headers(headers)
                        .post(requestBody)
                        .build();
            }
            else
            {
                request = new Request.Builder()
                        .url(uri)
                        .headers(headers)

                        .build();
            }
            //封装requset
            Call call = okHttpClient.newCall(request);
            System.out.println("312");
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String res = response.body().string();
                    System.out.println("322");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ObjectMapper objectMapper = new ObjectMapper();

                            Data_Path data_path = null;
                            System.out.println(res);
                            try {
                                data_path = objectMapper.readValue(res, Data_Path.class);
                                _image_Path = data_path.getData().getImageUrlList().get(0);
                            } catch (JsonProcessingException e) {
                                e.printStackTrace();
                            }
                            // url路径
                            String url = "http://47.107.52.7:88/member/photo/user/update";

                            // 请求头
                            Headers headers1 = new Headers.Builder()
                                    .add("Accept", "application/json, text/plain, */*")
                                    .add("appId", Constant.APP_ID)
                                    .add("appSecret", Constant.APP_SECRET)
                                    .add("Content-Type", "application/json")
                                    .build();

                            // 请求体
                            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串


                            Map<String, String> bodyMap = new HashMap<>();
                            if(requestBody !=null) {
                                bodyMap.put("avatar",data_path.getData().getImageUrlList().get(0));
                            }
                            bodyMap.put("id",data.getData().getId());
                            bodyMap.put("introduce", introduction.getText().toString());
                            radioButton_gender = findViewById(radioGroup_gender.getCheckedRadioButtonId());
                            if(radioButton_gender.getText().toString().equals("男"))
                                bodyMap.put("sex", "1");
                            else if(radioButton_gender.getText().toString().equals("女"))
                                bodyMap.put("sex","0");
                            bodyMap.put("username", userName.getText().toString());


                            // 将Map转换为字符串类型加入请求体中
                            String body = gson.toJson(bodyMap);

                            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

                            //请求组合创建
                            Request request = new Request.Builder()
                                    .url(url)
                                    // 将请求头加至请求中
                                    .headers(headers1)
                                    .post(RequestBody.create(MEDIA_TYPE_JSON, body))
                                    .build();
                            System.out.println("调用接口1");
                            try {
                                OkHttpClient client = new OkHttpClient();
                                //发起请求，传入callback进行回调
                                System.out.println("调用接口2");
                                client.newCall(request).enqueue(callback);
                            }catch (NetworkOnMainThreadException ex){
                                ex.printStackTrace();
                            }

                        }
                    });
                }
            });






        }).start();
    }

    /**
     * 回调
     */
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            //TODO 请求失败处理
            e.printStackTrace();
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO 请求成功处理
//            System.out.println("成功调用接口");
            flag = true;

            //子线程中调用Toast.makeText,Android中不允许在子线程中处理UI
            Looper.prepare();
            Toast.makeText(Edit_imformation.this, "修改成功", Toast.LENGTH_SHORT).show();
            System.out.println(introduction.getText().toString());
            reBack(_image_Path);
            Looper.loop();
        }
    };

    private void reBack(String data1)
    {
        Intent intent = new Intent();
        //将需传回的数据用bundle进行封装
        System.out.println(_uri+" 135");
        Bundle bundle = new Bundle();
        if (flag) {
            bundle.putString("avatar",data1);
            bundle.putBoolean("flag", flag);
            bundle.putString("userName", userName.getText().toString());
            radioButton_gender = findViewById(radioGroup_gender.getCheckedRadioButtonId());
            if (radioButton_gender.getText().toString().equals("男"))//sex.getText()并不是字符串，需先转化
                bundle.putInt("sex", 1);
            else if (radioButton_gender.getText().toString().equals("女"))
                bundle.putInt("sex", 0);
            bundle.putString("introduction", introduction.getText().toString());
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            ActivityCollector.removeActivity(Edit_imformation.this);
            finish();
        } else {
            bundle.putBoolean("flag", flag);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            ActivityCollector.removeActivity(Edit_imformation.this);
            finish();
        }
    }


}

//package com.example.photosharing.Personal_center;
//
//
//import android.Manifest;
//import android.content.Context;
//import android.content.Intent;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.net.Uri;
//import android.os.Build;
//import android.os.Bundle;
//import android.os.Looper;
//import android.os.NetworkOnMainThreadException;
//import android.provider.MediaStore;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.RadioButton;
//import android.widget.RadioGroup;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import com.bumptech.glide.Glide;
//import com.example.photosharing.ActivityCollector;
//import com.example.photosharing.App_close;
//import com.example.photosharing.R;
//import com.example.photosharing.Up_Data.App_up_Data;
//import com.example.photosharing.constant.Constant;
//import com.example.photosharing.jsonpare.Data_Path;
//import com.example.photosharing.jsonpare.Data_pubilish;
//import com.example.photosharing.jsonpare.data_image;
//import com.example.photosharing.jsonpare.data_login;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.google.gson.Gson;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Headers;
//import okhttp3.MediaType;
//import okhttp3.MultipartBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.RequestBody;
//import okhttp3.Response;
//
//public class Edit_imformation extends App_close {
//
//    private final Gson gson = new Gson();
//
//
//    private long mExitTime;//保存上次点击back键的系统时间
//    //判断是否保存
//    private boolean flag = false;
//
//    private ImageView _headerImage;
//    private EditText userName;
//    private EditText sex;
//    private RadioGroup radioGroup_gender;
//    private RadioButton radioButton_gender;
//    private RadioButton radioButton_male;
//    private RadioButton radioButton_female;
//
//    private EditText introduction;
//
//    private data_login data;
//    private String _image_Path;
//    private String _uri;
//
//    private Request request;
//
//    Bitmap bitmap_test;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_edit_imformation);
//        /*
//         * @description 动态申请权限
//         * @param
//         */
//        //绑定控件
//        _headerImage =findViewById(R.id.User_Pictrue1);
//        userName = findViewById(R.id.userName);
//        radioGroup_gender = findViewById(R.id.radioGroup_gender);
//        radioButton_male = findViewById(R.id.radioButton_male);
//        radioButton_female = findViewById(R.id.radioButton_female);
//        introduction = findViewById(R.id.introduction);
//        Button button = findViewById(R.id.save);
//
//
//        Intent intent = getIntent();
//         data = (data_login) intent.getSerializableExtra("data");
//         _image_Path = data.getData().getAvatar();
//        if (Build.VERSION.SDK_INT >= 23) {
//            int REQUEST_CODE_CONTACT = 101;
//            String[] permissions = {
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            //验证是否许可权限
//            for (String str : permissions) {
//                if (Edit_imformation.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                    //申请权限
//                    Edit_imformation.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                    return ;
//                } else
//                {
//
//
//
//
//        //控件现在的值
//        Glide.with(this).load(data.getData().getAvatar()).into(_headerImage);
//        userName.setText(data.getData().getUsername());
//        if(data.getData().getSex()!=null)
//        if (data.getData().getSex().equals("0")) {
//            radioGroup_gender.clearCheck();
//            radioButton_female.setChecked(true);
//            radioButton_male.setChecked(false);
//        }
//        else {
//                    radioGroup_gender.clearCheck();
//                    radioButton_male.setChecked(true);
//                    radioButton_female.setChecked(false);
//                }
//        else
//         {
//                        radioGroup_gender.clearCheck();
//                        radioButton_male.setChecked(true);
//                        radioButton_female.setChecked(false);
//                    };
//        introduction.setText(data.getData().getIntroduce());
//
//        ImageView back = findViewById(R.id.Back);
//
//        _headerImage.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_PICK, null);
//                intent.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "image/*");
//                startActivityForResult(intent, 2);
//
//            }
//        });
//
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//             reBack(_image_Path);
//
//
//            }
//        });
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                post();
//
//            }
//        });
//         }
//            }
//        }
//
//    }
///*
// * @description up image
// * @param
//*/
//
//    private static RequestBody getRequestBody(String file_Image)
//    {
//        if(file_Image!=null) {
//            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
//
//                File file = new File(file_Image);
//
//                builder.addFormDataPart(
//                        "fileList",
//                        file.getName(),
//                        RequestBody.create(MediaType.parse("application/json; charset=utf-8"), file)
//                );
//            return builder.build();
//        }
//        else return null;
//    }
//
//
//
//
//
//    /**
//     * Get the value of the data column for this Uri. This is useful for
//     * MediaStore Uris, and other file-based ContentProviders.
//     *
//     * @param context       The context.
//     * @param uri           The Uri to query.
//     * @param selection     (Optional) Filter used in the query.
//     * @param selectionArgs (Optional) Selection arguments used in the query.
//     * @return The value of the _data column, which is typically a file path.
//     */
//    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
//
//        Cursor cursor = null;
//        final String column = "_data";
//        final String[] projection = {column};
//        try {
//            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
//            if (cursor != null && cursor.moveToFirst()) {
//                final int column_index = cursor.getColumnIndexOrThrow(column);
//                return cursor.getString(column_index);
//            }
//        } finally {
//            if (cursor != null)
//                cursor.close();
//        }
//        return null;
//    }
//
//    /*
//     * @description 活动回调
//     * @param
//     */
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(requestCode==2)
//        {
//            if(data != null)
//            {
//
//                _uri = getDataColumn(this,data.getData(),null,null) ;
//                try {
//                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(data.getData()));
//                    if(bitmap!=null)
//                    {
//                       Glide.with(this).load(bitmap).into(_headerImage);
//                    }
//
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        }
//    }
//
//
//
//
//
//    //点击手机屏幕上的返回键，进行回调
//    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//                Intent intent = new Intent();
//                //将需传回的数据用bundle进行封装
//                Bundle bundle =new Bundle();
//                if(flag){
//                    bundle.putBoolean("flag",flag);
//                    bundle.putString("userName",userName.getText().toString());
//                    if(sex.getText().toString().equals("男"))//sex.getText()并不是字符串，需先转化
//                        bundle.putInt("sex",1);
//                    else if(sex.getText().toString().equals("女"))
//                        bundle.putInt("sex",0);
//                    bundle.putString("introduction",introduction.getText().toString());
//
//                    intent.putExtras(bundle);
//                    setResult(RESULT_OK,intent);
//                    ActivityCollector.removeActivity(Edit_imformation.this);
//                    finish();
//                }
//                else{
//                    bundle.putBoolean("flag",flag);
//                    intent.putExtras(bundle);
//                    setResult(RESULT_OK,intent);
//                    ActivityCollector.removeActivity(Edit_imformation.this);
//                    finish();
//                }
//
//            return true;
//        }
//        return super.onKeyDown(keyCode, event);
//    }
//
//    private void post(){
//        new Thread(() -> {
//
//            /*
//             * @description 上传图片获取uri
//             * @param
//            */
//
//            OkHttpClient okHttpClient = new OkHttpClient();
//
//            Headers headers = new Headers.Builder()
//                    .add("appId", Constant.APP_ID)
//                    .add("appSecret", Constant.APP_SECRET)
//                    .build();
//
//            String uri = "http://47.107.52.7:88/member/photo/image/upload";
//
//            RequestBody requestBody = getRequestBody(_uri);
//
//            if(requestBody !=null) {
//                 request = new Request.Builder()
//                        .url(uri)
//                        .headers(headers)
//                        .post(requestBody)
//                        .build();
//            }
//            else
//            {
//                 request = new Request.Builder()
//                        .url(uri)
//                        .headers(headers)
//
//                        .build();
//            }
//                //封装requset
//                Call call = okHttpClient.newCall(request);
//                System.out.println("312");
//                call.enqueue(new Callback() {
//                    @Override
//                    public void onFailure(Call call, IOException e) {
//
//                    }
//
//                    @Override
//                    public void onResponse(Call call, Response response) throws IOException {
//                        String res = response.body().string();
//                        System.out.println("322");
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                ObjectMapper objectMapper = new ObjectMapper();
//
//                                Data_Path data_path = null;
//                                System.out.println(res);
//                                try {
//                                    data_path = objectMapper.readValue(res, Data_Path.class);
//                                    _image_Path = data_path.getData().getImageUrlList().get(0);
//                                } catch (JsonProcessingException e) {
//                                    e.printStackTrace();
//                                }
//                                // url路径
//                                String url = "http://47.107.52.7:88/member/photo/user/update";
//
//                                // 请求头
//                                Headers headers1 = new Headers.Builder()
//                                        .add("Accept", "application/json, text/plain, */*")
//                                        .add("appId", Constant.APP_ID)
//                                        .add("appSecret", Constant.APP_SECRET)
//                                        .add("Content-Type", "application/json")
//                                        .build();
//
//                                // 请求体
//                                // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
//
//
//                                Map<String, String> bodyMap = new HashMap<>();
//                                if(requestBody !=null) {
//                                    bodyMap.put("avatar",data_path.getData().getImageUrlList().get(0));
//                                }
//                                bodyMap.put("id",data.getData().getId());
//                                bodyMap.put("introduce", introduction.getText().toString());
//                                if(sex.getText().toString().equals("男"))
//                                    bodyMap.put("sex", "1");
//                                else if(sex.getText().toString().equals("女"))
//                                    bodyMap.put("sex","0");
//                                bodyMap.put("username", userName.getText().toString());
//
//
//                                // 将Map转换为字符串类型加入请求体中
//                                String body = gson.toJson(bodyMap);
//
//                                MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
//
//                                //请求组合创建
//                                Request request = new Request.Builder()
//                                        .url(url)
//                                        // 将请求头加至请求中
//                                        .headers(headers1)
//                                        .post(RequestBody.create(MEDIA_TYPE_JSON, body))
//                                        .build();
//                                System.out.println("调用接口1");
//                                try {
//                                    OkHttpClient client = new OkHttpClient();
//                                    //发起请求，传入callback进行回调
//                                    System.out.println("调用接口2");
//                                    client.newCall(request).enqueue(callback);
//                                }catch (NetworkOnMainThreadException ex){
//                                    ex.printStackTrace();
//                                }
//
//                            }
//                        });
//                    }
//                });
//
//
//
//
//
//
//        }).start();
//    }
//
//    /**
//     * 回调
//     */
//    private final Callback callback = new Callback() {
//        @Override
//        public void onFailure(@NonNull Call call, IOException e) {
//            //TODO 请求失败处理
//            e.printStackTrace();
//        }
//        @Override
//        public void onResponse(@NonNull Call call, Response response) throws IOException {
//            //TODO 请求成功处理
////            System.out.println("成功调用接口");
//            flag = true;
//
//            //子线程中调用Toast.makeText,Android中不允许在子线程中处理UI
//            Looper.prepare();
//            Toast.makeText(Edit_imformation.this, "修改成功", Toast.LENGTH_SHORT).show();
//            System.out.println(introduction.getText().toString());
//            reBack(_image_Path);
//            Looper.loop();
//        }
//    };
//
//    private void reBack(String data1)
//    {
//        Intent intent = new Intent();
//        //将需传回的数据用bundle进行封装
//        System.out.println(_uri+" 135");
//        Bundle bundle = new Bundle();
//        if (flag) {
//            bundle.putString("avatar",data1);
//            bundle.putBoolean("flag", flag);
//            bundle.putString("userName", userName.getText().toString());
//            if (sex.getText().toString().equals("男"))//sex.getText()并不是字符串，需先转化
//                bundle.putInt("sex", 1);
//            else if (sex.getText().toString().equals("女"))
//                bundle.putInt("sex", 0);
//            bundle.putString("introduction", introduction.getText().toString());
//            intent.putExtras(bundle);
//            setResult(RESULT_OK, intent);
//            ActivityCollector.removeActivity(Edit_imformation.this);
//            finish();
//        } else {
//            bundle.putBoolean("flag", flag);
//            intent.putExtras(bundle);
//            setResult(RESULT_OK, intent);
//            ActivityCollector.removeActivity(Edit_imformation.this);
//            finish();
//        }
//    }
//
//
//}