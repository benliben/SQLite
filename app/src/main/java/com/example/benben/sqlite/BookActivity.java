package com.example.benben.sqlite;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/5/8.
 */
public class BookActivity extends AppCompatActivity {


    private static final String TAG = "lyx";
    private BooksDB dbHelper;



    @InjectView(R.id.book_add_data)
    Button mDatabase;
    @InjectView(R.id.book_text)
    TextView mContent;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.inject(this);
        initData();

    }

    private void initData() {
        dbHelper = new BooksDB(this, "BookStore.db", null, 1);

    }

    @OnClick({R.id.book_database, R.id.book_add_data, R.id.book_update, R.id.book_delete
            , R.id.book_query})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.book_database:
                dbHelper.getWritableDatabase();
                break;
            /**添加数据*
             *
             *insert（）方法
             * 第一个参数是表名，是我们希望向那张表里面添加数据
             * 第二个参数是用于在指定添加数据的情况下给摸个可能为空的列表自动赋值NULL，一般不用直接传null
             * 第三个参数是一个ContentValues对象，用于向ContentValues中添加数据，只需将表中的每个列名以及相应的待添加的数据传入即可
             */

            case R.id.book_add_data:
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                /**开始组装第一条数据*/
                values.put("name", "The Da Vinci Code");
                values.put("author", "Dan Brown");
                values.put("pages", 454);
                values.put("price", 16.96);
                db.insert("Book", null, values);//插入第一条数据
                values.clear();//开始组装第二条数据
                values.put("name", "The Lost Symbol");
                values.put("author", "Dan Brown");
                values.put("pages", 510);
                values.put("price", 19.95);
                db.insert("Book", null, values);//插入第二条数据
                Log.i(TAG, "数据添加成功");
                break;
            /**
             * 更新数据
             * 第一个参数是是表明
             * 第二个参数是ContentValues对象，把要跟新的数据在这里装进去
             * 第三，四个参数用于去约束跟新某一行或某几行中的数据，不指定的话默认跟新所有(name=?
             * 表示跟新所有的name等于?的行  而？是一个占位符，可以通过第四个参数提供的一个字符串为第三个参数中的每个占位符指定相应的内容
             *
             * 本次跟新的意图为：将名字是The Vinci Code的这本书价格改为10.99
             */
            case R.id.book_update:
                SQLiteDatabase db1 = dbHelper.getWritableDatabase();
                ContentValues values1 = new ContentValues();
                values1.put("price", 10.99);
                db1.update("Book", values1, "name=?", new String[]{"The Da Vinci Code"});
                break;

            /**
             * 删除数据
             * 第一个参数是表名
             * 第二个，第三个参数 指定仅删除当前Book表中的数据 其中The Lost Symbol这本书的页数超过了500页
             * 本次的意图为；删除超过500页的书
             */
            case R.id.book_delete:
                SQLiteDatabase db2 = dbHelper.getWritableDatabase();
                db2.delete("Book", "pages>?", new String[]{"500"});
                break;

            /**
             * 数据查询
             * 第一个参数为表名
             * 第二个参数用于指定去查询那几列，如果不指定则默认查询所有列
             * 第三，四个参数用于去约束查询某一行或某几行的数据，不指定则默认是查询所有数据
             * 第五个参数用于指定需要去group by的列，不指定表示不对查询结果进行group by 操作
             * 第六个参数用于对group by之后的数据进一步的过滤，不指定则表示不进行过滤
             * 第七个参数用于指定查询结果的排列方式，不指定表示使用默认的排列方式。
             */
            case R.id.book_query:
                SQLiteDatabase db3 = dbHelper.getWritableDatabase();
                /**查询book表中所有的数据*/
                Cursor cursor = db3.query("Book", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        /**遍历Cursor对象，取出数据并打印出来*/
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.i(TAG, "书的名字是" + name);
                        Log.i(TAG, "书的作者是" + author);
                        Log.i(TAG, "书的页数是" + pages);
                        Log.i(TAG, "书的价格是" + price);

                        mContent.setText("书的名字是" + name
                                +"书的作者是" + author
                                +"书的页数是" + pages
                                +"书的价格是" + price);

                    } while (cursor.moveToNext());
                    cursor.close();
                }
                break;
        }
    }
}
