package com.evil.liveplayer;

import android.app.Application;

import java.lang.reflect.Method;

/**
 * The type Application loader.
 *
 * @创建者: Noah.冯
 * @时间: 10 :27
 * @描述： TODO
 */
public class ApplicationLoader{
    private static Application sApplication;

    /**
     * Get application.获取程序入口application
     *
     * @return the application
     */
    public static Application get() {
        return sApplication != null ? sApplication : getApplication();
    }

    private static Application getApplication() {
        Method method;
        try {
            method = Class.forName("android.app.AppGlobals").getDeclaredMethod("getInitialApplication");
            method.setAccessible(true);
            sApplication = (Application) method.invoke(null);
        } catch (Exception e) {
            try {
                method = Class.forName("android.app.ActivityThread").getDeclaredMethod("currentApplication");
                method.setAccessible(true);
                sApplication = (Application) method.invoke(null);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return sApplication;
    }

}
