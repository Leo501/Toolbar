package com.leo.sleep.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * Created by Leo on 2016/9/27.
 */
public class WeatherUtil {

    /**
     * Java 中有一个 Closeable 接口,标识了一个可关闭的对象,它只有一个 close 方法.
     */
    public static void closeQuietly(Closeable closeable) {
        if (null != closeable) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
