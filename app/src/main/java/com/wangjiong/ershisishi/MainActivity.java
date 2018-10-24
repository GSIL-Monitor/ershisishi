package com.wangjiong.ershisishi;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    RecyclerView mRecyclerView;
    List<String> mBookNames = new LinkedList<>();

    Map<String , String> mMap = new LinkedHashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMap();
        initData();
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new BookNameAdapter());
    }

    void initMap(){
        mMap.put("beiqishu_s","北齐书");
        mMap.put("beishi_s","北史");
        mMap.put("chenshu_s","陈书");
        mMap.put("hanshu_s","汉书");
        mMap.put("houhanshu_s","后汉书");
        mMap.put("jinshi_s","金史");
        mMap.put("jinshu_s","晋书");
        mMap.put("jiutangshu_s","旧唐书");
        mMap.put("jiuwudaishi_s","旧五代史");
        mMap.put("liangshu_s","梁书");
        mMap.put("liaoshi_s","辽史");
        mMap.put("mingshi_s","明史");
        mMap.put("nanqishu_s","南齐书");
        mMap.put("nanshi_s","南史");
        mMap.put("qingshigao_s","清史稿");
        mMap.put("sanguozhi_s","三国志");
        mMap.put("shiji_s","史记");
        mMap.put("songshu_s","宋书");
        mMap.put("suishu_s","隋书");
        mMap.put("weishu_s","魏书");
        mMap.put("xintangshu_s","新唐书");
        mMap.put("xinwudaishi_s","新五代史");
        mMap.put("xinyuanshi_s","新元史");
        mMap.put("yuanshi_s","元史");
        mMap.put("zhoushu_s","周书");
        // mMap.put("sumData_s","");
    }

    void initData() {
        // 书名
        String[] files;
        try {
            // 获得Assets一共有几多文件
            files = this.getResources().getAssets().list("databases");
        } catch (IOException e1) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            // 获得每个文件的名字
            //mBookNames.add(mMap.get(files[i]));
            mBookNames.add(files[i]);
        }
        // 打开数据库输出流
//        DataBaseHelper myDbHelper = new DataBaseHelper(MainActivity.this, mBookNames.get(0));
//        try {
//            myDbHelper.createDataBase();
//            myDbHelper.openDataBase();
//            SQLiteDatabase db = myDbHelper.getSQLiteDatabase();
//
//            // Test
//            Cursor cursor = db.rawQuery("select * from table_volume where volume=?", new String[]{"卷一·帝纪第一"});
//            String content = null;
//            if (cursor.moveToFirst()) {
//                content = cursor.getString(cursor.getColumnIndex("content"));
//            }
//            Log.i("SQLdm==", content);
//            cursor.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
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
            holder.tv.setText(mBookNames.get(position));
            holder.tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, BookAct.class);
                    intent.putExtra("BookName", mBookNames.get(position));
                    startActivity(intent);
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
