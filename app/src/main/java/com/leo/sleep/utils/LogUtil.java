package com.leo.sleep.utils;

import android.util.Log;

import com.leo.sleep.BuildConfig;
import com.leo.sleep.base.BaseApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Leo on 2016/9/25.
 */
public class LogUtil {

    public static boolean isDebug= BuildConfig.DEBUG;
    public static final String PATH= BaseApplication.cacheDir;
    public static final String LOGUTIL_FILE_NAME="log.txt";

    //是否写入日志文件
    public static final boolean LOGUTIL_WRITE_TO_FILE=true;

    public static void e(String TAG,String msg){
        Log.e(TAG,msg);
        if (LOGUTIL_WRITE_TO_FILE){
            writeLogToFile("e",TAG,msg);
        }
    }

    public static void w(String TAG,String msg){
        if (isDebug){
            Log.w(TAG,msg);
            if (LOGUTIL_WRITE_TO_FILE){
                writeLogToFile("w",TAG,msg);
            }
        }
    }

    public static void d(String TAG,String msg){
        if (isDebug){
            Log.d(TAG,log(msg));
            if (LOGUTIL_WRITE_TO_FILE){
                writeLogToFile("d",TAG,msg);
            }
        }
    }

    public static void i(String TAG,String msg){
        if (isDebug){
            Log.i(TAG,log(msg));
            if (LOGUTIL_WRITE_TO_FILE){
                writeLogToFile("i",TAG,msg);
            }
        }
    }

    public static void e(String msg) {
        e(getClassName(), msg);
    }

    public static void w(String msg) {
        w(getClassName(), msg);
    }

    public static void d(String msg) {
        d(getClassName(), msg);
    }

    public static void i(String msg) {
        i(getClassName(), msg);
    }

    private static void writeLogToFile(String type, String tag, String msg) {
        isExist(PATH);
        //isDel();
        String needWriteMessage = "\r\n"
                + TimeUtil.getNowMDHMSTime()
                + "\r\n"
                + type
                + "    "
                + tag
                + "\r\n"
                + msg;
        File file = new File(PATH, LOGUTIL_FILE_NAME);
        try {
            FileWriter filerWriter = new FileWriter(file, true);
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);
            bufWriter.write(needWriteMessage);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除日志文件
     */
    public static void delFile() {
        File file = new File(PATH, LOGUTIL_FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 判断文件夹是否存在,如果不存在则创建文件夹
     */
    public static void isExist(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.mkdirs();
            } catch (Exception e) {
                LogUtil.e(e.getMessage());
            }
        }
    }

    /**
     * @return 当前的类名(simpleName)
     */
    private static String getClassName() {

        String result;
        StackTraceElement thisMethodStack = Thread.currentThread().getStackTrace()[2];
        result = thisMethodStack.getClassName();
        int lastIndex = result.lastIndexOf(".");
        result = result.substring(lastIndex + 1);

        int i = result.indexOf("$");// 剔除匿名内部类名

        return i == -1 ? result : result.substring(0, i);
    }

    /**
     * 打印 Log 行数位置
     */
    private static String log(String message) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[5];
        String className = targetElement.getClassName();
        className = className.substring(className.lastIndexOf('.') + 1) + ".java";
        int lineNumber = targetElement.getLineNumber();
        if (lineNumber < 0) lineNumber = 0;
        return "(" + className + ":" + lineNumber + ") " + message;
    }

}
