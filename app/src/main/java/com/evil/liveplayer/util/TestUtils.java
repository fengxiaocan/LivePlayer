package com.evil.liveplayer.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.Socket;

import static com.evil.liveplayer.util.Utils.IsAppDebug;

/**
 * @项目名： Advertisement
 * @包名：
 * @创建者: feng
 * @时间: 9:49
 * @描述： 测试工具类
 */
public class TestUtils{

    /**
     * 打印日志
     */
    public static void log(String msg){
        if(msg == null){
            msg = "null";
        }
        if(IsAppDebug){
            Log.e("evil-test",msg);
        }
    }

    /**
     * 测试运行时间
     */
    public static void runTime(CallBack callback){
        long startTime = System.currentTimeMillis();
        callback.mothod();
        long endTime = System.currentTimeMillis();
        if(IsAppDebug){
            TestUtils.log("time = " + (endTime - startTime));
        }
    }

    /**
     * 上传文件
     *
     * @param filePath
     */
    public static void updataFile(String filePath,String ip,int port){
        File file = new File(filePath);
        updataFile(file,ip,port);
    }

    /**
     * 上传调试文件
     */
    public static void updataFile(File file,String ip,int port){
        new Thread(new UpdataRunnable(file,ip,port)).start();
    }

    public static class UpdataRunnable implements Runnable{

        private File mFile;
        private String ip;
        private int port;

        public UpdataRunnable(File file,String ip,int port){
            mFile = file;
            this.ip = ip;
            this.port = port;
        }

        @Override
        public void run(){
            if(mFile == null || !mFile.exists()){
                return;
            }
            try{
                Socket socket = new Socket(ip,port);
                OutputStream os = socket.getOutputStream();
                byte[] arr = new byte[(int) mFile.length()];
                FileInputStream fis = new FileInputStream(mFile);
                fis.read(arr);
                os.write(arr);
                fis.close();
                os.close();
                socket.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public interface CallBack{

        void mothod();
    }
}
