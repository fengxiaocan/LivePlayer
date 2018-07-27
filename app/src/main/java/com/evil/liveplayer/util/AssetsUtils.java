package com.evil.liveplayer.util;

import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * @author wlj
 * @date 2017/3/29
 * @email wanglijundev@gmail.com
 * @packagename wanglijun.vip.androidutils.utils
 * @desc: Assets获取的相关操作类
 */

public class AssetsUtils {
    private static final String ENCODING = "UTF-8";

    /**
     * 从assets获取文件
     *
     * @param fileName
     * @return
     * @throws IOException
     */
    public static InputStream getFileFromAssets(String fileName) throws IOException {
        AssetManager am = Utils.getContext().getAssets();
        return am.open(fileName);
    }

    /**
     * 从assets获取文本文件
     *
     * @param fileName
     * @return
     */
    public static String getTextFromAssets(String fileName, Charset charset) {
        try {
            InputStreamReader inputReader = new InputStreamReader(getFileFromAssets(fileName),charset);
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            StringBuilder result = new StringBuilder();
            while ((line = bufReader.readLine()) != null) {
                result.append(line);
            }
            return result.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 从assets获取文本文件
     *
     * @param fileName
     * @return
     */
    public static String getTextFromAssets(String fileName) {
        return getTextFromAssets(fileName,Charset.forName(ENCODING));
    }
}
