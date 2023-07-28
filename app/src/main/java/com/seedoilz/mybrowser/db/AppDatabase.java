package com.seedoilz.mybrowser.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.seedoilz.mybrowser.db.bean.Province;
import com.seedoilz.mybrowser.db.dao.ArticleDao;
import com.seedoilz.mybrowser.db.dao.ProvinceDao;
import com.seedoilz.mybrowser.db.dao.UserDao;
import com.seedoilz.mybrowser.model.Article;
import com.seedoilz.mybrowser.model.User;


@Database(entities = {Province.class, Article.class, User.class}, version = 3, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "MyBrowserNew";
    private static volatile AppDatabase mInstance;
    public abstract ProvinceDao provinceDao();
    public abstract ArticleDao articleDao();
    public abstract UserDao userDao();

    
    public static AppDatabase getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AppDatabase.class) {
                if (mInstance == null) {
                    mInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME).fallbackToDestructiveMigration().build();
                }
            }
        }
        return mInstance;
    }
}
