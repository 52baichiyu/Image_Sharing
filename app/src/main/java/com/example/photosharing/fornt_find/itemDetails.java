package com.example.photosharing.fornt_find;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.photosharing.R;
import com.example.photosharing.constant.Constant;
import com.example.photosharing.main_page.FindFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class itemDetails extends AppCompatActivity {
    private String body;
    private String shareId;
    private String userId;
//    private String imageDetails;
    private String likeId;
    private String focusUserId;
    private String collectId;
    private String userName;
    private String content;
    private List<Comments> commentsList=new ArrayList<>();
    private String [] imageArray;
    private int photoIndex=0;

    private TextView tvTitle;
    private TextView tvContent;
    private ImageView ivImage;
    private ImageView like;
    private TextView tvUsername;
    private TextView tvCreateTime;
    private Button btFocus;
    private Button btComment;
    private ImageView collect;
    private EditText etComment;
    private TextView tvNoneComment;

    private Boolean likeSwitch=false;
    private Boolean focusSwitch=false;
    private Boolean collectSwitch=false;
    private Boolean hasFocus=false;
    private Boolean hasCollect=false;
    private Boolean hasLike=false;

    private CommentsAdater commentsAdapter=null;
    Gson gson=new Gson();
    commentFirst_POST_GET post_get=new commentFirst_POST_GET();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);

        Intent intent=getIntent();
        userId=intent.getStringExtra(FindFragment.MESSAGE_USERID);
        shareId=intent.getStringExtra(FindFragment.MESSAGE_SHAREID);
        userName=intent.getStringExtra(FindFragment.MESSAGE_USERNAME);

//        图片URL，用于在详情页中加载图片
//        imageDetails=intent.getStringExtra(Find.MESSAGE_IMAGE);
        imageArray=intent.getStringArrayExtra(FindFragment.MESSAGE_IMAGE);
        for(int i=0;i<imageArray.length;i++){
            System.out.println("详情页的图片数组："+imageArray[i]);
        }
        System.out.println("主线程中断！执行详情页接口请求！");
        get();
        try {
            System.out.println("主线程等待！");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("主线程继续执行！将数据绑定到详情页面！");
            initData(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        show_Comments0();
//展示评论
//        post_get.get(shareId,comments);
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        show_Comments();

//***************************************关注操作******************************************
        if(hasFocus){
            System.out.println("hasFocus为"+hasFocus);
            btFocus.setBackgroundColor(getResources().getColor(R.color.black));
            focusSwitch=true;
        }

        btFocus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                focusSwitch=!focusSwitch;
                focusOrCancel_POST Post = new focusOrCancel_POST();
                if(focusSwitch){
                    btFocus.setBackgroundColor(getResources().getColor(R.color.black));
                    Post.post(focusUserId,userId);
                }else{
                    btFocus.setBackgroundColor(getResources().getColor(R.color.white));
                    Post.post1(focusUserId,userId);

                }
            }
        });
//-----------------------------------------------------------------------------------------------------

//切换图片操作

        ImageView iv_lastPhoto=findViewById(R.id.iv_lastPhoto);
        iv_lastPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<imageArray.length;i++){
                    System.out.println(imageArray[i]);
                }

                if(photoIndex==0){
                    photoIndex=imageArray.length-1;
                    System.out.println(photoIndex);
                }
                else {
                    photoIndex=photoIndex-1;
                    System.out.println(photoIndex);
                }
                if(imageArray.length>1)
                    Glide.with(itemDetails.this).load(imageArray[photoIndex]).into(ivImage);
            }
        });

        ImageView iv_nextPhoto=findViewById(R.id.iv_nextPhoto);
        iv_nextPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                for(int i=0;i<imageArray.length;i++){
                    System.out.println(imageArray[i]);
                }
//                int photoIndex=0;
                if(photoIndex==imageArray.length-1){
                    photoIndex=0;
                    System.out.println(photoIndex);
                }
                else {
                    photoIndex=photoIndex+1;
                    System.out.println(photoIndex);
                }
                if(imageArray.length>1)
                    Glide.with(itemDetails.this).load(imageArray[photoIndex]).into(ivImage);
            }
        });

//****************************************点赞操作*******************************************************
        if(hasLike){
            System.out.println("hasLike为"+hasLike);
            like.setImageResource(R.drawable.ic_baseline_thumb_up_alt_24);
            likeSwitch=true;
        }

        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                likeSwitch=!likeSwitch;
                likeOrCancel_Post Post = new likeOrCancel_Post();
                if(likeSwitch){
                    like.setImageResource(R.drawable.ic_baseline_thumb_up_alt_24);
                    System.out.println("执行点赞接口前 "+likeId);
                    Post.post(shareId,userId);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    get();


                }else{
                    like.setImageResource(R.drawable.ic_baseline_thumb_up_alt_23);
                    System.out.println("执行取消点赞接口前"+likeId);
                    Post.post1(likeId);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    get();
                }
            }
        });
//------------------------------------------------------------------------------------------------------

//**********************************收藏操作******************************************************************
        if(hasCollect){
            System.out.println("hasCollect为"+hasCollect);
            collect.setImageResource(R.drawable.ic_baseline_star_23);
            focusSwitch=true;
        }
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectSwitch=!collectSwitch;
                collectOrCancel_Post Post = new collectOrCancel_Post();
                if(collectSwitch){
                    collect.setImageResource(R.drawable.ic_baseline_star_23);
                    System.out.println("执行收藏接口前collectId："+ collectId);
                    Post.post(shareId,userId);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    get();


                }else{
                    collect.setImageResource(R.drawable.ic_baseline_star_24);
                    System.out.println("执行取消收藏接口前collectId："+ collectId);
                    Post.post1(collectId);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    get();
                }
            }
        });
//-----------------------------------------------------------------------------------------------------

//*******************************************发表评论接口请求****************************************
        btComment=findViewById(R.id.bt_deliver);
        btComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etComment=findViewById(R.id.et_comment);
                content=etComment.getText().toString();
                etComment.setText("");
                post_get.post(content,shareId,userId,userName);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("commentsList大小："+commentsList.size());
                commentsList.clear();
                System.out.println("commentsList大小："+commentsList.size());
                System.out.println("进入show");
                show_Comments1();
            }
        });
// -----------------------------------------------------------------------------------------------------
    }


    //展示图文详情页接口请求
    private void get(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://47.107.52.7:88/member/photo/share/detail?shareId="+shareId+"&userId="+userId;

                // 请求头
                Headers headers = new Headers.Builder()
                        .add("appId", Constant.APP_ID)
                        .add("appSecret", Constant.APP_SECRET)
                        .add("Accept", "application/json, text/plain, */*")
                        .build();

                //请求组合创建
                Request request = new Request.Builder()
                        .url(url)
                        // 将请求头加至请求中
                        .headers(headers)
                        .get()
                        .build();
                try {
                    OkHttpClient client = new OkHttpClient();
                    //发起请求，传入callback进行回调
                    client.newCall(request).enqueue(callback);
                }catch (NetworkOnMainThreadException ex){
                    ex.printStackTrace();
                }
            }
        });
        thread.setPriority(6);
        thread.start();
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
            Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
            // 获取响应体的json串
            System.out.println("图文详情接口成功返回！");
            body = response.body().string();
            Log.d("info", body);
            // 解析json串到自己封装的状态
            ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);
            Log.d("info", dataResponseBody.toString());

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(body.toString())
                        .getJSONObject("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                hasFocus=jsonObject.getBoolean("hasFocus");
                hasCollect=jsonObject.getBoolean("hasCollect");
                hasLike=jsonObject.getBoolean("hasLike");
                likeId= jsonObject.getString("likeId");
                collectId= jsonObject.getString("collectId");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("likeId:"+likeId+" hasLike:"+hasLike+" hasFocus:"+hasFocus+" hasCollect:"+hasCollect+" collectId:"+collectId);
        }
    };

    //显示自己评论之前所有评论函数
    private void show_Comments0()
    {
        post_get.get(shareId,commentsList);
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(commentsList.size()!=0) {
            CommentsAdater commentsAdater = new CommentsAdater(itemDetails.this,
                    R.layout.comment_item, commentsList);
            ListView lvNewsList = findViewById(R.id.lv_comments_list);
            lvNewsList.setAdapter(commentsAdater);
        }
        else{
            tvNoneComment.setVisibility(View.VISIBLE);
        }
        System.out.println("初次判断评论成功");
    }
    //显示自己评论后的所以评论函数
    private void show_Comments1()
    {
        post_get.get(shareId,commentsList);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            tvNoneComment.setVisibility(View.GONE);
        System.out.println("commentsList个数："+commentsList.size());
            CommentsAdater commentsAdater = new CommentsAdater(itemDetails.this,
                    R.layout.comment_item, commentsList);
            ListView lvNewsList = findViewById(R.id.lv_comments_list);
            lvNewsList.setAdapter(commentsAdater);
        System.out.println("适配器绑定数据成功");
    }

    //数据绑定到详情页控件上
    private void initData(String body) throws JSONException {
        System.out.println("执行initData函数进行数据绑定！");
        JSONObject jsonObject = new JSONObject(body.toString())
                .getJSONObject("data");
        tvUsername=findViewById(R.id.tv_username);
        tvCreateTime=findViewById(R.id.tv_createTime);
        btFocus=findViewById(R.id.bt_focus);
        collect=findViewById(R.id.collect);
        like=findViewById(R.id.like);
        ivImage = findViewById(R.id.iv_image);
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        tvNoneComment = findViewById(R.id.tv_none_comment);

        tvUsername.setText(jsonObject.getString("username"));
        tvCreateTime.setText(jsonObject.getString("createTime"));
        Glide.with(this).load(imageArray[0]).into(ivImage);
        tvTitle.setText(jsonObject.getString("title"));
        tvContent.setText(jsonObject.getString("content"));

        focusUserId=jsonObject.getString("pUserId");

        System.out.println("绑定结束显示数据到控件！");
    }
    /**
     * http响应体的封装协议
     * @param <T> 泛型
     */
    public static class ResponseBody <T> {

        /**
         * 业务响应码
         */
        private int code;
        /**
         * 响应提示信息
         */
        private String msg;
        /**
         * 响应数据
         */
        private T data;

        public ResponseBody(){}

        public int getCode() {
            return code;
        }
        public String getMsg() {
            return msg;
        }
        public T getData() {
            return data;
        }

        @NonNull
        @Override
        public String toString() {
            return "ResponseBody{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }
}