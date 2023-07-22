package com.seedoilz.mybrowser.bottombar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seedoilz.mybrowser.MyBrowserApp;
import com.seedoilz.mybrowser.PublishNewsActivity;
import com.seedoilz.mybrowser.R;
import com.seedoilz.mybrowser.SplashActivity;
import com.seedoilz.mybrowser.adapter.ArticleAdapter;
import com.seedoilz.mybrowser.model.Article;

import java.util.List;

public class home extends Fragment {

    private TextView weatherTextView;

    private List<Article> articles;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        weatherTextView = rootView.findViewById(R.id.iv_weather);
        ValueAnimator alphaAnimator = ObjectAnimator.ofFloat(0f, 1f);
        alphaAnimator.setDuration(5000);  // 设置动画持续时间为5s
        alphaAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float alpha = (float) animation.getAnimatedValue();

                // 设置按钮的透明度
                weatherTextView.setAlpha(alpha);
            }
        });
        alphaAnimator.start();  // 启动动画
        weatherTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SplashActivity.class);
                startActivity(intent);
            }
        });

        View addNewsView = rootView.findViewById(R.id.add_button);
        addNewsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PublishNewsActivity.class);
                startActivity(intent);
            }
        });


        new Thread(new Runnable() {
            @Override
            public void run() {
                articles = MyBrowserApp.getDb().articleDao().getAll();
            }
        }).start();

        RecyclerView recyclerView = rootView.findViewById(R.id.article_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ArticleAdapter(articles));


        return rootView;
    }

}
