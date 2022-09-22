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

public class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<data_image> myNewData;
    private RecyclerView recyclerView;
    private Context context;
    private int resourseId;
    private int resourseId_2;
    private OnItemClickListener listener;


    private static int ADD_IMAGE = 0;
    private static int IMAGE_SIGE = 1;

    List<String> image_Path ;

    public MyAdapter(Context context, int resourseId, int resourseId_2, List<data_image> myNewData , List<String> image_Path) {

        this.image_Path = image_Path;
        this.myNewData = myNewData;
        this.resourseId = resourseId;
        this.context = context;
        this.resourseId_2 = resourseId_2;
    }





    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==0) {
            View view = LayoutInflater.from(context).inflate(resourseId, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.itemView1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(viewHolder.getLayoutPosition()==0)
                    listener.onItemClick(0);
                }
            });
            return viewHolder;
        }
        else  {
            View view = LayoutInflater.from(context).inflate(resourseId_2, parent, false);
            ImageViewHolder viewHolder = new ImageViewHolder(view);
            viewHolder.image_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  if (image_Path!=null)
                  {
                      System.out.println("???");
                      System.out.println(viewHolder.getLayoutPosition()-1);
                      image_Path.remove(viewHolder.getLayoutPosition()-1);
                      myNewData.remove(viewHolder.getLayoutPosition());
                      notifyDataSetChanged();
                      System.out.println(image_Path.size()+"  "+ myNewData.size());
                      //            System.out.println(image_Path.get(viewHolder.getLayoutPosition()-1));
                  }
                }
            });
            return viewHolder;
        }

    }



    public void onBindViewHolder( RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof ViewHolder) {
            data_image data_image = myNewData.get(position);
       //     int a = position;

                if (data_image != null) {
                    ((ViewHolder) holder).itemView1.setImageBitmap(data_image.getImage());
                }

        }
        else
        if (holder instanceof ImageViewHolder)
        {

            data_image data_image = myNewData.get(position);
            int a = position;
                if (data_image != null) {
                    System.out.println(data_image);
                    ((ImageViewHolder) holder).itemView_image.setImageBitmap(data_image.getImage());
                }
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
    static  class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView itemView_image;
        public ImageView image_button;
        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView_image = itemView.findViewById(R.id.imageView_image);
            image_button = itemView.findViewById(R.id.image_button);
        }
    }

    public interface OnItemClickListener{

        void onItemClick(int position);
    }
    @Override
    public int getItemViewType(int position) {

        if(position==0)

        {   System.out.println("????");
            return ADD_IMAGE ;
        }
        else
        return  IMAGE_SIGE;
    }
    public void setOnItemClickListener(OnItemClickListener listener)
    {
        this.listener = listener;
    }
}
