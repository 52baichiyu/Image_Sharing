package com.example.photosharing.MyAdpter;/*
 *@author: 余
 *@date: 2022/9/3
 *@substance:
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.photosharing.R;
import com.example.photosharing.jsonpare.data_image;

import java.util.List;

public class MyAdapter_0 extends RecyclerView.Adapter<MyAdapter_0.ViewHolder> {
    private final List<data_image> myNewData;
    private RecyclerView recyclerView;
    private final Context context;
    private final int resourseId;
    private OnItemClickListener listener;


    public MyAdapter_0(Context context, int resourseId, List<data_image> myNewData) {

        this.myNewData = myNewData;
        this.resourseId = resourseId;
        this.context = context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(resourseId,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.itemView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(0);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
                 data_image data_image = myNewData.get(position);
                 int a = position;
                 if(data_image!=null) {
                     holder.itemView1.setImageBitmap(data_image.getImage());

                 }
    }

    @Override
    public int getItemCount() {
        return myNewData.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView itemView1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
             itemView1 = itemView.findViewById(R.id.imageView1);
        }
    }


    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }

}
