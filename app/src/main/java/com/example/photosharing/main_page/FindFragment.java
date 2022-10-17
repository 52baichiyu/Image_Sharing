package com.example.photosharing.main_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.example.photosharing.R;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FindFragment extends Fragment implements View.OnClickListener,View.OnTouchListener{

        TabLayout mTableLayout;
    private ViewPager mViewPager;
    private MyAdapter2 adapter;
    private List<String> mTitle;
    private List<Fragment> mFragment;
    private String userId;
    private String userName;
    public FindFragment() {
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        userId = bundle.getString("id");
        userName = bundle.getString("userName");
        System.out.println(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //绑定一下fragment资源文件以及生成fragment对象
        View view = inflater.inflate(R.layout.fragment_find,null);
        mTableLayout=view.findViewById(R.id.find_2);
        mViewPager=view.findViewById(R.id.fing_pager2);
        //标题栏数组
        mTitle = new ArrayList<>();
        mTitle.add("首页");
        mTitle.add("关注");
        //fragment集合
        mFragment = new ArrayList<>();
        mFragment.add(new FindFragment_child());
        mFragment.add(new FindFragment_child2());
        //在activity中使用 getSupportFragmentManager(),这里是Fragment中使用如下方法
        adapter = new MyAdapter2(getFragmentManager());
        System.out.println(mViewPager+"!!");
        mViewPager.setAdapter(adapter);
        //将TabLayout和ViewPager绑定在一起，一个动另一个也会跟着动
        mTableLayout.setupWithViewPager(mViewPager);

        //返回视图


       // View view = inflater.inflate(R.layout.fragment_my, container, false);
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }

    //创建Fragment的适配器
    class MyAdapter2 extends FragmentPagerAdapter {

        public MyAdapter2(FragmentManager fm) {
            super(fm);
        }
        //获得每个页面的下标
        @Override
        public Fragment getItem(int position) {
            Fragment fragment = mFragment.get(position);

            Bundle bundle = new Bundle();

             bundle.putString("id",userId);
             bundle.getString("userName",userName);
             bundle.getInt("sig",1);
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

}