package com.evil.liveplayer.util;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

import com.evil.liveplayer.ApplicationLoader;

import static android.content.pm.ApplicationInfo.FLAG_DEBUGGABLE;

/**
 * <pre>
 *
 *
 *     time  : 16/12/08
 *     desc  : Utils初始化相关
 * </pre>
 */
public final class Utils{

    @SuppressLint("StaticFieldLeak") private static Context context;

    private Utils(){
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * The constant IsAppDebug.
     */
    public static boolean IsAppDebug = false; // 是否是debug模式

    /**
     * 初始化工具类
     *
     * @param context
     *         上下文
     */
    @Deprecated
    public static void init(Context context){
        Utils.context = context.getApplicationContext();
        IsAppDebug = AppUtils.isAppDebug();
    }

    /**
     * Is app debug boolean.
     *
     * @return the boolean
     */
    public static boolean IsAppDebug(){
        return IsAppDebug;
    }

    /**
     * Is app debug boolean.
     *
     * @return the boolean
     */
    static boolean initAppDebug(){
        IsAppDebug = (getContext()
                .getApplicationInfo().flags & FLAG_DEBUGGABLE) != 0;
        return IsAppDebug;
    }

    /**
     * 获取ApplicationContext
     *
     * @return ApplicationContext context
     */
    public static Context getContext(){
        if(context != null)
            return context;
        return context = ApplicationLoader.get().getApplicationContext();
    }

    /**
     * Get application application.
     *
     * @return the application
     */
    public static Application getApplication(){
        return ApplicationLoader.get();
    }

    /**
     * Get package name string.
     *
     * @return the string
     */
    public static String getPackageName(){
        return getContext().getPackageName();
    }

    static{
        initAppDebug();
    }

    /**
     * Get resources resources.
     *
     * @return the resources
     */
    public static Resources getResources(){
        return getContext().getResources();
    }
    /**
     * Get resources resources.
     *
     * @return the resources
     */
    public static String getString(int id){
        return getContext().getResources().getString(id);
    }
}