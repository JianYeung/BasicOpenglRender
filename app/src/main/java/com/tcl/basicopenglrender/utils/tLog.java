package com.tcl.basicopenglrender.utils;

import android.util.Log;

/**
 * 项目名：   BasicOpenglRender
 * 包名：     com.tcl.basicopenglrender.utils
 * 文件名：   tLog
 * 创建者：   root
 * 创建时间： 17-4-28 上午11:20
 * 描述：     TODO
 */

public class tLog {
    private static boolean isDebug = true;

    public static void setDebug(boolean debug){
        if (isDebug == debug){
            return;
        }
        isDebug = debug;
    }
    public static final void v(String tag, String msg){
        if (isDebug){
            Log.v(tag,msg);
        }
    }
    public static final void i(String tag, String msg){
        if (isDebug){
            Log.i(tag,msg);
        }
    }
    public static final void d(String tag, String msg){
        if (isDebug){
            Log.d(tag,msg);
        }
    }
    public static final void e(String tag, String msg){
        if (isDebug){
            Log.e(tag,msg);
        }
    }
}
