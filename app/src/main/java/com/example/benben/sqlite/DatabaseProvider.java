package com.example.benben.sqlite;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.telecom.Call;

/**
 * Created by benebne on 2016/5/11.
 * 与TheFirstLindOfCode进行跨程序数据共享
 */
public class DatabaseProvider extends ContentProvider {

    public static final int BOOK_DIR = 0 ;//book表中的所有数据
    public static final int BOOK_ITEM = 1 ;//book表中的单条数据
    public static final int CATEGORY_DIR = 2 ;//category表中的所有数据
    public static final int CATEGORY_ITEM = 3 ;//category表中的单条数据

    public static final String AUTHOEITY="com.example.benben.sqlite";

    private static UriMatcher uriMatcher;
    private DatabaseHelper dbHelper;

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHOEITY,"book",BOOK_DIR);
        uriMatcher.addURI(AUTHOEITY,"book/#",BOOK_ITEM);
        uriMatcher.addURI(AUTHOEITY,"category",CATEGORY_DIR);
        uriMatcher.addURI(AUTHOEITY,"category/#",CATEGORY_ITEM);
    }

    /**创建*/
    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseHelper(getContext(), "BookStore.db", null, 2);
        return true;
    }

    /**查询*/
    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //查询
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        /**根据传入的Uri参数判断出用户想要判断要访问那张表*/
        /**
         * 它会将内容 URI 权限之后的部分以“/”符号进行分割，并把分割后的结果放入到一个字符串列表中，
         * 那这个列表的第 0 个位置存放的就是路径，第 1 个位置存放的就是 id 了
         */
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                cursor = db.query("Book", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case BOOK_ITEM:
                String booiId=uri.getPathSegments().get(1);
                cursor = db.query("Book", projection, "id=?", new String[]{booiId}, null, null, sortOrder);
                break;
            case CATEGORY_DIR:
                cursor = db.query("Category", projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                cursor = db.query("Category", projection, "id=?", new String[]{categoryId}, null, null, sortOrder);
                break;
            default:
                break;
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.benben.sqlite.book";
            case BOOK_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.benben.sqlite.book";
            case CATEGORY_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.benben.sqlite.category";
            case CATEGORY_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.benben.sqlite.category";
        }
        return null;
    }

    /**添加数据*/
    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Uri uriRetutn=null;
        /**
         * 注意 insert()方法要求返回一个能够表示这条新增数据的 URI，
         * 所以我们还需要调用 Uri.parse()方法来将一个内容 URI 解析成 Uri 对象，当然这个内容 URI 是以新增数据的 id 结尾的
         */
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
            case BOOK_ITEM:
                long newBookId = db.insert("Book", null, values);
                uriRetutn = Uri.parse("content://" + AUTHOEITY + "/book/" + newBookId);
                break;
            case CATEGORY_DIR:
            case CATEGORY_ITEM:
                long newCategoryId = db.insert("Category", null, values);
                uriRetutn = Uri.parse("content://" + AUTHOEITY + "/category/" + newCategoryId);
                break;
            default:
                break;
        }
        return uriRetutn;
    }

    /**删除数据*/
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int deleteRows = 0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                deleteRows = db.delete("Book", selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                deleteRows = db.delete("Book", "id=?", new String[]{bookId});
                break;
            case CATEGORY_DIR:
                deleteRows = db.delete("Category", selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                deleteRows = db.delete("Category", "id=?", new String[]{categoryId});
                break;
            default:
                break;
        }
        return deleteRows;
    }


    /**更新数据*/
    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int updatedRows=0;
        switch (uriMatcher.match(uri)) {
            case BOOK_DIR:
                updatedRows = db.update("Book", values, selection, selectionArgs);
                break;
            case BOOK_ITEM:
                String bookId = uri.getPathSegments().get(1);
                updatedRows = db.update("Book", values,"id=?",new String[]{bookId});
                break;
            case CATEGORY_DIR:
                updatedRows = db.update("Category", values, selection, selectionArgs);
                break;
            case CATEGORY_ITEM:
                String categoryId = uri.getPathSegments().get(1);
                updatedRows = db.update("Category", values, "id=?",new String[]{categoryId});
                break;
            default:
                break;
        }
        return updatedRows;
    }
}
