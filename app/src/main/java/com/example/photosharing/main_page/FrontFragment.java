package com.example.photosharing.main_page;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.example.photosharing.MyAdpter.MyAdapter;
import com.example.photosharing.R;
import com.example.photosharing.jsonpare.Data_Path;
import com.example.photosharing.jsonpare.data_image;
import com.example.photosharing.main.App_Main;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class FrontFragment extends Fragment {

    Uri uri;
    List<String> image_Path = new ArrayList<>();
    private File file_Image;

    private int MAX_DATE = 5;

    private int sig_Number =0;

    private MyAdapter myAdapter = null;
    private RecyclerView recyclerView;

    private List<com.example.photosharing.jsonpare.data_image> newslist = new ArrayList<>();

    private data_image data_image;

    private String[] Array = new String[MAX_DATE];
    Button image_button;
    ImageView imageView;


    public FrontFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        }


/**
 * @description 上传文件路径生成RequestBody
 * @param "image_Path" 文件路径集合
 * @return builder.build() builider请求
 * @author 余
 * @time 2022/9/5 18:29
 */



        private static RequestBody getRequestBody(List<String> file_Image)
        {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for(int a = 0 ; a < file_Image.size();a++)
            {
                File file = new File(file_Image.get(a));
                RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), file);
                builder.addFormDataPart(
                        "fileList",
                        file.getName(),
                        requestBody
                );
            }

            return builder.build();
        }





        /**
         * Get the value of the data column for this Uri. This is useful for
         * MediaStore Uris, and other file-based ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         */
        private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return null;
        }

//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is ExternalStorageProvider.
//     */
//    private static boolean isExternalStorageDocument(Uri uri) {
//        return "com.android.externalstorage.documents".equals(uri.getAuthority());
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is DownloadsProvider.
//     */
//    private static boolean isDownloadsDocument(Uri uri) {
//        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
//    }
//
//    /**
//     * @param uri The Uri to check.
//     * @return Whether the Uri authority is MediaProvider.
//     */
//    private static boolean isMediaDocument(Uri uri) {
//        return "com.android.providers.media.documents".equals(uri.getAuthority());
//    }


        /*
         * @description 活动回调
         * @param
         */

        @Override
        public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if(requestCode==2)
            {
                if(data != null)
                {
                    uri = data.getData();
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getActivity().getContentResolver().openInputStream(uri));
                        if(bitmap!=null)
                        {
                            data_image = new data_image(bitmap);
                            newslist.add(data_image);
                            myAdapter.notifyItemInserted(0);

                            myAdapter.notifyItemChanged(0, newslist.size());
                            myAdapter.notifyDataSetChanged();


                            Array[sig_Number] = uri.toString();

                            image_Path.add(getDataColumn(getActivity(),uri,null,null));

                            sig_Number++;

                            ///   System.out.println(Uri.parse(MediaStore.Images.Media.insertImage(getContentResolver(),bitmap,null,null)));
                            System.out.println(getDataColumn(getActivity(),uri,null,null));





                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }


                }
            }
        }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_from, container, false);

        recyclerView = view.findViewById(R.id.lvNews);
        myAdapter = new MyAdapter(getActivity(),R.layout.item_list,newslist);

        image_button = view.findViewById(R.id.button2);

        imageView = view.findViewById(R.id.imageView1);

//        if (Build.VERSION.SDK_INT >= 23) {
//            int REQUEST_CODE_CONTACT = 101;
//            String[] permissions = {
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
//            //验证是否许可权限
//            for (String str : permissions) {
//                if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
//                    //申请权限
//                    getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);
//                    return view;
//                } else {
//                    //这里就是权限打开之后自己要操作的逻辑



//        if(data_image!=null) {
//            newslist.add(0, data_image);
//        }
//        else
//        {
//            System.out.println("???");
//        }

        /*
         * @description text
         * @param
         */
        image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient okHttpClient = new OkHttpClient();

                Headers headers = new Headers.Builder()
                        .add("appId","fdf96a0eb7fd451abcbd4f2509a3309f")
                        .add("appSecret","778851a88e4675f11429ea6aff0be2d99bf6a")
                        .build();

                String uri = "http://47.107.52.7:88/member/photo/image/upload";

                Request request = new Request.Builder()
                        .url(uri)
                        .headers(headers)
                        .post(getRequestBody(image_Path))
                        .build();

                //封装requset
                Call call = okHttpClient.newCall(request);

                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        System.out.println( e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String res = response.body().string();
                       getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ObjectMapper objectMapper = new ObjectMapper();

                                Data_Path data_path = null;
                                System.out.println(res);
//                                try {
//                                    data_path = objectMapper.readValue(res,Data_Path.class);
//                                } catch (JsonProcessingException e) {
//                                    e.printStackTrace();
//                                }
//                                if(data_path!=null)
//                                {
//                                    System.out.println(data_path.getData().getImageCode());
//                                    System.out.println(data_path.getData().getImageUrlList().get(0));
//                                }

                            }
                        });
                    }
                });

            }
        });

           if(myAdapter.getItemCount()<1) {
               newslist.add(0, data_image);
               myAdapter.notifyItemInserted(0);
//
               myAdapter.notifyItemChanged(0, newslist.size() - 0);
               myAdapter.notifyDataSetChanged();
           }



        recyclerView = view.findViewById(R.id.lvNews);
        myAdapter = new MyAdapter(getActivity(),R.layout.item_list,newslist);

//        LinearLayoutManager llm = new LinearLayoutManager(this);
//         recyclerView.setLayoutManager(llm);
        GridLayoutManager grm = new GridLayoutManager(getActivity(),4);
        recyclerView.setLayoutManager(grm);
        recyclerView.setAdapter(myAdapter);


        /*
         * @description 动态申请权限
         * @param 
        */
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {
                    Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (getActivity().checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    getActivity().requestPermissions(permissions, REQUEST_CODE_CONTACT);
                    return view;
                } else {
                    //这里就是权限打开之后自己要操作的逻辑
        
        myAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(position==0)
                {
                    Intent intent = new Intent(Intent.ACTION_PICK,null);
                    intent.setDataAndType(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,"image/*");
                    startActivityForResult(intent,2);
                }
            }
        });


                }
            }
        }

        return view;
    }


}