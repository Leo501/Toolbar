package com.leo.sleep.modules.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.leo.sleep.R;
import com.leo.sleep.base.BaseApplication;
import com.leo.sleep.utils.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 用于打开省市的数据库
 * Created by Leo on 2016/9/25.
 */
public class DBManager {

    private static String TAG = DBManager.class.getSimpleName();
    private final int BUFFER_SIZE=400000;
    public static final String DB_NAME="china_city.db";
    public static final String PACKAGE_NAME="com.leo.sleep";
    public static final String DB_PATH="/data" + Environment.getDataDirectory().getAbsolutePath() + "/" +
            PACKAGE_NAME;  //在手机里存放数据库的位置(/data/data/com.xiecc.seeWeather/china_city.db)
    private SQLiteDatabase database;
    private Context context;

    private static DBManager dbInstance=new DBManager();

    public DBManager(Context context){
        this.context=context;
    }

    public DBManager(){
        if (null==dbInstance){
            dbInstance=new DBManager(BaseApplication.getAppContext());
        }
    }

    public static DBManager getInstance(){
        return dbInstance;
    }

    public SQLiteDatabase getDatabase(){
        return database;
    }

    public void setDatabase(SQLiteDatabase database){
        this.database=database;
    }

    public void openDatabase(){
        this.database=this.openDatabase(DB_PATH+"/"+DB_NAME);
    }

    private SQLiteDatabase openDatabase(String dbfile){
        try {
            if (!(new File(dbfile).exists())){
                InputStream inputStream=BaseApplication.getAppContext().getResources()
                        .openRawResource(R.raw.china_city);
                FileOutputStream fileOutputStream=new FileOutputStream(dbfile);
                byte[] buffer = new byte[1024];
                while (true){
                    if (inputStream.available()<1024){
                        int c=-1;
                        //输出流里写入一个值为 b 的字节。需要注意的是，实际写入的是 int 类型 b 的低8位，
                        // 其余的 24 位被忽略。
                        while ((c=inputStream.read())!=-1){
                            fileOutputStream.write(c);
                        }
                        break;
                    }else {
                        inputStream.read(buffer);
                        fileOutputStream.write(buffer);
                    }
                }
                fileOutputStream.close();
                inputStream.close();
            }
            return SQLiteDatabase.openOrCreateDatabase(dbfile,null);
        }catch (FileNotFoundException e) {
            LogUtil.e("File not found");
            e.printStackTrace();
        } catch (IOException e) {
            LogUtil.e("IO exception");
            e.printStackTrace();
        }
        return null;
    }

    public void closeDatabase(){
        if (this.database!=null){
            this.database.close();
        }
    }
}
