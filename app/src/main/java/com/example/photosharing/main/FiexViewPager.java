package com.example.photosharing.main;/*
 *@author: 余
 *@date: 2022/8/26
 *@substance:
 */

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.viewpager.widget.ViewPager;

public class FiexViewPager extends androidx.viewpager.widget.ViewPager {


    public FiexViewPager(@NonNull Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        return super.onInterceptTouchEvent(ev);
    }

    public FiexViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);


    }


    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return super.onInterceptHoverEvent(event);
    }

    @Override
    public void setCurrentItem(int item) {
        super.setCurrentItem(item,false);
    }
}
