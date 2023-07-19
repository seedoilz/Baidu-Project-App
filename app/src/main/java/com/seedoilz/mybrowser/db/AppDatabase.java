package com.seedoilz.mybrowser.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.seedoilz.mybrowser.db.bean.Province;
import com.seedoilz.mybrowser.db.dao.ProvinceDao;

/**
 * Room数据库类
 */
@Database(entities = {Province.class},version = 1,exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "MyBrowserNew";
    private static volatile AppDatabase mInstance;

    public abstract ProvinceDao provinceDao();

    /**
     * 单例模式
     */
    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).build();
                }
            }
        }
        return mInstance;
    }
}