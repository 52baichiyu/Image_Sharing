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
import com.example.photosharing.App_close;
import com.example.photosharing.R;
import com.example.photosharing.constant.Constant;
import com.example.photosharing.main_page.FindFragment;
import com.example.photosharing.main_page.FindFragment_child;
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


public class itemDetails extends App_close {
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
        userId=intent.getStringExtra(FindFragment_child.MESSAGE_USERID);
        shareId=intent.getStringExtra(FindFragment_child.MESSAGE_SHAREID);
        userName=intent.getStringExtra(FindFragment_child.MESSAGE_USERNAME);

//        ??????URL????????????????????????????????????
//        imageDetails=intent.getStringExtra(Find.MESSAGE_IMAGE);
        imageArray=intent.getStringArrayExtra(FindFragment_child.MESSAGE_IMAGE);
        for(int i=0;i<imageArray.length;i++){
            System.out.println("???????????????????????????"+imageArray[i]);
        }
        System.out.println("????????????????????????????????????????????????");
        get();
        try {
            System.out.println("??????????????????");
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            System.out.println("?????????????????????????????????????????????????????????");
            initData(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        show_Comments0();
//????????????
//        post_get.get(shareId,comments);
//        try {
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        show_Comments();

//***************************************????????????******************************************
        if(hasFocus){
            System.out.println("hasFocus???"+hasFocus);
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

//??????????????????

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

//****************************************????????????*******************************************************
        if(hasLike){
            System.out.println("hasLike???"+hasLike);
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
                    System.out.println("????????????????????? "+likeId);
                    Post.post(shareId,userId);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    get();


                }else{
                    like.setImageResource(R.drawable.ic_baseline_thumb_up_alt_23);
                    System.out.println("???????????????????????????"+likeId);
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

//**********************************????????????******************************************************************
        if(hasCollect){
            System.out.println("hasCollect???"+hasCollect);
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
                    System.out.println("?????????????????????collectId???"+ collectId);
                    Post.post(shareId,userId);
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    get();


                }else{
                    collect.setImageResource(R.drawable.ic_baseline_star_24);
                    System.out.println("???????????????????????????collectId???"+ collectId);
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

//*******************************************????????????????????????****************************************
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
                System.out.println("commentsList?????????"+commentsList.size());
                commentsList.clear();
                System.out.println("commentsList?????????"+commentsList.size());
                System.out.println("??????show");
                show_Comments1();
            }
        });
// -----------------------------------------------------------------------------------------------------
    }


    //?????????????????????????????????
    private void get(){
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String url = "http://47.107.52.7:88/member/photo/share/detail?shareId="+shareId+"&userId="+userId;

                // ?????????
                Headers headers = new Headers.Builder()
                        .add("appId", Constant.APP_ID)
                        .add("appSecret", Constant.APP_SECRET)
                        .add("Accept", "application/json, text/plain, */*")
                        .build();

                //??????????????????
                Request request = new Request.Builder()
                        .url(url)
                        // ???????????????????????????
                        .headers(headers)
                        .get()
                        .build();
                try {
                    OkHttpClient client = new OkHttpClient();
                    //?????????????????????callback????????????
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
     * ??????
     */
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            //TODO ??????????????????
            e.printStackTrace();
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO ??????????????????
            Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
            // ??????????????????json???
            System.out.println("?????????????????????????????????");
            body = response.body().string();
            Log.d("info", body);
            // ??????json???????????????????????????
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

    //??????????????????????????????????????????
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
        System.out.println("????????????????????????");
    }
    //??????????????????????????????????????????
    private void show_Comments1()
    {
        post_get.get(shareId,commentsList);
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
            tvNoneComment.setVisibility(View.GONE);
        System.out.println("commentsList?????????"+commentsList.size());
            CommentsAdater commentsAdater = new CommentsAdater(itemDetails.this,
                    R.layout.comment_item, commentsList);
            ListView lvNewsList = findViewById(R.id.lv_comments_list);
            lvNewsList.setAdapter(commentsAdater);
        System.out.println("???????????????????????????");
    }

    //?????????????????????????????????
    private void initData(String body) throws JSONException {
        System.out.println("??????initData???????????????????????????");
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

        System.out.println("????????????????????????????????????");
    }
    /**
     * http????????????????????????
     * @param <T> ??????
     */
    public static class ResponseBody <T> {

        /**
         * ???????????????
         */
        private int code;
        /**
         * ??????????????????
         */
        private String msg;
        /**
         * ????????????
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