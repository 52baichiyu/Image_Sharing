package com.example.photosharing.Up_Data;/*
 *@author: 余
 *@date: 2022/9/13
 *@substance:
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.photosharing.MyAdpter.MyAdapter;
import com.example.photosharing.MyAdpter.MyAdapter_0;
import com.example.photosharing.R;
import com.example.photosharing.constant.Constant;
import com.example.photosharing.jsonpare.Data_Path;
import com.example.photosharing.jsonpare.Data_pubilish;
import com.example.photosharing.jsonpare.Path_to_Bitmap;
import com.example.photosharing.jsonpare.Save_Image_Data;
import com.example.photosharing.jsonpare.data_image;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class App_up__Datadown extends AppCompatActivity {
    private static final String DIRECTORY_PICTURES = "Pictures";
    private static final String TAG = "1";
    private final List<String> imageUrlList = new Vector<>();
    ImageView imageView;
    Runable runable;
    Looper mainlooper;

    Save_Image_Data data;


    private  String APP_ID;

    String path_Save  = "/storage/emulated/0/Download";

   String Uri = "http://47.107.52.7:88/member/photo/share/save?current=1&size=1&userId=";

   Save_Image_Data saveImageData = null;

/*
 * @description 布局相关
 * @param 
*/
   
    RecyclerView recyclerView;

    private List<data_image> bitmapList = new ArrayList<>() ;

    boolean flg = true;

    String A="A", B="T" ;
     Handler handler2 ;
    Button button;
    String uri;
    private com.example.photosharing.jsonpare.data_image data_image;

    private EditText title;
    private EditText content;
    private ImageView come_Back;

    private String pu_title;
    private String pu_content;
    private String image_code;

/*
 * @description item 点击事件
 * @param
*/

    private int MAX_DATE = 5;

    private int sig_Number =0;
    private String[] Array = new String[MAX_DATE];

    Uri uri_2;

    MyAdapter myAdapter;

    private final List<String> image_Path = new ArrayList<>();

    public App_up__Datadown() {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_up_data_dow);

        /*
         * @description 接收Id
         * @param
        */
        Intent intent = new Intent();
        intent=getIntent();
        APP_ID = intent.getStringExtra("APP_Id");
        System.out.println(Uri+APP_ID);
     //   imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button2);
      //   uri = "https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2022/08/26/cf5fec26-8ff3-4d3d-a16c-7d6310ef4e92.jpeg";
        recyclerView = findViewById(R.id.lvNew1);
         mainlooper = Looper.myLooper();
         content = findViewById(R.id.text_content1);
         title = findViewById(R.id.text_title1);
         come_Back = findViewById(R.id.come_back);
         
         
         
        imageUrlList.add(uri);





       Download();



  
       /*
        * @description save to up button
        * @param null
       */
       
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    System.out.println(image_Path);
                    pu_title = title.getText().toString();
                    pu_content = content.getText().toString();

                    OkHttpClient okHttpClient = new OkHttpClient();

                    Headers headers = new Headers.Builder()
                            .add("appId", "fdf96a0eb7fd451abcbd4f2509a3309f")
                            .add("appSecret", "778851a88e4675f11429ea6aff0be2d99bf6a")
                            .build();

                    String uri = "http://47.107.52.7:88/member/photo/image/upload";

                    if (getRequestBody(image_Path) != null) {
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
                                            String uri_publish = "http://47.107.52.7:88/member/photo/share/change";

                                            JSONObject pubilish = new JSONObject();
                                            try {
                                                pubilish.put("content", pu_content);
                                                pubilish.put("id",data.getData().getRecords().get(0).getId());
                                                pubilish.put("imageCode", image_code);
                                                pubilish.put("pUserId", APP_ID);
                                                pubilish.put("title", pu_title);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                            RequestBody rqBy_pubilish = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), String.valueOf(pubilish));
                                            Request rq_pubilish = new Request.Builder()
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
                                                                data_pubilish = objectMapper.readValue(res_pubilish, Data_pubilish.class);
                                                            } catch (JsonProcessingException e) {
                                                                e.printStackTrace();
                                                            }
                                                            if (data_pubilish.getCode() == 200) {
                                                                Toast.makeText(App_up__Datadown.this, "发布成功！", Toast.LENGTH_SHORT).show();
                                                                finish();
                                                            } else {
                                                                Toast.makeText(App_up__Datadown.this, data_pubilish.getMsg(), Toast.LENGTH_SHORT).show();
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
         * @description 结束当前活动
         * @param
        */
        come_Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
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


    /*
     * @description 子线程Runable
     * @param null
    */

     public class Runable implements Runnable
     {
         data_image bitmap = new data_image();

         private List<String> imge_ptah = new ArrayList<>();


         private List<data_image>  bitmaps = new ArrayList<>();



         boolean flg = true;
  
         private List<String> imageUrlList = new ArrayList<>();
            public Looper myLooper;

            public Handler handler;

         @SuppressLint("HandlerLeak")



         private void stRunning()
         {
             this.flg = false;
         }
         private boolean isRunning()
         {
             return this.flg;
         }



         @Override
         public void run() {

             Looper.prepare();
              while(flg) {






        /*
         * @description 线程通信
         * @param
        */
                  handler = new Handler(Looper.myLooper()) {

                      public void handleMessage(@NonNull Message msg) {
                          super.handleMessage(msg);
                          if (msg.what == 1) {
                           //   String uri2 = "https://guet-lab.oss-cn-hangzhou.aliyuncs.com/api/2022/08/26/cf5fec26-8ff3-4d3d-a16c-7d6310ef4e92.jpeg";
                            Save_Image_Data   imageData = (Save_Image_Data) msg.obj;
                            System.out.println(imageData);
                            for (int i =0 ;i<imageData.getData().getRecords().get(0).getImageUrlList().size();i++)
                            {
                                imageUrlList.add(imageData.getData().getRecords().get(0).getImageUrlList().get(i));
                            }
//                              System.out.println(imageUrlList.size());
//                              System.out.println("succeed");
                              for (int i = 0;i<imageUrlList.size();i++) {
                                  try {
                                      Path_to_Bitmap path_to_bitmap = new Path_to_Bitmap();
                                      path_to_bitmap = getBitMap(imageUrlList.get(0));
                                      bitmap.setImage(path_to_bitmap.getBitmap());
                                      image_Path.add(path_to_bitmap.getPath());
//                                      SavaImage(bitmap.getImage(),path_Save);

                                      System.out.println(bitmap);
                                      bitmaps.add(bitmap);
                                  } catch (IOException e) {
                                      e.printStackTrace();
                                  }
                              }



                              Message msg2 = new Message();
                              msg2.obj = bitmaps;
                    //      msg2.obj = imge_ptah;
                              System.out.println(bitmaps.get(0));
                              msg2.what = 2;
                              handler2.sendMessage(msg2);



                          }
                      }
                  };





                  myLooper = Looper.myLooper();
                  System.out.println(flg);
                  stRunning();
                  System.out.println(flg);
              }
               Looper.loop();



         }
     }



     /*
      * @description 获取数据
      * @param
     */

    public void Download()
    {
        OkHttpClient client = new OkHttpClient();

        Headers header = new Headers.Builder()
                .add("appId", Constant.APP_ID)
                .add("appSecret", Constant.APP_SECRET)
                .build();



        Request request = new Request.Builder()
                .get()
                .url(Uri+APP_ID)
                .headers(header)
                .build();

        Call call =client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(App_up__Datadown.this, "no fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
             //   System.out.println(res);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ObjectMapper objectMapper =new ObjectMapper();
                         data = null;
                        try {
                            data = objectMapper.readValue(res, Save_Image_Data.class);

                            title.setText(data.getData().getRecords().get(0).getTitle());
                            content.setText(data.getData().getRecords().get(0).getContent());

                            runable = new Runable();
                            Thread thread =  new Thread(runable);
                            thread.start();

                            Message msg = new Message();
                            msg.obj = data;
                            msg.what = 1;


                            handler2 = new Handler(Looper.getMainLooper())
                            {
                                @Override
                                public void handleMessage(@NonNull Message msg) {
                                    super.handleMessage(msg);
                                    if(msg.what == 2)
                                    {



                                        bitmapList = (List<data_image>) msg.obj;


                                        System.out.println(bitmapList.get(0));
                                         myAdapter = Myadapter(image_Path,bitmapList, App_up__Datadown.this);

                                        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(int position) {
                                                if (position == 0) {
                                                    if(sig_Number<3){
                                                    Intent intent = new Intent(Intent.ACTION_PICK, null);
                                                    intent.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, "image/*");
                                                    startActivityForResult(intent, 2);
                                                    }
                                                    else
                                                    {
                                                        System.out.println("???");
                                                    }
                                                }
                                            }
                                        });


                                    }
                                }
                            };


                            while(true)
                            {
                                if(runable.myLooper!=null)
                                {
                                    runable.handler.sendMessage(msg);

                                    break;
                                }
                            }


                        } catch (JsonProcessingException e) {
                            e.printStackTrace();
                        }
                        //    System.out.println(data);

                    }
                });

            }
        });



    }



     /*
      * @description List_sign
      * @param
     */


    public MyAdapter Myadapter(List<String> image_Path, List<data_image> bitmapList , Context context)
    {
        MyAdapter myAdapter =  new MyAdapter(context,R.layout.item_list,R.layout.item_list_two,bitmapList,image_Path);

        bitmapList.add(0, data_image);
        myAdapter.notifyItemInserted(0);
//
        myAdapter.notifyItemChanged(0, bitmapList.size() - 0);
        myAdapter.notifyDataSetChanged();



        GridLayoutManager gm = new GridLayoutManager(context,4);
        recyclerView.setLayoutManager(gm);
        recyclerView.setAdapter(myAdapter);
        return myAdapter;
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
                uri_2 = data.getData();
                sig_Number = bitmapList.size()-1;
                for(int i = 1 ;i<bitmapList.size();i++)
                {
//                    try {
//                    //    Uri uri_1 = android.net.Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmapList.get(i), null,null));
//                        image_Path.add(getDataColumn(this,uri_1,null,null));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }

                }
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri_2));
                    if(bitmap!=null)
                    {
                        data_image = new data_image(bitmap);
                        System.out.println(bitmapList+"?");
                        bitmapList.add(data_image);
                        System.out.println(bitmapList+"?");

                        Array[sig_Number] = uri_2.toString();

                        image_Path.add(getDataColumn(this,uri_2,null,null));

                        sig_Number++;
                        System.out.println(image_Path);
                        ///   System.out.println(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,null,null)));
                        System.out.println(getDataColumn(this,uri_2,null,null));
                        System.out.println(myAdapter);
                        myAdapter.notifyItemInserted(0);

                        myAdapter.notifyItemChanged(0,bitmapList.size()-0);
                        myAdapter.notifyDataSetChanged();

                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }


            }

        }
    }



/*
 * @description  test recycle map
 * @param uri = Bitmap-link from server
*/

    public Path_to_Bitmap getBitMap(String path) throws IOException
    {
        Path_to_Bitmap path_to_bitmap = new Path_to_Bitmap();
        try {
            /*
             * @description download map from
             * @param
            */
            URL url = new URL(path);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(5000);
            con.setRequestMethod("GET");
            if(con.getResponseCode()==200) {
                InputStream inputStream =con.getInputStream();
                Bitmap bitmap =  BitmapFactory.decodeStream(inputStream);
            //    SavaImage(bitmap, getExternalCacheDir());
     //           SavaImage(bitmap, Environment.getExternalStoragePublicDirectory("Pictures"));
                 path_to_bitmap.setBitmap(BitmapFactory.decodeFile( SavaImage(bitmap, getExternalCacheDir())));
                path_to_bitmap.setPath(SavaImage(bitmap, getExternalCacheDir()));
                return path_to_bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
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


    /**
     * 保存位图到本地
     * @param bitmap
     * @param path 本地路径
     * @return void
     */
    public String SavaImage(Bitmap bitmap, File path){
        long ptah = System.currentTimeMillis();
        File file=new File(String.valueOf(path));
        FileOutputStream fileOutputStream=null;
        FileInputStream fileInputStream = null;
        //文件夹不存在，则创建它"/"+
//+"/"+System.currentTimeMillis()+".png"
        if(!file.exists()){
            file.mkdir();
            Log.d(TAG, "SavaImage() returned: " +"?" );
        }
        try {
            fileOutputStream=new FileOutputStream(file+"/"+ptah+".jpg");
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100,fileOutputStream);
            fileOutputStream.close();

            String pt = file+"/"+ptah+".jpg";
System.out.println(pt);
            return pt;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
