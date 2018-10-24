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
import android.widget.TextView;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by 995204127 on 2018-10-23.
 */

public class BookAct extends AppCompatActivity {
    RecyclerView mRecyclerView;
    List<String> mBookNames = new LinkedList<>();
    String mBookName;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.book_act);
        mBookName = getIntent().getStringExtra("BookName");

        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new BookNameAdapter());
    }

    SQLiteDatabase db;
    void initData(){

        // 打开数据库输出流
        DataBaseHelper myDbHelper = new DataBaseHelper(BookAct.this, mBookName);
        try {
            myDbHelper.createDataBase();
            myDbHelper.openDataBase();
            db = myDbHelper.getSQLiteDatabase();

            Cursor cursor = db.rawQuery("select * from table_volume", null);
            while (cursor.moveToNext()) {
                mBookNames.add(cursor.getString(cursor.getColumnIndex("volume")));
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
                    BookAct.this).inflate(R.layout.item_recyclerview, parent,
                    false));
            return holder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, final int position) {
            holder.tv.setText(mBookNames.get(position));
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Cursor cursor = db.rawQuery("select * from table_volume where volume=?", new String[]{mBookNames.get(position)});
                        if (cursor.moveToFirst()) {
                            Intent intent = new Intent(BookAct.this, BookContentAct.class);
                            intent.putExtra("BookContent",cursor.getString(cursor.getColumnIndex("content")) );
                            startActivity(intent);
                    }
                    cursor.close();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mBookNames.size();
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
