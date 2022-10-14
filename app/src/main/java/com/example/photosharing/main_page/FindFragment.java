package com.example.photosharing.main_page;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.photosharing.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the  factory method to
 * create an instance of this fragment.
 */
public class FindFragment extends Fragment implements View.OnClickListener,View.OnTouchListener{

   Button button;
   TextView textView;
   ImageView imageView;
   int sig = 0;
    public FindFragment() {

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       button= view.findViewById(R.id.button2);
       textView = view.findViewById(R.id.textView);
       Bundle bundle = getArguments();
       String data = bundle.getString("id","");

       ///test
//       imageView = view.findViewById(R.id.test);
//        Glide.with(getActivity()).load(R.drawable.my_jiazai).into(imageView);
        ///

       button.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
              if(sig == 0) {
                  textView.setText(data);
                  sig = 1;
              }
              else textView.setText("123456");
              imageView.setVisibility(View.GONE);
           }


       });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_find, container, false);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        return false;
    }
}