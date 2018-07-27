package com.evil.liveplayer.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.evil.liveplayer.util.FileUtils.getFile;

/**
 * The type Save utils.
 *
 * @name： BaseApp
 * @package： com.dgtle.baselib.util
 * @author: Noah.冯 QQ:1066537317
 * @time: 17 :18
 * @version: 1.1
 * @desc： 保存工具类
 */
public final class SaveObjectUtils {

    /**
     * Save.
     *
     * @param file
     *         the file
     * @param value
     *         the value
     */
    public static void save(File file,Object value){
        FileOutputStream fos = null;
        ObjectOutputStream os = null;
        try{
            fos = new FileOutputStream(file);
            os = new ObjectOutputStream(fos);
            os.writeObject(value);
            os.close();
        }catch(Exception e){
        }finally{
            CloseUtils.closeIO(fos);
            CloseUtils.closeIO(os);
        }
    }

    /**
     * Save.
     *
     * @param fileName
     *         the file name
     * @param value
     *         the value
     */
    public static void save(String fileName,Object value){
        save(getFile(fileName),value);
    }

    /**
     * Save.
     *
     * @param <T>
     *         the type parameter
     * @param file
     *         the file
     *
     * @return the t
     */
    public static <T> T get(File file){
        FileInputStream fis = null;
        ObjectInputStream os = null;
        T t = null;
        try{
            fis = new FileInputStream(file);
            os = new ObjectInputStream(fis);
            t = (T) os.readObject();
            os.close();
        }catch(Exception e){
        }finally{
            CloseUtils.closeIO(fis);
            CloseUtils.closeIO(os);
        }
        return t;
    }

    /**
     * Save.
     *
     * @param <T>
     *         the type parameter
     * @param fileName
     *         the file path name
     *
     * @return the t
     */
    public static <T> T get(String fileName){
        return get(getFile(fileName));
    }
}
