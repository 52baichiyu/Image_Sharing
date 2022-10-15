package com.example.photosharing.fornt_find;

import android.os.NetworkOnMainThreadException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.photosharing.constant.Constant;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class commentFirst_POST_GET {
    /**
     * @description 评论方法
     * @param 
     * @return 
     * @author 余
     * @time 2022/10/15 8:27
     */
    private List<Comments> commentsList=new ArrayList<>();
    private Gson gson=new Gson();

    public void post(String content,String shareId,String userId,String userName){
        System.out.println("发表评论接口开始");
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/photo/comment/first";

            // 请求头
            Headers headers = new Headers.Builder()
                    .add("appId", Constant.APP_ID)
                    .add("appSecret", Constant.APP_SECRET)
                    .add("Accept", "application/json, text/plain, */*")
                    .build();

            // 请求体
            // PS.用户也可以选择自定义一个实体类，然后使用类似fastjson的工具获取json串
            Map<String, Object> bodyMap = new HashMap<>();
            bodyMap.put("content", content);
            bodyMap.put("shareId", shareId);
            bodyMap.put("userId", userId);
            bodyMap.put("userName", userName);
            // 将Map转换为字符串类型加入请求体中
            String body = gson.toJson(bodyMap);

            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .post(RequestBody.create(MEDIA_TYPE_JSON, body))
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                //发起请求，传入callback进行回调
                client.newCall(request).enqueue(callback);
            }catch (NetworkOnMainThreadException ex){
                ex.printStackTrace();
            }
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
            Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
            // 获取响应体的json串
            String body = response.body().string();
            Log.d("info", body);
            System.out.println("评论成功！");
            // 解析json串到自己封装的状态
//            ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);
//            Log.d("info", dataResponseBody.toString());
        }
    };

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

    public void get(String shareId, List<Comments> commentList){
        System.out.println("返回评论接口开始");
        commentsList=commentList;
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/photo/comment/first?shareId="+shareId;

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
                client.newCall(request).enqueue(callback1);
            }catch (NetworkOnMainThreadException ex){
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * 回调
     */
    private final Callback callback1 = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            //TODO 请求失败处理
            e.printStackTrace();
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO 请求成功处理
            Type jsonType = new TypeToken<ResponseBody1<Object>>(){}.getType();
            // 获取响应体的json串
            String body = response.body().string();
            Log.d("info", body);
            // 解析json串到自己封装的状态
//            ResponseBody1<Object> dataResponseBody = gson.fromJson(body,jsonType);
//            Log.d("info", dataResponseBody.toString());
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(body.toString())
                        .getJSONObject("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONArray jsonArray = null;
            try {
                jsonArray = jsonObject.getJSONArray("records");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            System.out.println("获取的评论个数"+jsonArray.length());

            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                Comments comments = new Comments();
                try {
                    comments.setUserName(jsonObject2.getString("userName"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    comments.setContent(jsonObject2.getString("content"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    comments.setCreateTime(jsonObject2.getString("createTime").substring(0, 11));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                commentsList.add(comments);
            }
            System.out.println("返回评论成功");
        }
    };

    /**
     * http响应体的封装协议
     * @param <T> 泛型
     */
    public static class ResponseBody1 <T> {

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

        public ResponseBody1(){}

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
