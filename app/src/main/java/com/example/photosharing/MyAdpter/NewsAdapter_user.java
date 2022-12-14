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
                        System.out.println("????????????????????????item???id");
                        break;
                    }
                    case 1:{
                        aboutId = newsUserpaper.getCollectId();
                        System.out.println("????????????????????????item???id");
                        break;
                    }

                    case 2:
                        aboutId = newsUserpaper.getLikeId();
                        System.out.println("????????????????????????item???id");
                        break;

                }
                System.out.println(aboutId);
                post(flag);
                if(listener != null && newsUserpaperList.size() != 0){
//                    System.out.println("???item????????????"+holder.getAbsoluteAdapterPosition()+"id?????????"+id+"position?????????"+position);
                    listener.onDelete(holder.getAbsoluteAdapterPosition());
                }
            }
        });

        //????????????
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



    //????????????
    //????????????
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
        //??????????????????????????????
        notifyDataSetChanged();

    }

    private void post(int flag){
        new Thread(() -> {

//
            // url??????
            System.out.println(UserId);
            System.out.println(aboutId);

            String url = "http://47.107.52.7:88/member/photo";
            switch (flag){
                case 0:{
                    url = "http://47.107.52.7:88/member/photo/share/delete?shareId="+aboutId+"&userId="+UserId;
                    System.out.println("??????????????????");
                    break;
                }
                case 1:{
                    url = "http://47.107.52.7:88/member/photo/collect/cancel?collectId="+aboutId;
                    System.out.println("??????????????????");
                    break;
                }

                case 2:
                    url = "http://47.107.52.7:88/member/photo/like/cancel?likeId="+aboutId;
                    System.out.println("??????????????????");
                    break;

            }


            System.out.println("??????????????????");
            // ?????????
            Headers headers = new Headers.Builder()
                    .add("appId", Constant.APP_ID)
                    .add("appSecret", Constant.APP_SECRET)
                    .add("Accept", "application/json, text/plain, */*")
                    .build();


            MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");

            //??????????????????
            Request request = new Request.Builder()
                    .url(url)
                    // ???????????????????????????
                    .headers(headers)
                    .post(RequestBody.create(MEDIA_TYPE_JSON, ""))
                    .build();
            try {
                OkHttpClient client = new OkHttpClient();
                //?????????????????????callback????????????
                client.newCall(request).enqueue(callback);
            }catch (NetworkOnMainThreadException ex){
                ex.printStackTrace();
            }
        }).start();
    }

    /**
     * ??????
     */
    private final Callback callback = new Callback() {
        @Override
        public void onFailure(@NonNull Call call, IOException e) {
            //TODO ??????????????????
            e.printStackTrace();
            System.out.println("????????????????????????");
        }
        @Override
        public void onResponse(@NonNull Call call, Response response) throws IOException {
            //TODO ??????????????????
//            Type jsonType = new TypeToken<ResponseBody<Object>>(){}.getType();
            // ??????????????????json???
            String body = response.body().string();
            Log.d("info", body);
            System.out.println("????????????????????????");
            // ??????json???????????????????????????
//            ResponseBody<Object> dataResponseBody = gson.fromJson(body,jsonType);
//            Log.d("info", dataResponseBody.toString());
        }
    };

}

