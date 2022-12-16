package com.example.photosharing.MyAdpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.photosharing.R;
import com.example.photosharing.my_Date.News;

import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> implements View.OnClickListener{

    private List<News> mNewsData;
    private Context mContext;
    private int resourceId;
    private RecyclerView recyclerView;
    private OnItemClickListener onItemClickListener;
    int photoIndex=0;

    public NewsAdapter(Context context , int resourceId , List<News> data) {
//        super(context , resourceId , data);
        this.mContext = context;
        this.mNewsData = data;
        this.resourceId = resourceId;
    }


    @Override
    public void onViewRecycled(ViewHolder holder) {
        super.onViewRecycled(holder);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //指定一个父控件
        recyclerView = (RecyclerView) parent;

        View view=LayoutInflater.from(mContext).inflate(resourceId,parent,false);
        view.setOnClickListener(this);//绑定监听点击事件
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        News news=mNewsData.get(position);
        holder.tvTitle.setText(news.getTitle());
        holder.tvContent.setText(news.getContent());
        if(news.getImageArray()!=null)
            System.out.println(news.getImageArray()[0]);
        Glide.with(mContext)
                .load(news.getImageArray()[0])
                .placeholder(R.drawable.ic_baseline_sync_24)
                .error(R.drawable.ic_baseline_error_24)
//                .fitCenter()
                .into(holder.ivImage);

//        Glide.with(mContext).load(news.getImageArray()[0]).into(holder.ivImage);
//        ImageView iv_lastPhoto=view.findViewById(R.id.iv_lastPhoto);
        ImageView iv_lastPhoto=holder.ivLastPhoto;

        iv_lastPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("图片切换："+position);
                for(int i=0;i<news.getImageArray().length;i++){
                    System.out.println(news.getImageArray()[i]);
                }

                if(photoIndex==0){
                    photoIndex=news.getImageArray().length-1;
                    System.out.println(photoIndex);
                }
                else {
                    photoIndex=photoIndex-1;
                    System.out.println(photoIndex);
                }
                if(news.getImageArray().length>1)
                    Glide.with(mContext).load(news.getImageArray()[photoIndex]).into(holder.ivImage);
            }
        });

        ImageView iv_nextPhoto=holder.ivNextPhoto;
        iv_nextPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("图片切换："+position);
                for(int i=0;i<news.getImageArray().length;i++){
                    System.out.println(news.getImageArray()[i]);
                }
//                int photoIndex=0;
                if(photoIndex==news.getImageArray().length-1){
                    photoIndex=0;
                    System.out.println(photoIndex);
                }
                else {
                    photoIndex=photoIndex+1;
                    System.out.println(photoIndex);
                }
                if(news.getImageArray().length>1)
                    Glide.with(mContext).load(news.getImageArray()[photoIndex]).into(holder.ivImage);
            }
        });



        holder.tvShareId.setText(news.getShareId());
        holder.tvImageDetails.setText(news.getImage());
//        holder.tvimageArray.setText(news.getImageArray().toString());
//        System.out.println("aaaaaaaaaaaaa"+news.getImageArray());
        holder.tvUsername.setText(news.getUsername());
        holder.tvCreateTime.setText(news.getCreateTime());
        holder.tvfocusUserId.setText(news.getFocusUserId());
//        holder.tvhasFocus.setText(news.getHasFocus());
//        if(news.getHasFocus().equals("true")){
//            System.out.println("该item的hasFocus为"+news.getHasFocus());
//            holder.btFocus.setBackgroundColor(getResources().getColor(R.color.black));
//        }
    }

    @Override
    public int getItemCount() {
        return mNewsData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTitle;
        TextView tvContent;
        ImageView ivImage;
        TextView tvShareId;
        TextView tvImageDetails;

        TextView tvUsername;
        TextView tvCreateTime;
        TextView tvfocusUserId;
        ImageView ivLastPhoto;
        ImageView ivNextPhoto;
//        TextView tvimageArray;
//        TextView tvhasFocus;
        Button btFocus;
        public ViewHolder(View view){
            super(view);
            System.out.println("重新绑定数据");
            tvTitle=view.findViewById(R.id.tv_title);
            tvContent=view.findViewById(R.id.tv_content);
            ivImage=view.findViewById(R.id.iv_image);
            tvShareId=view.findViewById(R.id.tv_shareId);
            tvImageDetails=view.findViewById(R.id.tv_imageDetails);
//            tvimageArray=view.findViewById(R.id.tv_imageArray);
            tvUsername=view.findViewById(R.id.tv_username);
            tvCreateTime=view.findViewById(R.id.tv_createTime);
            tvfocusUserId=view.findViewById(R.id.tv_focusUserId);
//            tvhasFocus=view.findViewById(R.id.tv_tvhasFocus);
            btFocus=view.findViewById(R.id.bt_focus);
            ivLastPhoto=view.findViewById(R.id.iv_lastPhoto);
            ivNextPhoto=view.findViewById(R.id.iv_nextPhoto);
        }
    }
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        News news = (News) getItem(position);
//        View view ;
//        i++;
//        System.out.println("getView:"+i+"位置："+position);
//        final ViewHolder vh;
//
//        if (convertView == null) {
//            view = LayoutInflater.from(getContext()).inflate(resourceId , parent , false);
//            vh = new ViewHolder();
//            vh.tvTitle = view.findViewById(R.id.tv_title);
//            vh.tvContent = view.findViewById(R.id.tv_content);
//            vh.ivImage = view.findViewById(R.id.iv_image);
//            vh.tvShareId=view.findViewById(R.id.tv_shareId);
//            vh.tvImageDetails=view.findViewById(R.id.tv_imageDetails);
//
//            view.setTag(vh);
//        } else {
//            view = convertView;
//            vh = (ViewHolder) view.getTag();
//        }
//        vh.tvTitle.setText(news.getTitle());
//        vh.tvContent.setText(news.getContent());
//        Glide.with(mContext).load(news.getImage()).into(vh.ivImage);
//        vh.tvShareId.setText(news.getShareId());
//        vh.tvImageDetails.setText(news.getImage());
//        return view;
//    }
//
//    class ViewHolder{
//        TextView tvTitle;
//        TextView tvContent;
//        ImageView ivImage;
//        TextView tvShareId;
//        TextView tvImageDetails;
//    }

    //重载点击事件，指定点击事件时执行自定义的onItemClick
    @Override
    public void onClick(View view) {
        //此时就用到上面拉出来的那个 rvParent 了
        int position = recyclerView.getChildAdapterPosition(view);
        if (onItemClickListener != null)
            onItemClickListener.onItemClick(recyclerView, view, position);
    }
    //设置回调接口
    public interface OnItemClickListener{
        //参数(父组件，当前单击的View,单击的View的位置，数据)
        void onItemClick(RecyclerView parent, View view, int position);
    }

    //实例化自定义接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){

        this.onItemClickListener = onItemClickListener;

    }
}
