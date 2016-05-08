package com.example.benben.sqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/5/8.
 */
public class BookActivity extends AppCompatActivity {

    private BooksDB dbHelper;
    @InjectView(R.id.book_database)
    Button mDatabase;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        ButterKnife.inject(this);
        initData();

    }

    private void initData() {
        dbHelper = new BooksDB(this, "BookStore.db", null, 1);
    }


    @OnClick(R.id.book_database)
    public void onClick() {
        dbHelper.getWritableDatabase();
    }
}
