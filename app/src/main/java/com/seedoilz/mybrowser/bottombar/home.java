package com.seedoilz.mybrowser.bottombar;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seedoilz.mybrowser.MyBrowserApp;
import com.seedoilz.mybrowser.activity.PublishNewsActivity;
import com.seedoilz.mybrowser.R;
import com.seedoilz.mybrowser.activity.SearchActivity;

import com.seedoilz.mybrowser.activity.SplashActivity;
import com.seedoilz.mybrowser.adapter.ArticleAdapter;
import com.seedoilz.mybrowser.databinding.FragmentHomeBinding;
import com.seedoilz.mybrowser.model.Article;
import com.seedoilz.mybrowser.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class home extends Fragment {

    private TextView weatherTextView;

    private List<Article> articles;

    private FragmentHomeBinding binding;
    private HomeViewModel viewModel;

    private ArticleAdapter articleAdapter;

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

        View refreshButton = rootView.findViewById(R.id.imageView);
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh(rootView);
            }
        });

        EditText mSearch = rootView.findViewById(R.id.search_content);
        mSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == android.view.KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    Intent intent = new Intent(getActivity(), SearchActivity.class);
                    intent.putExtra("query", mSearch.getText().toString());
                    startActivity(intent);
                    return true;
                }
                return false;
            }
        });

        ImageView searchView = rootView.findViewById(R.id.search_icon);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        binding = FragmentHomeBinding.inflate(inflater, container, false);

        // Initialize your ViewModel
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        // Set click listener to the ImageView
        binding.imageView.setOnClickListener(v -> viewModel.refresh());


        articleAdapter = new ArticleAdapter(new ArrayList<>());
        binding.recyclerView.setAdapter(articleAdapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        // Observe the LiveData in your ViewModel
        viewModel.getData().observe(getViewLifecycleOwner(), newData -> {
            // Set the new data to your TextView
            articleAdapter.setData(newData);
        });

        refresh(rootView);

        return rootView;
    }

    private void refresh(View rootView) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                articles = MyBrowserApp.getDb().articleDao().getAll();
            }
        }).start();

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ArticleAdapter(articles));
    }
}
