package com.feicui.edu.highpart.biz;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.feicui.edu.highpart.Person;
import com.feicui.edu.highpart.bean.News;
import com.feicui.edu.highpart.bean.NewsGroup;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/9/29 0029.
 */
public class LocalNawsDBManager {
    /**
     *插入新闻
     * @param sqLiteOP
     * @param news
     */
    public static long insert(LocalNewsSQLiteOP sqLiteOP,News news){
        ContentValues values=new ContentValues();
        values.put(NewsContract.LocalNewsEntry.COLUMN_NAME_ENTRY_ID,news.getNid());
        values.put(NewsContract.LocalNewsEntry.COLUMN_NAME_TITLE,news.getTitle());
        values.put(NewsContract.LocalNewsEntry.COLUMN_NAME_SUMMARY,news.getSummary());
        values.put(NewsContract.LocalNewsEntry.COLUMN_NAME_LINK,news.getLink());
        values.put(NewsContract.LocalNewsEntry.COLUMN_NAME_ICON,news.getIcon());
        long count = sqLiteOP.getWritableDatabase().insert(NewsContract.LocalNewsEntry.TABLE_NAME, null, values);
        return count;

    }

    /**
     * 查询收藏新闻是否存在已经被收藏
     * @param sqLiteOP
     * @return
     */
    public static boolean isExistNews(LocalNewsSQLiteOP sqLiteOP,int nid){
        String selection=NewsContract.LocalNewsEntry.COLUMN_NAME_ENTRY_ID+" LIKE ? ";
        String[] selectionArg=new String[]{nid+""};

        Cursor cursor= sqLiteOP.getWritableDatabase().query(
                NewsContract.LocalNewsEntry.TABLE_NAME,
                new String[]{NewsContract.LocalNewsEntry.COLUMN_NAME_ENTRY_ID},
                selection,
                selectionArg,
                null,
                null,
                null);
        return cursor.getCount()>0;

    }


    public static ArrayList<News> query(LocalNewsSQLiteOP sqLiteOP){
        ArrayList<News> data=new ArrayList<>();

        Cursor cursor= sqLiteOP.getWritableDatabase().query(
                NewsContract.LocalNewsEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null);
        int count=cursor.getCount();

        Log.d("query","count"+count);
        while (cursor.moveToNext()){
            News n=new News();
            String icon=cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalNewsEntry.COLUMN_NAME_ICON));
            String link=cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalNewsEntry.COLUMN_NAME_LINK));
            String summary=cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalNewsEntry.COLUMN_NAME_SUMMARY));
            String title=cursor.getString(
                    cursor.getColumnIndex(NewsContract.LocalNewsEntry.COLUMN_NAME_TITLE));

            n.setIcon(icon);
            n.setLink(link);
            n.setSummary(summary);
            n.setTitle(title);
            data.add(n);
        }
    return data;
    }

}
