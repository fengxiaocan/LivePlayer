package com.evil.liveplayer.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;


import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *     time  : 2016/08/11
 *     desc  : SD卡相关工具类
 * </pre>
 */
public final class SDCardUtils{

    private SDCardUtils(){
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean sdCardExist(){
        return Environment.MEDIA_MOUNTED
                .equals(Environment.getExternalStorageState());
    }

    /**
     * 获取SD卡路径
     * <p>先用shell，shell失败再普通方法获取，一般是/storage/emulated/0/</p>
     *
     * @return SD卡路径 string
     */
    public static String getSDCardPath(){
        if(!sdCardExist()){
            return null;
        }
        return getSdRoot().getAbsolutePath();
    }

    /**
     * 获取系统SD存储路径
     *
     * @return the string
     */
    public static String getSdRootPath(){
        return getSdRoot().getAbsolutePath();
    }

    /**
     * 获取sd卡根目录
     *
     * @return the file
     */
    public static File getSdRoot(){
        File sdDir = Environment.getExternalStorageDirectory();//获取根目录
        return sdDir;
    }

    /**
     * 获取保存目录
     *
     * @param name
     *         the name
     *
     * @return the file
     */
    public static File getSaveDir(String name){
        File saveDir;
        if(sdCardExist()){
            saveDir = new File(getSdRoot(),Utils.getContext()
                                                .getPackageName() + "/" +
                    name);//获取根目录
        }else{
            saveDir = new File(Utils.getContext().getFilesDir(),name);
        }
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        return saveDir;
    }

    /**
     * 获取文件
     *
     * @param fileName
     *         文件名
     * @param parentName
     *         父类名
     *
     * @return 一个新文件
     */
    public static File getFileOfSave(String fileName,String parentName){
        File file;
        File saveDir;
        if(sdCardExist()){
            saveDir = new File(getSdRoot(),parentName);
            if(!saveDir.exists()){
                saveDir.mkdirs();
            }
            file = new File(saveDir,fileName);
        }else{
            file = new File(Utils.getContext().getFilesDir(),
                            parentName + "/" + fileName);
        }
        return file;
    }

    /**
     * 获取文件
     *
     * @param fileName
     *         文件名
     *
     * @return 一个新文件
     */
    public static File getFileOfSave(String fileName){
        return getFileOfSave(fileName,Utils.getPackageName());
    }

    /**
     * 获取保存目录
     *
     * @param name
     *         the name
     *
     * @return the file
     */
    public static File getSdSaveDir(String name){
        if(sdCardExist()){
            File saveDir = new File(getSdRoot(),name);
            if(!saveDir.exists()){
                saveDir.mkdirs();
            }
            return saveDir;
        }
        throw new NullPointerException("SD卡不存在");
    }

    /**
     * 获取公有的保存目录
     *
     * @param type
     *         the type
     * @param name
     *         the name
     *
     * @return the file
     */
    public static File getPublicDir(
                    String type,String name){
        if(sdCardExist()){
            File saveDir = new File(getPublicDir(type),name);
            if(!saveDir.exists()){
                saveDir.mkdirs();
            }
            return saveDir;
        }
        throw new NullPointerException("SD卡不存在");
    }

    /**
     * 获取公有的保存目录
     *
     * @param type
     *         the type
     *
     * @return the file
     */
    public static File getPublicDir(
                    String type){
        if(sdCardExist()){
            return Environment.getExternalStoragePublicDirectory(type);
        }
        throw new NullPointerException("SD卡不存在");
    }


    /**
     * 获取SD卡data路径
     *
     * @return SD卡data路径 string
     */
    public static String getPackagePath(){
        if(!sdCardExist()){
            return null;
        }
        return getSdRoot().getAbsolutePath() + "/Android/data/" + Utils
                .getContext().getPackageName();
    }


    /**
     * 创建一个文件
     *
     * @param parentName
     *         the parent name
     * @param fileName
     *         the file name
     *
     * @return file
     */
    public static File newSaveFile(String parentName,String fileName){
        return new File(getSaveDir(parentName),fileName);
    }

    private static File newSaveFile(File parent,String name){
        return new File(parent,name);
    }

    /**
     * 获取SD卡信息
     *
     * @return SDCardInfo sd card info
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public static SDCardInfo getSDCardInfo(){
        if(!sdCardExist()){
            return null;
        }
        SDCardInfo sd = new SDCardInfo();
        sd.isExist = true;
        StatFs sf = new StatFs(
                Environment.getExternalStorageDirectory().getAbsolutePath());
        sd.totalBlocks = sf.getBlockCountLong();
        sd.blockByteSize = sf.getBlockSizeLong();
        sd.availableBlocks = sf.getAvailableBlocksLong();
        sd.availableBytes = sf.getAvailableBytes();
        sd.freeBlocks = sf.getFreeBlocksLong();
        sd.freeBytes = sf.getFreeBytes();
        sd.totalBytes = sf.getTotalBytes();
        return sd;
    }

    /**
     * 遍历文件夹
     *
     * @param dir
     * @param list
     * @param filter
     */
    public static void findFile(File dir,List<File> list,FileFilter filter){
        if(dir == null){
            return;
        }
        File[] files = dir.listFiles(filter);
        if(files == null){
            return;
        }
        for(File file : files){
            if(file.isDirectory()){
                findFile(dir,list,filter);
            }else{
                list.add(file);
            }
        }
    }

    /**
     * 遍历文件夹
     *
     * @param dir
     * @param filter
     */
    public static List<File> findFiles(File dir,FileFilter filter){
        List<File> list = null;
        if(dir == null){
            return list;
        }
        list = new ArrayList<>();
        findFile(dir,list,filter);
        return list;
    }


    /**
     * The type Sd card info.
     */
    public static class SDCardInfo{

        /**
         * The Is exist.
         */
        boolean isExist;
        /**
         * The Total blocks.
         */
        long    totalBlocks;
        /**
         * The Free blocks.
         */
        long    freeBlocks;
        /**
         * The Available blocks.
         */
        long    availableBlocks;
        /**
         * The Block byte size.
         */
        long    blockByteSize;
        /**
         * The Total bytes.
         */
        long    totalBytes;
        /**
         * The Free bytes.
         */
        long    freeBytes;
        /**
         * The Available bytes.
         */
        long    availableBytes;
    }
}