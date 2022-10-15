package com.example.photosharing.fornt_find;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.photosharing.R;

import java.util.List;

public class CommentsAdater extends ArrayAdapter<Comments> {
    private List<Comments> mCommentsData;
    private Context mContext;
    private int resourceId;

    public CommentsAdater(@NonNull Context context , int resourceId , List<Comments> data) {
        super(context, resourceId,data);
        this.mContext = context;
        this.mCommentsData = data;
        this.resourceId = resourceId;
    }

//    @NonNull
//    @Override
//    public CommentsAdater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view= LayoutInflater.from(mContext).inflate(resourceId,parent,false);
//        CommentsAdater.ViewHolder holder=new CommentsAdater.ViewHolder(view);
//        return holder;
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull CommentsAdater.ViewHolder holder, int position) {
//        Comments comments=mCommentsData.get(position);
//        holder.tvContent.setText(comments.getContent());
//        holder.tvUsername.setText(comments.getUserName());
//        holder.tvCreateTime.setText(comments.getCreateTime());
//    }
//
//    @Override
//    public int getItemCount() {
//        return mCommentsData.size();
//    }
//    public static class ViewHolder extends RecyclerView.ViewHolder{
//        TextView tvContent;
//        TextView tvUsername;
//        TextView tvCreateTime;
//
//        public ViewHolder(@NonNull View view) {
//            super(view);
//            tvContent=view.findViewById(R.id.tv_content);
//            tvUsername=view.findViewById(R.id.tv_userName);
//            tvCreateTime=view.findViewById(R.id.tv_createTime);
//        }
//    }
@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
    Comments comments=getItem(position);
    View view;

    ViewHolder holder;
    if(convertView==null){
        view= LayoutInflater.from(getContext()).inflate(resourceId,parent,false);

        holder=new ViewHolder();
        holder.tvContent=view.findViewById(R.id.tv_content);
       holder.tvUsername=view.findViewById(R.id.tv_userName);
           holder.tvCreateTime=view.findViewById(R.id.tv_createTime);
        view.setTag( holder);
    }else{
        view=convertView;
        holder=(ViewHolder)view.getTag();
    }
      holder.tvContent.setText(comments.getContent());
      holder.tvUsername.setText(comments.getUserName());
      holder.tvCreateTime.setText(comments.getCreateTime());
    return view;
}
    class ViewHolder{
        TextView tvContent;
       TextView tvUsername;
       TextView tvCreateTime;
    }
}
