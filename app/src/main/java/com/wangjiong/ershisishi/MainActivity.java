package com.wangjiong.ershisishi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<String> mHistoryNames = new LinkedList<>();
    List<String> mDatabaseNames = new LinkedList<>();

    Button mPlayMusicBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new BookNameAdapter());
        // service
        mPlayMusicBtn = (Button) findViewById(R.id.id_play_btn);
        mPlayMusicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startService(new Intent(MainActivity.this, MusicService.class));
            }
        });
    }


    void initData() {
        // 打开数据库输出流
        DataBaseHelper myDbHelper = new DataBaseHelper(MainActivity.this, "sumData_s.db");
        try {
            myDbHelper.createDataBase();
            myDbHelper.openDataBase();
            SQLiteDatabase db = myDbHelper.getSQLiteDatabase();

            Cursor cursor = db.rawQuery("select * from table_history", null);
            while (cursor.moveToNext()) {
                mHistoryNames.add(cursor.getString(cursor.getColumnIndex("history")));
                mDatabaseNames.add(cursor.getString(cursor.getColumnIndex("databaseName")));
            }
            cursor.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class BookNameAdapter extends RecyclerView.Adapter<BookNameAdapter.MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                    MainActivity.this).inflate(R.layout.item_recyclerview, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv.setText(mHistoryNames.get(position));
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, BookAct.class);
                    intent.putExtra("BookName", mDatabaseNames.get(position));
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mHistoryNames.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView)view.findViewById(R.id.id_num);
            }
        }
    }
}
