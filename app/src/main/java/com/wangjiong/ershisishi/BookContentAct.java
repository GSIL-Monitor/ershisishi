package com.wangjiong.ershisishi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by 995204127 on 2018-10-23.
 */

public class BookContentAct extends AppCompatActivity {
    ScrollView mScrollView;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_content_act);

        TextView textView =  (TextView)findViewById(R.id.id_textview);
        textView.setText(getIntent().getStringExtra("BookContent"));

        mScrollView =  (ScrollView)findViewById(R.id.id_scrollview);

        if(MusicService.msMusicService != null){
            MusicService.msMusicService.playMusic("music/yuefei.mp3");
        }
    }
}
