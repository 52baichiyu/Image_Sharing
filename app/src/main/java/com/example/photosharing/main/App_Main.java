package com.example.photosharing.main;/*
 *@author: 余
 *@date: 2022/8/24
 *@substance:
 */

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.photosharing.R;
import com.example.photosharing.Up_Data.App_up_Data;
import com.example.photosharing.login.App_Login;
import com.example.photosharing.main_page.FindFragment;
import com.example.photosharing.main_page.FrontFragment;
import com.example.photosharing.main_page.MyFragment;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;


public class App_Main extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {

    private static String Id ;
    private static String  apkId;

    /*
     * @description tab栏参数
     * @param
    */

    ImageView image;

    FiexViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    FindFragment findFragment = new FindFragment();
//    FrontFragment frontFragment = new FrontFragment();
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

         image = findViewById(R.id.up_data);


         Bundle bundle = new Bundle();
         bundle.putString("id","love");

         /*
          * @description 页面切换
          * @param
         */
         viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
             @NonNull
             @Override
             public Fragment getItem(int position) {
                 if(position!=3)
                 switch (position)
                 {
//                     case 1:  return frontFragment;
                     case 0: findFragment.setArguments(bundle); return findFragment;
                     case 1:return myFragment;
                 }
                 return null;
             }

             @Override
             public int getCount() {
                 return 2;
             }
         });

              image.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      Intent intent = new Intent(App_Main.this, App_up_Data.class);

                      intent.putExtra("Id", Id);
                      intent.putExtra("apkId", apkId);
                      startActivity(intent);
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

         //   2131230996

    }


}
