package com.example.benben.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.ScaleDrawable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/5/8.
 */
public class BooksDB extends SQLiteOpenHelper {

    public static final String CREATE_BOOK="create table book("
            +"id integer primary key autoincrement,"
            +"author text,"
            +"price real,"
            +"pages integer,"
            +"name text)";
    public static final String CREATE_CATECORY="create table Category("
            +"id integer primary key autoincrement,"
            +"category_name text,"
            +"category_code integer)";
    private static final String TAG ="lyx" ;

    private Context mContext;

    public BooksDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext,"Create succeeded",Toast.LENGTH_LONG).show();
        Log.i(TAG, "Create succeeded ");
        Log.i(TAG, "创建|打开 ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        db.execSQL("drop table if exists Category");
        onCreate(db);
        Log.i(TAG, "跟新 ");

    }
}
