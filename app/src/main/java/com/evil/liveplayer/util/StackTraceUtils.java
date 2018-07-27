package com.evil.liveplayer.util;

/**
 * The type Stack trace utils.
 */
public class StackTraceUtils {
    private StackTraceUtils() {
    }

    /**
     * 获取方法栈
     *
     * @return stack trace element [ ]
     */
    public static StackTraceElement[] getStackTrace() {
        return Thread.currentThread().getStackTrace();
    }

    /**
     * Gets method name.
     * 获取方法名
     *
     * @param index the index 第几个区间
     * @return the method name 方法名
     */
    public static String getMethodName(int index) {
        try {
            return getStackTrace()[index].getMethodName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Gets line number.
     * 获取代码运行所在的类的第几行
     *
     * @param index the index 第几个区间
     * @return the line number
     */
    public static int getLineNumber(int index) {
        try {
            return getStackTrace()[index].getLineNumber();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * Gets file name.
     * 获取源码文件名
     *
     * @param index the index 第几个区间
     * @return the file name
     */
    public static String getFileName(int index) {
        try {
            return getStackTrace()[index].getFileName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Gets class name.
     * 获取当前所在的类的名字
     *
     * @param index the index 第几个区间
     * @return the class name
     */
    public static String getClassName(int index) {
        try {
            return getStackTrace()[index].getClassName();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Gets current method name.
     * 获取当前所调用的方法
     *
     * @return the current method name
     */
    public static String getCurrentMethodName() {
        return getMethodName(6);
    }

    /**
     * Gets current line number.
     * 获取当前所调用的方法的第几行
     *
     * @return the current line number
     */
    public static int getCurrentLineNumber() {
        return getLineNumber(6);
    }

    /**
     * Gets current file name.
     * 获取当前所调用的类的文件名
     *
     * @return the current file name
     */
    public static String getCurrentFileName() {
        return getFileName(6);
    }

    /**
     * Gets current class name.
     * 获取当前所调用的类
     *
     * @return the current class name
     */
    public static String getCurrentClassName() {
        return getClassName(6);
    }
}
