package com.example.photosharing.main_page;

import android.content.Intent;
import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.photosharing.MyAdpter.NewsAdapter;
import com.example.photosharing.R;
import com.example.photosharing.constant.Constant;
import com.example.photosharing.fornt_find.itemDetails;
import com.example.photosharing.my_Date.News;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FindFragment_child extends Fragment implements View.OnClickListener,View.OnTouchListener{

    private  String body;
    private String userId;
    private String userName;
    private Boolean hasFocus=false;
    private Boolean focusSwitch=false;
    private int Position=-1;
    private Gson gson = new Gson();

    private NewsAdapter newsAdapter=null;
    private ImageView lastPhoto;
    private ImageView nextPhoto;
    private List<News> newsList=new ArrayList<>();
    private SwipeRefreshLayout swipe;
    private int current=0;

    private View test_1;
    private TextView test_2;


    public static final String MESSAGE_SHAREID=
            "shareId";
    public static final String MESSAGE_USERID=
            "userId";
    public static final String MESSAGE_IMAGE=
            "imageUrl";
    public static final String MESSAGE_USERNAME=
            "userName";
    //    ??????????????????????????????
    public FindFragment_child() {

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        test_1 = view.findViewById(R.id.null_prompt);
        test_2 = view.findViewById(R.id.null_prompt_text);

        Bundle bundle = getArguments();
        userId = bundle.getString("id");
        userName = bundle.getString("userName");

        System.out.println(getActivity()+"!");
        System.out.println("????????????!");
        get();

        try {
            System.out.println("????????????!");
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("??????????????????!");
        try {
            System.out.println("initData?????????????????????newsList");
            initData(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RecyclerView recyclerView = view.findViewById(R.id.lv_news_list);
        System.out.println("newsList????????????"+newsList);
        newsAdapter = new NewsAdapter(getActivity(),
                R.layout.list_item,
                newsList
        );
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        System.out.println(recyclerView);
        recyclerView.setLayoutManager(llm);
        recyclerView.setAdapter(newsAdapter);
        swipe=view.findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){

            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        newsAdapter.setOnItemClickListener(new NewsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {

//******************************???????????????????????????*****************************
                News news=newsList.get(position);
                ImageView iv_image=view.findViewById(R.id.iv_image);
                iv_image.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView tvShareId=view.findViewById(R.id.tv_shareId);
                        String shareId=tvShareId.getText().toString();


                        String [] imageArray = new String[news.getImageArray().length];
                        imageArray=news.getImageArray();

                        System.out.println("shareId???"+shareId+"  userId???"+userId);
                        //!!
                        Intent intent=new Intent(getActivity(),
                                itemDetails.class);
                        intent.putExtra(MESSAGE_SHAREID,shareId);
                        intent.putExtra(MESSAGE_USERID,userId);
//                        intent.putExtra(MESSAGE_IMAGE,imageDetails);
                        intent.putExtra(MESSAGE_IMAGE,imageArray);
                        intent.putExtra(MESSAGE_USERNAME,userName);
                        startActivity(intent);
                    }
                });

            }
        });
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
            Type jsonType = new TypeToken<FindFragment_child.ResponseBody<Object>>() {
            }.getType();
            // ??????????????????json???
            body = response.body().string();
            System.out.println("????????????????????????");
            Log.d("info", body);
            // ??????json???????????????????????????
            FindFragment_child.ResponseBody<Object> dataResponseBody = gson.fromJson(body, jsonType);
            Log.d("info", dataResponseBody.toString());
            if (dataResponseBody.getData()!=null) {
//**********************????????????hasFocus************************
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
                if (Position != -1) {
                    JSONObject jsonObject2 = (JSONObject) jsonArray.opt(Position);

                    try {
                        hasFocus = Boolean.valueOf(jsonObject2.getString("hasFocus"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("???????????????????????????hasFocus???" + hasFocus + " position??????" + Position);
//--------------------------------------------------------------------------------------
            }
        }
    };

    private void refreshData(){
        get();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            initData(body);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        newsAdapter.notifyDataSetChanged();
        swipe.setRefreshing(false);
    }
    private void get(){
        System.out.println("????????????get()??????????????????");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                // url??????
                String url = "http://47.107.52.7:88/member/photo/share?current="+current+"&size=20&userId="+userId;
                current=current+10;
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

    private void initData(String body) throws JSONException {
        Type jsonType = new TypeToken<FindFragment_child2.ResponseBody<Object>>() {
        }.getType();
        FindFragment_child2.ResponseBody<Object> dataResponseBody = gson.fromJson(body, jsonType);
        if(dataResponseBody.getData()!=null) {
            test_1.setVisibility(View.GONE);
            test_2.setVisibility(View.GONE);

            JSONObject jsonObject = new JSONObject(body.toString())
                    .getJSONObject("data");
            JSONArray jsonArray = jsonObject.getJSONArray("records");
            System.out.println("?????????????????????" + jsonArray.length());

            for (int i = 0; i < jsonArray.length(); i = i + 2) {
                JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
                News news = new News();
                news.setTitle(jsonObject2.getString("title"));
                news.setContent(jsonObject2.getString("content"));
                news.setShareId(jsonObject2.getString("id"));
                news.setLikeId(jsonObject2.getString("likeId"));
                news.setUsername(jsonObject2.getString("username"));
                news.setCreateTime(jsonObject2.getString("createTime"));
                news.setFocusUserId(jsonObject2.getString("pUserId"));
                news.setHasFocus((jsonObject2.getString("hasFocus")));
                JSONArray imageArray = jsonObject2.getJSONArray("imageUrlList");
                System.out.println("?????????" + imageArray.length());
                String image = (String) imageArray.opt(0);
                if(image!=null)
                {

                    System.out.println("??? 299");
                String[] ImageArray = new String[imageArray.length()];
                for (int j = 0; j < imageArray.length(); j++) {
                    ImageArray[j] = (String) imageArray.opt(j);
                    System.out.println(ImageArray[j]);
                }


                news.setImage(image);

                news.setImageArray(ImageArray);
                newsList.add(0, news);
            }
            }

            News news;
            System.out.println("newsList????????????" + newsList.size());
            for (int j = 0; j < newsList.size(); j++) {

                news = newsList.get(j);
                System.out.println("newslist:" + news.getShareId() + " " + news.getTitle() + " " + news.getContent() + " " + news.getImage() + " " + "imageArray:" + news.getImageArray());

            }

        }
        else {
            if (newsList.size() != 0) {
                while (newsList.size() > 0) {
                    newsList.remove(newsList.size() - 1);
                }
                System.out.println(newsList.size());

                test_1.setVisibility(View.VISIBLE);
                test_2.setVisibility(View.VISIBLE);
            }
        }
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


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_find_child1, container, false);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}