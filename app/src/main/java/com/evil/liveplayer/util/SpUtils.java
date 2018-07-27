package com.evil.liveplayer.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.Map;

/**
 * sharedPreferences快速保存数据/获取数据
 */
public class SpUtils {

    private static SharedPreferences sp;
    private static String sName = AppUtils.getAppPackageName();

    private SpUtils() {
    }

    /**
     * 修改sp保存的名字
     *
     * @param name
     */
    public static void trans(String name) {
        sName = name;
    }

    private static SharedPreferences getSharedP() {
        if (sp == null) {
            sp = Utils.getContext().getSharedPreferences(sName,Context.MODE_PRIVATE);
        }
        return sp;
    }

    /**
     * 保存各种类型的信息
     */
    public static void save(Object... list) {
        if (list == null) {
            return;
        }
        sp = getSharedP();
        // 获取编辑器
        Editor edit = sp.edit();
        for (int i = 0;i < list.length;i += 2) {
            String key = (String)list[i];
            Object object = list[i + 1];
            if (object instanceof String) {
                edit.putString(key,(String)object);
            } else if (object instanceof Integer) {
                edit.putInt(key,(Integer)object);
            } else if (object instanceof Boolean) {
                edit.putBoolean(key,(Boolean)object);
            } else if (object instanceof Float) {
                edit.putFloat(key,(Float)object);
            } else if (object instanceof Long) {
                edit.putLong(key,(Long)object);
            } else {
                edit.putString(key,object.toString());
            }
        }
        // 提交数据
        edit.commit();
    }

    /**
     * 保存String类型的信息
     */
    public static void save(String key,String value) {
        sp = getSharedP();
        // 获取编辑器
        Editor edit = sp.edit();
        // 写入数据
        edit.putString(key,value);
        // 提交数据
        edit.commit();
    }

    /**
     * 保存String类型的信息
     */
    public static void save(String... list) {
        if (list == null) {
            return;
        }
        sp = getSharedP();
        // 获取编辑器
        Editor edit = sp.edit();
        for (int i = 0;i < list.length;i += 2) {
            // 写入数据
            edit.putString(list[i],list[i + 1]);
        }
        // 提交数据
        edit.commit();
        sp = null;
    }


    /**
     * 获取String类型的数据信息
     */
    public static String getInfo(String key,String defValue) {
        sp = getSharedP();

        String value = sp.getString(key,defValue);

        return value;
    }

    /**
     * 保存boolean类型的置
     */
    public static void save(String key,boolean value) {
        sp = getSharedP();
        // 获取编辑器
        Editor edit = sp.edit();
        // 写入数据
        edit.putBoolean(key,value);
        // 提交数据
        edit.commit();
    }

    /**
     * 获取boolean的值
     */
    public static boolean getInfo(String key,boolean defValue) {
        sp = getSharedP();

        boolean value = sp.getBoolean(key,defValue);

        return value;
    }

    /**
     * 获取int类型的值
     */
    public static void save(String key,int value) {
        sp = getSharedP();
        // 获取编辑器
        Editor edit = sp.edit();
        // 写入数据
        edit.putInt(key,value);
        // 提交数据
        edit.commit();
    }

    /**
     * 获取int类型的值
     */
    public static int getInfo(String key,int defValue) {
        sp = getSharedP();

        int value = sp.getInt(key,defValue);

        return value;
    }


    /**
     * 获取int类型的值
     */
    public static <T> T getInfo(String key,T defValue) {
        sp = getSharedP();
        // 获取编辑器
        T value;
        if (defValue instanceof String) {
            value = (T)sp.getString(key,(String)defValue);
        } else if (defValue instanceof Integer) {
            value = (T)Integer.valueOf(sp.getInt(key,(Integer)defValue));
        } else if (defValue instanceof Boolean) {
            value = (T)Boolean.valueOf(sp.getBoolean(key,(Boolean)defValue));
        } else if (defValue instanceof Float) {
            value = (T)Float.valueOf(sp.getFloat(key,(Float)defValue));
        } else if (defValue instanceof Long) {
            value = (T)Long.valueOf(sp.getLong(key,(Long)defValue));
        } else {
            value = (T)sp.getString(key,defValue.toString());
        }
        return value;
    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public static void remove(String key) {
        SharedPreferences sp = getSharedP();
        Editor editor = sp.edit();
        editor.remove(key);
        editor.commit();
    }

    /**
     * 清除所有数据
     */
    public static void clear() {
        SharedPreferences sp = getSharedP();
        Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 查询某个key是否已经存在
     *
     * @param key
     * @return
     */
    public static boolean contains(String key) {
        SharedPreferences sp = getSharedP();
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     *
     * @return
     */
    public static Map<String,?> getAll() {
        SharedPreferences sp = getSharedP();
        return sp.getAll();
    }
}
