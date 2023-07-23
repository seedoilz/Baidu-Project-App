package com.seedoilz.mybrowser.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.seedoilz.mybrowser.MyBrowserApp;
import com.seedoilz.mybrowser.R;
import com.seedoilz.mybrowser.model.Article;

import java.io.File;
import java.util.concurrent.CountDownLatch;

public class ArticleDisplayActivity extends AppCompatActivity {

    private Article article;
    private TextView articleTitle;
    private TextView articleTitleShow;
    private ImageView articleImage;
    private TextView articleContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_article);

        Intent intent = getIntent();
        String article_id = intent.getStringExtra("article_id");

        articleTitle = findViewById(R.id.article_title);
        articleTitleShow = findViewById(R.id.article_title_show);
        articleImage = findViewById(R.id.article_image);
        articleContent = findViewById(R.id.article_content);

        ImageView backImage = findViewById(R.id.back_image);
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Handler handler = new Handler();

        new Thread(new Runnable() {
            @Override
            public void run() {
                assert article_id != null;
                int id = Integer.parseInt(article_id);
                article = MyBrowserApp.getDb().articleDao().getOneById(id);

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (article != null) {
                            articleTitle.setText(article.title);
                            articleTitleShow.setText(article.title);
                            if (article.thumbnailPath.startsWith("http")) {
                                Glide.with(ArticleDisplayActivity.this).load(article.thumbnailPath).into(articleImage);
                            } else {
                                Glide.with(ArticleDisplayActivity.this).load(new File(article.thumbnailPath)).into(articleImage);
                            }
                            articleContent.setText(article.content);
                        } else {
                            Toast.makeText(ArticleDisplayActivity.this, "加载错误", Toast.LENGTH_SHORT).show();
//                            onBackPressed();
                        }
                    }
                });
            }
        }).start();


//        if (article != null) {
//            articleTitle.setText(article.title);
//            articleTitleShow.setText(article.title);
//            if (article.thumbnailPath.startsWith("http")) {
//                Glide.with(this).load(article.thumbnailPath).into(articleImage);
//            } else {
//                Glide.with(this).load(new File(article.thumbnailPath)).into(articleImage);
//            }
//            articleContent.setText(article.content);
//        } else {
//            Toast.makeText(ArticleDisplayActivity.this, "加载错误", Toast.LENGTH_SHORT).show();
////            onBackPressed();
//        }
    }

}
