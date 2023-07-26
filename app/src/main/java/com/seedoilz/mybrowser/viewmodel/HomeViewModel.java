package com.seedoilz.mybrowser.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.seedoilz.mybrowser.MyBrowserApp;
import com.seedoilz.mybrowser.model.Article;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private MutableLiveData<List<Article>> data;

    private List<Article> articles;
    public HomeViewModel() {
        data = new MutableLiveData<>();
    }

    public MutableLiveData<List<Article>> getData() {
        return data;
    }

    public void refresh() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                articles = MyBrowserApp.getDb().articleDao().getAll();
            }
        }).start();
        data.setValue(articles);
    }
}
