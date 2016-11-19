package com.feicui.edu.highpart.biz;

import android.provider.BaseColumns;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class NewsContract {
    //SQLiteOpenHelper  是一个抽象类


    public static abstract class LocalNewsEntry implements BaseColumns{
        public static final String TABLE_NAME="local_news";
        public static final String COLUMN_NAME_ENTRY_ID="nid";
        public static final String COLUMN_NAME_TITLE="title";
        public static final String COLUMN_NAME_SUMMARY="summary";
        public static final String COLUMN_NAME_LINK="link";
        public static final String COLUMN_NAME_ICON="icon";


    }

    /*
 private int cid;//“cid”:评论编号
 private String uid;//“uid”:评论者名字
 private String portrait;//“portrait”:用户头像链接
 private String stamp;//“stamp”:评论时间
 private String content;//“content":评论内容
  */
    public static abstract class LocalCommentEntry implements BaseColumns {
        public static final String TABLE_NAME = "local_news";
        public static final String COLUMN_NAME_ENTRY_ID = "cid";
        public static final String COLUMN_NAME_UID = "uid";
        public static final String COLUMN_NAME_CONTENT = "content";
        public static final String COLUMN_NAME_STAMP = "stamp";
        public static final String COLUMN_NAME_PORTRAIT = "portrait";
    }

}
