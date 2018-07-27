package com.evil.liveplayer.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;


import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *
 *
 *     time  : 2016/09/23
 *     desc  : Activity相关工具类
 * </pre>
 */
public final class ActivityUtils {

    private ActivityUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 创建快捷方式
     * 首先生成一个Intent实例（隐式Intent），然后向intent中放入一些值来配置
     * 1.是否允许重复创建
     * 2.确定创建的图标的名称
     * 3.确定创建的图标的图片
     * 4.设置点击这个快捷图标就运行该APP
     * 你可以在你的APP第一次运行时在第一个Activity中调用这个类的静态方法，就实现了快捷图标的创建
     *
     * @param act
     * @param iconResId
     * @param appnameResId
     */
    public static void createLauncherIcon(
            Activity act,int iconResId,int appnameResId
    )
    {
        createLauncherIcon(act,iconResId,act.getString(appnameResId));
    }

    /**
     * 创建快捷方式
     * 首先生成一个Intent实例（隐式Intent），然后向intent中放入一些值来配置
     * 1.是否允许重复创建
     * 2.确定创建的图标的名称
     * 3.确定创建的图标的图片
     * 4.设置点击这个快捷图标就运行该APP
     * 你可以在你的APP第一次运行时在第一个Activity中调用这个类的静态方法，就实现了快捷图标的创建
     *
     * @param act
     * @param iconResId
     * @param appname app名字
     */
    public static void createLauncherIcon(
            Activity act,int iconResId,String appname
    )
    {
        //<uses-permission android:name="com.android.launcher.permission
        // .INSTALL_SHORTCUT"/>
        Intent intent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        // 不允许重复创建
        intent.putExtra("duplicate",false);
        // 需要现实的名称
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME,appname);
        // 快捷图片
        Context appContext = act.getApplicationContext();
        Parcelable icon = Intent.ShortcutIconResource.fromContext(appContext,iconResId);
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE,icon);
        // 点击快捷图片，运行程序
        Intent value = new Intent(appContext,act.getClass());
        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT,value);
        // 发送广播
        act.sendBroadcast(intent);
    }

    /**
     * 判断是否存在Activity
     *
     * @param packageName 包名
     * @param className activity全路径类名
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isActivityExists(String packageName,String className) {
        Intent intent = new Intent();
        intent.setClassName(packageName,className);
        return !(Utils.getContext().getPackageManager().resolveActivity(intent,0) == null ||
                 intent.resolveActivity(Utils.getContext().getPackageManager()) == null ||
                 Utils.getContext().getPackageManager().queryIntentActivities(intent,0).size() ==
                 0);
    }


    /**
     * 打开一个界面
     *
     * @param clazz the clazz
     */
    public static void openActivity(Context context,Class<? extends Activity> clazz) {
        openActivity(context,clazz,null);
    }

    /**
     * 打开一个界面
     *
     * @param clazz the clazz
     * @param bundle the bundle
     */
    public static void openActivity(
            Context context,
            Class<? extends Activity> clazz,Bundle bundle
    )
    {
        Intent intent = new Intent(context,clazz);
        if (bundle != null) {
            intent.putExtra("Bundle",bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 打开一个等待返回结果的界面
     *
     * @param clazz the clazz
     * @param bundle the bundle
     * @param requestCode the request code
     */
    public static void openActivityForResult(Activity activity,
            Class<? extends Activity> clazz,Bundle bundle,int requestCode
    )
    {
        Intent intent = new Intent(activity,clazz);
        if (bundle != null) {
            intent.putExtra("Bundle",bundle);
        }
        activity.startActivityForResult(intent,requestCode);
    }

    /**
     * 打开一个等待返回结果的界面
     *
     * @param clazz the clazz
     * @param requestCode the request code
     */
    public static void openActivityForResult(Activity activity,
            Class<? extends Activity> clazz,int requestCode
    )
    {
        openActivityForResult(activity,clazz,null,requestCode);
    }


    /**
     * 获取launcher activity
     *
     * @param packageName 包名
     * @return launcher activity
     */
    public static String getLauncherActivity(String packageName) {
        Intent intent = new Intent(Intent.ACTION_MAIN,null);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PackageManager pm = Utils.getContext().getPackageManager();
        List<ResolveInfo> info = pm.queryIntentActivities(intent,0);
        for (ResolveInfo aInfo : info) {
            if (aInfo.activityInfo.packageName.equals(packageName)) {
                return aInfo.activityInfo.name;
            }
        }
        return "no " + packageName;
    }


    /**
     * 获取栈顶Activity
     *
     * @return 栈顶Activity
     */
    public static Activity getTopActivity() {
        try {
            Class activityThreadClass = Class.forName("android.app.ActivityThread");
            Object activityThread = activityThreadClass.getMethod("currentActivityThread")
                                                       .invoke(null);
            Field activitiesField = activityThreadClass.getDeclaredField("mActivities");
            activitiesField.setAccessible(true);
            Map activities = null;
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                activities = (HashMap)activitiesField.get(activityThread);
            } else {
                activities = (ArrayMap)activitiesField.get(activityThread);
            }
            for (Object activityRecord : activities.values()) {
                Class activityRecordClass = activityRecord.getClass();
                Field pausedField = activityRecordClass.getDeclaredField("paused");
                pausedField.setAccessible(true);
                if (!pausedField.getBoolean(activityRecord)) {
                    Field activityField = activityRecordClass.getDeclaredField("activity");
                    activityField.setAccessible(true);
                    return (Activity)activityField.get(activityRecord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 打开本应用的设置界面
     *
     * @param context
     */
    public static void openSettingActivity(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",context.getPackageName(),null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 根据包名打开应用的设置界面
     *
     * @param context
     */
    public static void openSettingActivity(Context context,String packageName) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package",packageName,null);
        intent.setData(uri);
        context.startActivity(intent);
    }

    /**
     * 获取Activity根节点View
     *
     * @return
     */
    public static View getRootView(Activity activity) {
        return ((ViewGroup)activity.findViewById(android.R.id.content)).getChildAt(0);
    }

    /**
     * 获取Activity根节点Group
     *
     * @return
     */
    public static View getRootViewGroup(Activity activity) {
        return activity.getWindow().getDecorView().findViewById(android.R.id.content);
    }

    /**
     * 全屏
     *
     * @param activity
     */
    public static void allScreen(Activity activity) {
        /*set it to be no title*/
        activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*set it to be full screen*/
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                                      WindowManager.LayoutParams.FLAG_FULLSCREEN
        );
    }
}
