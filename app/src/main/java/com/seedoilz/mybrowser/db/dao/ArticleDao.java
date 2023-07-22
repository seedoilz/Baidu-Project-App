package com.seedoilz.mybrowser.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.seedoilz.mybrowser.model.Article;

import java.util.List;

import io.reactivex.Completable;

@Dao
public interface ArticleDao {
    @Query("SELECT * FROM article")
    List<Article> getAll();

    @Query("SELECT * FROM article WHERE id IN (:articleIds)")
    List<Article> loadAllByIds(int[] articleIds);

    @Insert
    Completable insertAll(Article... articles);

    @Delete
    void delete(Article article);
}
