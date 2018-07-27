package com.evil.liveplayer.util.status_bar;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by dabai on 17/8/17.
 */

public class StatusBarIconUtil {
    private static final String KEY_MIUI_VERSION_CODE = "ro.miui.ui.version.code";
    private static final String KEY_MIUI_VERSION_NAME = "ro.miui.ui.version.name";
    private static final String KEY_MIUI_INTERNAL_STORAGE = "ro.miui.internal.storage";
    private static boolean isTranslucentStatusMiUi;
    private static boolean isFlyMe;

    static {
        isTranslucentStatusMiUi = isTranslucentStatusMiUiVersion();
        isFlyMe = isFlyMeOS();
    }

    //是否是支持沉浸式状态栏的 MiUi
    public static boolean isTranslucentStatusMiUi() {
        return isTranslucentStatusMiUi;
    }

    //判断是否是支持沉浸式状态栏的 MiUi 版本
    private static boolean isTranslucentStatusMiUiVersion() {
        try {
            Class<?> sysClass = Class.forName("android.os.SystemProperties");
            Method getStringMethod = sysClass.getDeclaredMethod("get", String.class);
            boolean isMiUiV6 = "V6".equals(getStringMethod.invoke(sysClass, "ro.miui.ui.version.name"));
            boolean isMiUiV7 = "V7".equals(getStringMethod.invoke(sysClass, "ro.miui.ui.version.name"));
            boolean isMiUiV8 = "V8".equals(getStringMethod.invoke(sysClass, "ro.miui.ui.version.name"));
            boolean isMiUiV9 = "V9".equals(getStringMethod.invoke(sysClass, "ro.miui.ui.version.name"));
            return isMiUiV6|isMiUiV7|isMiUiV8|isMiUiV9;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //判断是否是小米的 MiUi（所有版本）
    public static boolean isMiUiOS() {
        try {
            final BuildProperties prop = BuildProperties.newInstance();
            return prop.getProperty(KEY_MIUI_VERSION_CODE, null) != null
                    || prop.getProperty(KEY_MIUI_VERSION_NAME, null) != null
                    || prop.getProperty(KEY_MIUI_INTERNAL_STORAGE, null) != null;
        } catch (final IOException e) {
            return false;
        }
    }

    /**
     * set status bar darkMode
     *
     * MiUi 将修改MiUiWindowManager的部分LayoutParams
     * FlyMe 将调用其对应的API
     * Android 6.0 起将调用高亮状态栏模式
     *
     * @param darkMode 是否是黑色模式
     * @param activity 所要设置的activity
     */
    public static void setStatusBarDarkMode(boolean darkMode, Activity activity) {
        if (isTranslucentStatusMiUi) {
            Class<? extends Window> clazz = activity.getWindow().getClass();
            try {
                int tranceFlag = 0;
                int darkModeFlag = 0;

                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                darkModeFlag = field.getInt(layoutParams);
                field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_TRANSPARENT");
                tranceFlag = field.getInt(layoutParams);

                Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
                //extraFlagField.invoke(activity.getWindow(), darkmode ? darkModeFlag : 0, darkModeFlag);
                if(darkMode) extraFlagField.invoke(activity.getWindow(), tranceFlag|darkModeFlag, tranceFlag|darkModeFlag);
                else extraFlagField.invoke(activity.getWindow(), 0, darkModeFlag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        //MiUi 和 FlyMe 均再进行高亮模式设置，为了避免系统版本在Android M起切换黑色模式无效的问题
        setLightStatusBar(darkMode, activity);
    }

    /**
     *
     * @param lightMode 是否是高亮模式（高亮模式状态栏文字及图标将变成灰色）
     * @param activity 所要设置的activity
     */
    private static void setLightStatusBar(boolean lightMode, Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int vis = activity.getWindow().getDecorView().getSystemUiVisibility();
            if(lightMode) vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            else vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            activity.getWindow().getDecorView().setSystemUiVisibility(vis);
        }
    }

    //判断是否是魅族的 FlyMe OS
    public static boolean isFlyMeOS() {
        /* 获取魅族系统操作版本标识*/
        String meiZuFlyMeOSFlag = getSystemProperty("ro.observe.display.id", "");
        if (TextUtils.isEmpty(meiZuFlyMeOSFlag)){
            return false;
        } else if (meiZuFlyMeOSFlag.contains("flyme") || meiZuFlyMeOSFlag.toLowerCase().contains("flyme")){
            return true;
        } else {
            return false;
        }
    }

    //是否是魅族的 FlyMe
    public static boolean isFlyMe() {
        return isFlyMe;
    }

    /**
     * 获取系统属性
     * @param key  ro.observe.display.id
     * @param defaultValue 默认值
     * @return 系统操作版本标识
     */
    private static String getSystemProperty(String key, String defaultValue) {
        try {
            Class<?> clz = Class.forName("android.os.SystemProperties");
            Method get = clz.getMethod("get", String.class, String.class);
            return (String)get.invoke(clz, key, defaultValue);
        } catch (Exception e) {
            return null;
        }
    }

    //获取状态栏高度
    public static int getStatusBarHeight(Resources res) {
        String key = "status_bar_height";
        int result = 0;
        int resourceId = res.getIdentifier(key, "dimen", "android");
        if (resourceId > 0) {
            result = res.getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 设置系统沉浸式状态栏属性（系统版本需高于等于 Android 4.4）
     *
     * @param activity 所要设置的activity
     * @param on 是否开启沉浸式
     */
    public static void setTranslucentStatus(Activity activity, boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = activity.getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    public static void setFullScreen(boolean enable, Window window) {
        if (enable) {
            WindowManager.LayoutParams attrs = window.getAttributes();
            attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(attrs);
        } else {
            WindowManager.LayoutParams attrs = window.getAttributes();
            attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
            window.setAttributes(attrs);
        }
    }
}
