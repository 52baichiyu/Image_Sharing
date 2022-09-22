package com.example.photosharing.Up_Data;

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
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photosharing.MyAdpter.MyAdapter;
import com.example.photosharing.MyAdpter.MyAdapter_0;
import com.example.photosharing.R;
import com.example.photosharing.jsonpare.Data_Path;
import com.example.photosharing.jsonpare.Data_pubilish;
import com.example.photosharing.jsonpare.data_image;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class App_up_Data extends AppCompatActivity {



    Uri uri;
    List<String> image_Path = new ArrayList<>();
    private File file_Image;

    private int MAX_DATE = 5;

    private int sig_Number =0;

    private MyAdapter myAdapter = null;
    private RecyclerView recyclerView;

    private List<com.example.photosharing.jsonpare.data_image> newslist = new ArrayList<>();

    private data_image data_image;

    private String[] Array = new String[MAX_DATE];
    
    /*
     * @description up_data variable
     * @param 
    */
    private String image_code;
    private String pu_title;
    private String pu_content;
    /*
     * @description xml components
     * @param 
    */
    
        Button image_button;
        ImageView imageView;
        EditText title_text;
        EditText conte_text;
        ImageView come_Back;
        Button save_data;
        Button re_publication;

        /*
         * @description user id apkid from Login
         * @param
        */
    private static String Id;
    private static String apkId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_up_data);

/*
 * @description 获取用户id - form App_Main
 * @param Id apkId
*/
        Intent intent = new Intent();
        intent = getIntent();
        Id = intent.getStringExtra("Id");
        apkId = intent.getStringExtra("apkId");

        recyclerView = findViewById(R.id.lvNews);
        myAdapter = new MyAdapter(App_up_Data.this, R.layout.item_list,R.layout.item_list_two, newslist,image_Path);

        image_button = findViewById(R.id.button2);
        title_text = findViewById(R.id.text_title);
        come_Back = findViewById(R.id.come_back_find);
        save_data = findViewById(R.id.save_data);
        re_publication = findViewById( R.id.record_publication);
        conte_text = findViewById(R.id.text_content);
        imageView = findViewById(R.id.imageView1);


        /*
         * @description RecyclerView defiane
         * @param
         */

        newslist.add(0, data_image);
        myAdapter.notifyItemInserted(0);
//
        myAdapter.notifyItemChanged(0, newslist.size() - 0);
        myAdapter.notifyDataSetChanged();


        recyclerView = findViewById(R.id.lvNews);
    //    myAdapter = new MyAdapter_0(App_up_Data.this, R.layout.item_list, newslist);

//        LinearLayoutManager llm = new LinearLayoutManager(this);
//         recyclerView.setLayoutManager(llm);
        GridLayoutManager grm = new GridLayoutManager(this, 4);
        recyclerView.setLayoutManager(grm);
        recyclerView.setAdapter(myAdapter);



        /*
         * @description 动态申请权限
         * @param
         */


        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (App_up_Data.this.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    App_up_Data.this.requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return ;
                } else {

         
                    /*
                     * @description 发布内容 入口
                     * @param 
                    */
                    
        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pu_title = title_text.getText().toString();
                pu_content = conte_text.getText().toString();

                OkHttpClient okHttpClient = new OkHttpClient();

                Headers headers = new Headers.Builder()
                        .add("appId", "fdf96a0eb7fd451abcbd4f2509a3309f")
                        .add("appSecret", "778851a88e4675f11429ea6aff0be2d99bf6a")
                        .build();

                String uri = "http://47.107.52.7:88/member/photo/image/upload";

                if(getRequestBody(image_Path)!=null){
                Request request = new Request.Builder()
                        .url(uri)
                        .headers(headers)
                        .post(getRequestBody(image_Path))
                        .build();

                //封装requset
                Call call = okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println(e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ObjectMapper objectMapper = new ObjectMapper();

                                Data_Path data_path = null;
                                System.out.println(res);
                                try {
                                    data_path = objectMapper.readValue(res, Data_Path.class);
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }
                                if (data_path != null) {
                                    System.out.println(data_path.getData().getImageCode());
                                    System.out.println(data_path.getData().getImageUrlList().get(0));
                                    System.out.println(pu_content);
                                    image_code = data_path.getData().getImageCode();
                                    String uri_publish =  "http://47.107.52.7:88/member/photo/share/add";

                                    JSONObject pubilish = new JSONObject();
                                    try {
                                        pubilish.put("content",pu_content);
                                        pubilish.put("imageCode",image_code);
                                        pubilish.put("pUserId",Id);
                                        pubilish.put("title",pu_title);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    RequestBody rqBy_pubilish = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), String.valueOf(pubilish));
                                    Request rq_pubilish = new  Request.Builder()
                                            .post(rqBy_pubilish)
                                            .url(uri_publish)
                                            .headers(headers)
                                            .build();

                                    Call call_pubilish = okHttpClient.newCall(rq_pubilish);

                                    call_pubilish.enqueue(new Callback() {
                                        @Override
                                        public void onFailure(Call call, IOException e) {
                                            System.out.println(e.getMessage());
                                        }

                                        @Override
                                        public void onResponse(Call call, Response response) throws IOException {
                                                     String res_pubilish = response.body().string();
                                                     runOnUiThread(new Runnable() {
                                                         @Override
                                                         public void run() {
//                                                             System.out.println(content);
//                                                             System.out.println(titile);
                                                             System.out.println("123");
                                                             System.out.println(res_pubilish);
                                                             Data_pubilish data_pubilish = new Data_pubilish();
                                                             try {
                                                                 data_pubilish =objectMapper.readValue(res_pubilish,Data_pubilish.class);
                                                             } catch (JsonProcessingException e) {
                                                                 e.printStackTrace();
                                                             }
                                                             if(data_pubilish.getCode()==200)
                                                             {
                                                                 Toast.makeText(App_up_Data.this, "发布成功！", Toast.LENGTH_SHORT).show();
                                                                 finish();
                                                             }
                                                             else
                                                             {
                                                                     Toast.makeText(App_up_Data.this, data_pubilish.getMsg(), Toast.LENGTH_SHORT).show();
                                                             }
                                                         }
                                                     });
                                        }
                                    });


                                }

                            }
                        });
                    }
                });

            }
            }
        });

 /*
  * @description 结束当前活动 返回App_main 活动
  * @param 
 */
        
        come_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*
         * @description 保存当前发布内容草稿 入口
         * @param 
        */
        save_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                pu_title = title_text.getText().toString();
                pu_content = conte_text.getText().toString();

                OkHttpClient okHttpClient = new OkHttpClient();

                Headers headers = new Headers.Builder()
                        .add("appId", "fdf96a0eb7fd451abcbd4f2509a3309f")
                        .add("appSecret", "778851a88e4675f11429ea6aff0be2d99bf6a")
                        .build();

                String uri = "http://47.107.52.7:88/member/photo/image/upload";

                if(getRequestBody(image_Path)!=null){
                    Request request = new Request.Builder()
                            .url(uri)
                            .headers(headers)
                            .post(getRequestBody(image_Path))
                            .build();

                    //封装requset
                    Call call = okHttpClient.newCall(request);

                    call.enqueue(new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            System.out.println(e.getMessage());
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            String res = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    ObjectMapper objectMapper = new ObjectMapper();

                                    Data_Path data_path = null;
                                    System.out.println(res);
                                    try {
                                        data_path = objectMapper.readValue(res, Data_Path.class);
                                    } catch (JsonProcessingException e) {
                                        e.printStackTrace();
                                    }
                                    if (data_path != null) {
                                        System.out.println(data_path.getData().getImageCode());
                                        System.out.println(data_path.getData().getImageUrlList().get(0));
                                        System.out.println(pu_content);
                                        image_code = data_path.getData().getImageCode();
                                        String uri_publish =  "http://47.107.52.7:88/member/photo/share/save";

                                        JSONObject pubilish = new JSONObject();
                                        try {
                                            pubilish.put("content",pu_content);
                                            pubilish.put("imageCode",image_code);
                                            pubilish.put("pUserId",Id);
                                            pubilish.put("title",pu_title);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                        RequestBody rqBy_pubilish = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), String.valueOf(pubilish));
                                        Request rq_pubilish = new  Request.Builder()
                                                .post(rqBy_pubilish)
                                                .url(uri_publish)
                                                .headers(headers)
                                                .build();

                                        Call call_pubilish = okHttpClient.newCall(rq_pubilish);

                                        call_pubilish.enqueue(new Callback() {
                                            @Override
                                            public void onFailure(Call call, IOException e) {
                                                System.out.println(e.getMessage());
                                            }

                                            @Override
                                            public void onResponse(Call call, Response response) throws IOException {
                                                String res_pubilish = response.body().string();
                                                runOnUiThread(new Runnable() {
                                                    @Override
                                                    public void run() {
//                                                             System.out.println(content);
//                                                             System.out.println(titile);
                                                        System.out.println("123");
                                                        System.out.println(res_pubilish);



                                                        Data_pubilish data_pubilish = new Data_pubilish();
                                                        try {
                                                            data_pubilish =objectMapper.readValue(res_pubilish,Data_pubilish.class);
                                                        } catch (JsonProcessingException e) {
                                                            e.printStackTrace();
                                                        }
                                                        if(data_pubilish.getCode()==200)
                                                        {
                                                            Toast.makeText(App_up_Data.this, "已保存！", Toast.LENGTH_SHORT).show();
                                                            
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(App_up_Data.this, data_pubilish.getMsg(), Toast.LENGTH_SHORT).show();
                                                        }


                                                    }
                                                });
                                            }
                                        });


                                    }

                                }
                            });
                        }
                    });
                }
            }
        });

        /*
         * @description 发布-草稿
         * @param
        */
        re_publication.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_rePub = new Intent(App_up_Data.this,App_up__Datadown.class);
                intent_rePub.putExtra("APP_Id",Id);
                startActivity(intent_rePub);
            }
        });

        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if (position == 0) {
                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                    intent.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(intent, 2);
                }
            }
        });


    }}}
    }


/**
 * @description 上传文件路径生成RequestBody
 * @param "image_Path" 文件路径集合
 * @return builder.build() builider请求
 * @author 余
 * @time 2022/9/5 18:29
 */



private static RequestBody getRequestBody(List<String> file_Image)
{
    if(file_Image.size()>0) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        for (int a = 0; a < file_Image.size(); a++) {
            File file = new File(file_Image.get(a));

            builder.addFormDataPart(
                    "fileList",
                    file.getName(),
                    RequestBody.create(MediaType.parse("application/json; charset=utf-8"), file)
            );
        }

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

//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is ExternalStorageProvider.
//     */
//    private static boolean isExternalStorageDocument(Uri uri) {
//        return "com.android.externalstorage.documents".equals(uri.getAuthority());
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is DownloadsProvider.
//     */
//    private static boolean isDownloadsDocument(Uri uri) {
//        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is MediaProvider.
//     */
//    private static boolean isMediaDocument(Uri uri) {
//        return "com.android.providers.media.documents".equals(uri.getAuthority());
//    }


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
              uri = data.getData();
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    if(bitmap!=null)
                    {
                       data_image = new data_image(bitmap);
                       newslist.add(data_image);
                       myAdapter.notifyItemInserted(0);

                       myAdapter.notifyItemChanged(0,newslist.size()-0);
                       myAdapter.notifyDataSetChanged();


                      Array[sig_Number] = uri.toString();

                     image_Path.add(getDataColumn(this,uri,null,null));

                       sig_Number++;

                   ///   System.out.println(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,null,null)));
                        System.out.println(getDataColumn(this,uri,null,null));





                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }

        }
    }


}