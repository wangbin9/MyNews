package com.feicui.edu.highpart.base;

import android.util.Log;

/**
 * Created by Administrator on 2016/9/5 0005.
 *  日志管理
 */
public class LogUtil {
    public static final String TAG = "新闻随意看";
    //调试日志的开关
    public static boolean isDebug = true;

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);
    }
    public static void d(String msg) {
        if (isDebug)
            Log.d(LogUtil.TAG,msg);
    }
}
