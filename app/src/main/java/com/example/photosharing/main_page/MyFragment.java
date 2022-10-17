package com.example.photosharing.main_page;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.photosharing.Personal_center.Edit_imformation;
import com.example.photosharing.Personal_center.Personnal_center;
import com.example.photosharing.R;
import com.example.photosharing.jsonpare.data_login;
import com.example.photosharing.my_Date.News_userpaper;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.

 * create an instance of this fragment.
 */
public class MyFragment extends Fragment {
    private static String Id ;
    private static String  apkId;

    private static String _userName;
    private static String _userSex;
    private static String _userIntrod;
    private static String _userAvatar;

    private static data_login mParam1;

    Bundle bundle = new Bundle();

    private ImageView _headerImage;
    private TextView userName;
    private TextView id;
    private Button edit;
    private ImageView sex;
    private TextView introduction;
    private final Gson gson = new Gson();
    private List<News_userpaper> newsUserpaperList = new ArrayList<>();


    private MyF_DynamicFragment dynamicFragment = new MyF_DynamicFragment();
    private MyF_CollectFragment myFCollectFragment = new MyF_CollectFragment();
    private MyF_DianzanFragment dianzanFragment = new MyF_DianzanFragment();

    TabLayout mTableLayout;
    private ViewPager mViewPager;
    private MyAdapter2 adapter;
    private List<String> mTitle;
    private List<Fragment> mFragment;

    public MyFragment() {

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mTableLayout=view.findViewById(R.id.my_2);
        mViewPager=view.findViewById(R.id.my_paper1);
        //标题栏数组
        mTitle = new ArrayList<>();
        mTitle.add("动态");
        mTitle.add("收藏");
        mTitle.add("点赞");
        //fragment集合
        mFragment = new ArrayList<>();
        mFragment.add(new MyF_DynamicFragment());
        mFragment.add(new MyF_CollectFragment());
        mFragment.add(new MyF_DianzanFragment());
        //在activity中使用 getSupportFragmentManager(),这里是Fragment中使用如下方法
        adapter = new MyAdapter2(getFragmentManager());
        System.out.println(mViewPager+"!!");
        mViewPager.setAdapter(adapter);
        //将TabLayout和ViewPager绑定在一起，一个动另一个也会跟着动
        mTableLayout.setupWithViewPager(mViewPager);


    }
    class MyAdapter2 extends FragmentPagerAdapter {

        public MyAdapter2(FragmentManager fm) {
            super(fm);
        }
        //获得每个页面的下标

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mFragment.get(position);

            Bundle bundle = new Bundle();
            bundle.putSerializable("data",mParam1);

            fragment.setArguments(bundle);
            return mFragment.get(position);
        }
        //获得List的大小
        @Override
        public int getCount() {
            return mFragment.size();
        }
        //获取title的下标
        @Override
        public CharSequence getPageTitle(int position) {
            return mTitle.get(position);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("创建UserFragment");
        if (getArguments() != null) {
            bundle = getArguments();
            mParam1 = (data_login) bundle.getSerializable("data");
            //在不需点击跳转的情况下，使用静态变量向子fragement传递数据（用户id）
            System.out.println(mParam1+"!");
        }

    }


        @SuppressLint("CheckResult")
        @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
            System.out.println("创建、绘制该UserFragment的View组件");
            View rootView = inflater.inflate(R.layout.fragment_my, container, false);
            userName = rootView.findViewById(R.id.userName);
            id = rootView.findViewById(R.id.ID);
            edit = rootView.findViewById(R.id.editing);
            sex = rootView.findViewById(R.id.Sex);
            introduction = rootView.findViewById(R.id.introduction);
            _headerImage =rootView.findViewById(R.id.User_Pictrue1);


            //显示信息
            System.out.println(mParam1+"!!!");
            Glide.with(this).load(mParam1.getData().getAvatar()).into(_headerImage);
            userName.setText(mParam1.getData().getUsername());
         //   id.setText(mParam1.getData().getUsername());
           if (mParam1.getData().getSex()!=null)
            if(mParam1.getData().getSex().equals("0"))
                sex.setImageResource(R.drawable.girl);
            else
                sex.setImageResource(R.drawable.boy);
            else sex.setImageResource(R.drawable.boy);
            if(mParam1.getData().getIntroduce() != null)
                introduction.setText(mParam1.getData().getIntroduce());

           //个人中心
            edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

//                    Intent intent = new Intent(getActivity(), Edit_imformation.class);
                    Intent intent = new Intent(getActivity(), Personnal_center.class);
                    intent.putExtra("data", mParam1);
                    activityResultLauncher.launch(intent);

                }

            });
        return rootView;
    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode() == Activity.RESULT_OK) {
                        // There are no request codes
                        Intent data = result.getData();
                        //获取返回的数据
//                        assert data != null;
                        Bundle bundle = data.getExtras();

                        boolean flag = bundle.getBoolean("flag");
                        if(flag){
                            String temp_userName = bundle.getString("userName");
                            int temp_sex = bundle.getInt("sex");
                            String temp_introduction = bundle.getString("introduction");

                            //修改data类里保存的数据
                            mParam1.getData().setUsername(temp_userName);
                            mParam1.getData().setSex(String.valueOf(temp_sex));
                            mParam1.getData().setIntroduce(temp_introduction);
                            mParam1.getData().setAvatar(bundle.getString("avatar"));
                            System.out.println("进入重写函数");
                            System.out.println(temp_introduction);
                            Glide.with(getActivity()).load(mParam1.getData().getAvatar()).into(_headerImage);
                            userName.setText(temp_userName);
                            if(temp_sex == 1)
                                sex.setImageResource(R.drawable.boy);
                            else if(temp_sex == 0){
                                sex.setImageResource(R.drawable.girl);
                                System.out.println("ooo");
                            }
                            introduction.setText(temp_introduction);
                        }

                    }
                }

            });


}