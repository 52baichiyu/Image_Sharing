package com.example.photosharing.main;/*
 *@author: 余
 *@date: 2022/8/24
 *@substance:
 */

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.photosharing.R;
import com.example.photosharing.main_page.FindFragment;
import com.example.photosharing.main_page.FrontFragment;
import com.example.photosharing.main_page.MyFragment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import org.json.JSONObject;


public class App_Main extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private static String Id ;
    private static String  apkId;

    /*
     * @description tab栏参数
     * @param
    */

    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    FindFragment findFragment = new FindFragment();
    FrontFragment frontFragment = new FrontFragment();
    MyFragment myFragment = new MyFragment();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_main);
   
        /*
         * @description 活动间传递数据
         * @param null
        */
        Intent intent = new Intent();
        intent = getIntent();
        Id = intent.getStringExtra("Id");
        apkId = intent.getStringExtra("apkId");

        ObjectMapper objectMapper = new ObjectMapper();

        /*
         * @description 
         * @param 
        */
          init();


    }
    
    /**
     * @description 初始化导航栏
     * @param 
     * @return 
     * @author 余
     * @time 2022/8/26 18:40
     */
     private void init(){

  /*
   * @description 获取app_main.xml 组件对象
   * @param 
  */
         viewPager = findViewById(R.id.viewPager);
         viewPager.addOnPageChangeListener((ViewPager.OnPageChangeListener) this);
         bottomNavigationView = findViewById(R.id.navigation);
         bottomNavigationView.setOnItemSelectedListener((NavigationBarView.OnItemSelectedListener) this);


         /*
          * @description 页面切换
          * @param
         */
         viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
             @NonNull
             @Override
             public Fragment getItem(int position) {
                 switch (position)
                 {
                     case 0:return frontFragment;
                     case 1:return findFragment;
                     case 2:return myFragment;
                 }
                 return null;
             }

             @Override
             public int getCount() {
                 return 3;
             }
         });




     }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
           bottomNavigationView.getMenu().getItem(position).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
         viewPager.setCurrentItem(item.getOrder());
        return true;
    }
}
