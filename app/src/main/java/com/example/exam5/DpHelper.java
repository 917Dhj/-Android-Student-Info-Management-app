package com.example.exam5;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DpHelper extends SQLiteOpenHelper {
    private Context mContext;

    //创建表格的SQL语句
    public static final String CREATE_TABLE = "CREATE TABLE student ("
            + "id Varchar(10) primary key, "
            + "name Varchar(10), "
            + "sex Varchar(2), "
            + "phone Varchar(15))";


    public DpHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        //在这里创建数据库表
        db.execSQL(CREATE_TABLE);
        //提示数据库创建成功
        Toast.makeText(mContext, "数据库创建成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //在这里更新数据库表

    }
}
