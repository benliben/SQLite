package com.example.benben.sqlite;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Administrator on 2016/5/8.
 */
public class DatabaseHelper extends SQLiteOpenHelper{

    private static  final int VERSION=1;
    private static final String TAG = "lyx";
    private static final String DB_NAME = "mydata.db";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DatabaseHelper(Context context, String name) {
        this(context, name,VERSION);
    }
    public DatabaseHelper(Context context,String name,int version) {
        this(context, name,null,version);
    }


    /**
     * 三个不同的参数的构造函数
     * 带全部参数的构造函数，此构造函数必不可少
     * @param context
     * @param name
     */

    /**创建数据库*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "create a Database: ");
        /**创建数据库sql语句*/
        String sql = "create table user(id int,name varchar(20) not null,password varchar(60) not null";
        /**执行创建数据库的操作*/
        db.execSQL(sql);
    }

    /**版本更新*/
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/**创建成功，日志输出提示*/
        Log.i(TAG, "update a Database: ");

    }
}
