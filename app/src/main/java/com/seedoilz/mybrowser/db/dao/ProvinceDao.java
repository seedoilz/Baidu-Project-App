package com.seedoilz.mybrowser.db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.seedoilz.mybrowser.db.bean.Province;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ProvinceDao {

    @Query("SELECT * FROM Province")
    Flowable<List<Province>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertAll(Province... provinces);
}
