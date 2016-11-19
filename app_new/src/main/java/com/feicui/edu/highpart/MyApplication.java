package com.feicui.edu.highpart;

import android.app.Application;
import android.util.Log;

import com.feicui.edu.highpart.biz.LocalCommentSQLiteOP;
import com.feicui.edu.highpart.biz.LocalNewsSQLiteOP;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class MyApplication extends Application {
    public LocalNewsSQLiteOP sqLiteOP;
    public LocalCommentSQLiteOP commentSQLiteOP;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("MyApplication","onCreate");
        //创建数据库操作
        sqLiteOP=new LocalNewsSQLiteOP(
                this,
                LocalNewsSQLiteOP.DATABASE_NAME,
                null,
                LocalNewsSQLiteOP.DATABASE_VERSION);


        commentSQLiteOP = new LocalCommentSQLiteOP(
                this,
                LocalCommentSQLiteOP.DATABASE_NAME,
                null,
                LocalCommentSQLiteOP.DATABASE_VERSION
        );
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
