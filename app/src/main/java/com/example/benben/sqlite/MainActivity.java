package com.example.benben.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @InjectView(R.id.main_createDatabase)
    Button mCreateDatabase;
    @InjectView(R.id.main_updateDatabase)
    Button mUpdateDatabase;
    @InjectView(R.id.main_insert)
    Button mInsert;
    @InjectView(R.id.main_update)
    Button mUpdate;
    @InjectView(R.id.main_query)
    Button mQuery;
    @InjectView(R.id.main_delete)
    Button mDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
    }



    @OnClick({R.id.main_createDatabase, R.id.main_updateDatabase, R.id.main_insert, R.id.main_update, R.id.main_query, R.id.main_delete})
    public void onClick(View view) {
        switch (view.getId()) {
            /**创建数据库*/
            case R.id.main_createDatabase:
                Log.i("lyx", "_______________1________________: ");
                /**创建一个DatabaseHelper对象*/
                DatabaseHelper deHelper1 = new DatabaseHelper(MainActivity.this, "test.db");
                /**取得一个只读的数据库对象*/
                SQLiteDatabase db1 = deHelper1.getReadableDatabase();

                break;
            /**更新数据库*/
            case R.id.main_updateDatabase:
                Log.i("lyx", "_______________2________________: ");
                DatabaseHelper deHelper2 = new DatabaseHelper(MainActivity.this, "test_db", 2);
                SQLiteDatabase db2 = deHelper2.getReadableDatabase();
                break;
            /**插入数据*/
            case R.id.main_insert:
                Log.i("lyx", "_______________3________________: ");
                /**创建存放数据的ContentValues对象*/
                ContentValues values = new ContentValues();
                /**向ContentValues中存放数据*/
                values.put("id", 3);
                values.put("name", "liYuanXiong");
                DatabaseHelper dbHelper3=new DatabaseHelper(MainActivity.this,"test_db");
                SQLiteDatabase db3 = dbHelper3.getReadableDatabase();
                /**数据库执行插入命令*/
                db3.insert("user", null, values);
                break;
            /**更新数据*/
            case R.id.main_update:
                Log.i("lyx", "_______________4________________: ");
                DatabaseHelper dbHelper4 = new DatabaseHelper(MainActivity.this, "test_db");
                SQLiteDatabase db4 = dbHelper4.getWritableDatabase();
                ContentValues values2 = new ContentValues();
                values2.put("name", "jianMing");
                db4.update("user", values2, "id=?", new String[]{"1"});
                break;
            /**查询信息*/
            case R.id.main_query:
                Log.i("lyx", "_______________5________________: ");
                DatabaseHelper dbHelper5 = new DatabaseHelper(MainActivity.this, "test_db");
                SQLiteDatabase db5 = dbHelper5.getReadableDatabase();
                /**创建游标对象*/
                Cursor cursor = db5.query("user", new String[]{"id", "name"}, "id=?", new String[]{"1"}, null, null, null, null);
                /**利用游标便利所以数据对象*/
                while (cursor.moveToNext()) {
                    String name=cursor.getString(cursor.getColumnIndex("name"));
                    /**日志打印输出*/
                    Log.i("lyx", "query-->: " + name);
                }
                cursor.close();
                break;
            /**删除数据*/
            case R.id.main_delete:
                Log.i("lyx", "_______________6________________: ");
                DatabaseHelper dbHelper6 = new DatabaseHelper(MainActivity.this, "test_db");
                SQLiteDatabase db6 = dbHelper6.getWritableDatabase();
                db6.delete("user", "id=?", new String[]{"1"});
                break;
            default:
                Log.i("lyx", "error: ");
                break;
        }
    }
}
