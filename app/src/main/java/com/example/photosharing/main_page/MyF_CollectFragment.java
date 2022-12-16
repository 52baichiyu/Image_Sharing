package com.example.photosharing.main_page;

import android.os.Bundle;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.photosharing.MyAdpter.NewsAdapter_user;
import com.example.photosharing.R;
import com.example.photosharing.constant.Constant;
import com.example.photosharing.jsonpare.data_login;
import com.example.photosharing.my_Date.News_userpaper;
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

///**
// * A simple {@link Fragment} subclass.
// * Use the {@link CollectFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
public class MyF_CollectFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "id";
    //    private static final String ARG_PARAM2 = "param2";
    private List<News_userpaper> newsUserpaperList = new ArrayList<>();
    public static String id;
    NewsAdapter_user newsAdapterUser;
    private String position;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private int flag=1;
//    private String mParam2;

    private data_login data;
    private Bundle bundle = new Bundle();

    private Gson gson = new Gson();

    private boolean sig = true;

    private View test_1;

    private TextView test_2;

    private boolean _isGetData = false;

    public MyF_CollectFragment() {

        // Required empty public constructor
    }


//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment DynamicFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static DynamicFragment newInstance(String param1, String param2) {
//        DynamicFragment fragment = new DynamicFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
////        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bundle = getArguments();
        data = (data_login) bundle.getSerializable("data");
        mParam1 = data.getData().getId();

//        try {
//            //等待get方法执行完在继续执行程序
//            get();
//            TimeUnit.SECONDS.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        System.out.println("Clollter + 121");
        try {
            //等待get方法执行完在继续执行程序
            get();
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        View rootView = inflater.inflate(R.layout.fragment_collect,container,false);
        test_1 = rootView.findViewById(R.id.null_prompt);
        test_2 = rootView.findViewById(R.id.null_prompt_text);
        //使用recyclerView
        RecyclerView recyclerView =  rootView.findViewById(R.id.collect);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        recyclerView.setLayoutManager(layoutManager);
        System.out.println("进入设置适配器");

        newsAdapterUser = new NewsAdapter_user(rootView.getContext(),R.layout.list_item_2, newsUserpaperList,mParam1,flag);//getContext()?
        newsAdapterUser.setOnItemDeleteListener(new NewsAdapter_user.OnItemDeleteListener() {
            @Override
            public void onDelete(int id) {
                newsAdapterUser.remove(id);
            }
        });
        recyclerView.setAdapter(newsAdapterUser);

        return rootView;
    }

//    public void remove(int id,String position){
//        newsList.remove(id);
//        this.position = position;
//        post();
//        newsAdapter.notifyItemRemoved(id);
//        //为了数据同步防止错位
//        newsAdapter.notifyDataSetChanged();
//
//    }


    private void get(){
        new Thread(() -> {

            // url路径
            String url = "http://47.107.52.7:88/member/photo/collect?userId="+mParam1;

            // 请求头
            Headers headers = new Headers.Builder()
                    .add("appId", Constant.APP_ID)//  fdf96a0eb7fd451abcbd4f2509a3309f
                    .add("appSecret", Constant.APP_SECRET)//  778851a88e4675f11429ea6aff0be2d99bf6a
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
//            Type jsonType = new TypeToken<UserFragment.ResponseBody<Object>>(){}.getType();
            // 获取响应体的json串
            String body = response.body().string();
            Log.d("info", body);

            //解析数据，获取数据
            try {
                innit(body);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    };
    private void innit(String body) throws JSONException {
        Type jsonType = new TypeToken<FindFragment_child2.ResponseBody<Object>>() {
        }.getType();
        FindFragment_child2.ResponseBody<Object> dataResponseBody = gson.fromJson(body, jsonType);
        if(dataResponseBody.getData()!=null) {


            if(newsUserpaperList.size()!=0)
            {
                while (newsUserpaperList.size()>0)
                {
                    newsUserpaperList.remove(newsUserpaperList.size()-1);
                }
                System.out.println(newsUserpaperList.size());



            }

        JSONObject jsonObject = new JSONObject(body)
                .getJSONObject("data");
        JSONArray jsonArray = jsonObject.getJSONArray("records");
        System.out.println("获取的新闻个数" + jsonArray.length() + "Clollect 242");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject2 = (JSONObject) jsonArray.opt(i);
            News_userpaper newsUserpaper = new News_userpaper();
            newsUserpaper.setTitle(jsonObject2.getString("title"));
            newsUserpaper.setContent(jsonObject2.getString("content"));
            newsUserpaper.setShareId(jsonObject2.getString("id"));
            newsUserpaper.setCollectId(jsonObject2.getString("collectId"));
            newsUserpaper.setLikeId(jsonObject2.getString("likeId"));
            newsUserpaper.setUsername(jsonObject2.getString("username"));
            newsUserpaper.setCreateTime(jsonObject2.getString("createTime"));
            newsUserpaper.setFocusUserId(jsonObject2.getString("pUserId"));
            newsUserpaper.setHasFocus((jsonObject2.getString("hasFocus")));
            JSONArray imageArray = jsonObject2.getJSONArray("imageUrlList");
            String image = (String) imageArray.opt(0);
            newsUserpaper.setImage(image);
            newsUserpaperList.add(0,newsUserpaper);
//            System.out.println(jsonObject2.getString("collectId")+" " );
        }

        }
        else {
            if (newsUserpaperList.size() != 0) {
                while (newsUserpaperList.size() > 0) {
                    newsUserpaperList.remove(newsUserpaperList.size() - 1);
                }
                //System.out.println(newsList.size());
                test_1.setVisibility(View.VISIBLE);
                test_2.setVisibility(View.VISIBLE);
            }
        }
    }

   /*
    * @description data 同步
    * @param 
   */ 
    
    @Nullable
    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter&&!_isGetData)
        {
            get();
        }
        else {
            _isGetData =false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onPause() {
        super.onPause();
        //跟随生命周期
        _isGetData = false;
    }

    @Override
    public void onResume() {
        if(!_isGetData)
        {

            _isGetData = true;
        }
        super.onResume();
    }

}