package com.leo.sleep.component.manager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.leo.sleep.modules.serializable.City;
import com.leo.sleep.modules.serializable.Province;
import com.leo.sleep.utils.SqCloseUtil;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 封装数据库操作
 */
public class CityDB {

    public CityDB() {

    }

    public static List<Province> loadProvinces(SQLiteDatabase db) {

        List<Province> list = new ArrayList<>();
        //向数据库中查找"T_Province"表的数据。
        Cursor cursor = db.query("T_Province", null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                Province province = new Province();
                province.proSort = cursor.getInt(cursor.getColumnIndex("ProSort"));
                province.proName = cursor.getString(cursor.getColumnIndex("ProName"));
                list.add(province);
            } while (cursor.moveToNext());
        }
        SqCloseUtil.closeQuietly(cursor);
        return list;
    }

    public static List<City> loadCities(SQLiteDatabase db, int ProID) {
        List<City> list = new ArrayList<>();
        Cursor cursor = db.query("T_City", null, "ProID = ?", new String[] { String.valueOf(ProID) }, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                City city = new City();
                city.cityName = cursor.getString(cursor.getColumnIndex("CityName"));
                city.proID = ProID;
                city.citySort = cursor.getInt(cursor.getColumnIndex("CitySort"));
                list.add(city);
            } while (cursor.moveToNext());
        }
        SqCloseUtil.closeQuietly(cursor);
        return list;
    }
}
