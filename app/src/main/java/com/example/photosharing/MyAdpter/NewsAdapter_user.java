package com.example.photosharing.MyAdpter;

import android.content.Context;
import android.os.NetworkOnMainThreadException;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photosharing.R;
import com.example.photosharing.constant.Constant;
import com.example.photosharing.my_Date.News_userpaper;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class NewsAdapter_user extends RecyclerView.Adapter<NewsAdapter_user.ViewHolder> {

    private List<News_userpaper> newsUserpaperList;
    private Context mcontext;
    private OnItemDeleteListener listener = null;
    private RecyclerView recyclerView;
    private int resourceId;
    private String aboutId;
    private String UserId;
    private int flag;

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView activeImage;
        ImageView delete_image;
//        TextView activeName;

        public ViewHolder(View view) {
            super(view);
            activeImage = view.findViewById(R.id.image);
            delete_image = view.findViewById(R.id.close_image);
//            activeName = view.findViewById(R.id.active_name);

        }

    }

    public NewsAdapter_user(Context context, int resourceId, List<News_userpaper> newsUserpaperList, String UserId, int flag) {
        mcontext = context;
        this.newsUserpaperList = newsUserpaperList;
        this.resourceId = resourceId;
        this.UserId = UserId;
        this.flag = flag;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mcontext).inflate(R.layout.list_item_1, parent, false);
        ViewHolder holder = new ViewHolder(view);

//        int id = holder.getAbsoluteAdapterPosition();
//        News news = newsList.get(holder.getAbsoluteAdapterPosition());
//        shareId = news.getShareId();


        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        News_userpaper newsUserpaper = newsUserpaperList.get(position);
        int id = holder.getAbsoluteAdapterPosition();
//        News news = newsList.get(holder.getAbsoluteAdapterPosition());
//        shareId = news.getShareId();

        holder.delete_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                News_userpaper newsUserpaper = newsUserpaperList.get(holder.getAbsoluteAdapterPosition());
                switch (flag){
                    case 0:{
                        aboutId = newsUserpaper.getShareId();
                        System.out.println("进入获取动态的该item的id");
                        break;
                    }
                    case 1:{
                        aboutId = newsUserpaper.getCollectId();
                        System.out.println("进入获取收藏的该item的id");
                        break;
                    }

                    case 2:
                        aboutId = newsUserpaper.getLikeId();
                        System.out.println("进入获取点赞的该item的id");
                        break;

                }
                System.out.println(aboutId);
                post(flag);
                if(listener != null && newsUserpaperList.size() != 0){
//                    System.out.println("该item位置是："+holder.getAbsoluteAdapterPosition()+"id值是："+id+"position值是："+position);
                    listener.onDelete(holder.getAbsoluteAdapterPosition());
                }
            }
        });

        //加载图片
        Glide.with(mcontext).load(newsUserpaper.getImage()).into(holder.activeImage);
//        holder.activeImage.setImageResource(news.getImage());
//        holder.activeName.setText(active.getName());
    }

    @Override
    public int getItemCount() {
        return newsUserpaperList.size();
    }


//    public View getView(int position , View convertView , ViewGroup parent){
//
//    }



    //删除操作
    //定义接口
    public interface OnItemDeleteListener{
        void onDelete(int id);
    }

    public void setOnItemDeleteListener (
            OnItemDeleteListener listener) {
        this.listener = listener;
    }

    public void remove(int id){
        newsUserpaperList.remove(id);
        notifyItemRemoved(id);
        //为了数据同步防止错位
        notifyDataSetChanged();

    }

    private void post(int flag){
        new Thread(() -> {

//
            // url路径
            System.out.println(UserId);
            System.out.println(aboutId);

            String url = "http://47.107.52.7:88/member/photo";
            switch (flag){
                case 0:{
                    url = "http://47.107.52.7:88/member/photo/share/delete?shareId="+aboutId+"&userId="+UserId;
                    System.out.println("进入动态删除");
                    break;
                }
                case 1:{
                    url = "http://47.107.52.7:88/member/photo/collect/cancel?collectId="+aboutId;
                    System.out.println("进入收藏删除");
                    break;
                }

                case 2:
                    url = "http://47.107.52.7:88/member/photo/like/cancel?likeId="+aboutId;
                    break;

            }


            System.out.println("进入删除接口");
            // 请求头
            Headers headers = new Headers.Builder()
                    .add("appId", Constant.APP_ID)
                    .add("appSecret", Constant.APP_SECRET)
                    .add("Accept", "application/json, text/plain, */*")
                    .build();


            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

            //请求组合创建
            Request request = new Request.Builder()
                    .url(url)
                    // 将请求头加至请求中
                    .headers(headers)
                    .post(RequestBody.create(MEDIA_TYPE_JSON, ""))
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
            System.out.println("删除接口失败回调");
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO 请求成功处理
//            Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
            // 获取响应体的json串
            String body = response.body().string();
            Log.d("info", body);
            System.out.println("删除接口成功回调");
            // 解析json串到自己封装的状态
//            ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);
//            Log.d("info", dataResponseBody.toString());
        }
    };

}

