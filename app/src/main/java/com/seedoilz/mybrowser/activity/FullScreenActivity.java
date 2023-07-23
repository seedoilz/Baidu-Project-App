package com.seedoilz.mybrowser.activity;// VideoPlayerActivity.java

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.seedoilz.mybrowser.R;

public class FullScreenActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private TextView txtTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fullscreen_video);

        // 获取 Intent 数据
        Intent intent = getIntent();
        String videoUrl = intent.getStringExtra("videoUrl");
        String videoTitle = intent.getStringExtra("videoTitle");

        // 初始化视图
        ImageView backImage = findViewById(R.id.back_image);
        playerView = findViewById(R.id.playerView);
        txtTitle = findViewById(R.id.txtTitle);

        // 设置标题
        txtTitle.setText(videoTitle);

        // 返回按钮点击事件
        backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                player.release();
                onBackPressed();
            }
        });

        // 初始化 ExoPlayer
        SimpleExoPlayer player = new SimpleExoPlayer.Builder(this)
                .setTrackSelector(new DefaultTrackSelector(this))
                .build();
        playerView.setPlayer(player);

        // 创建媒体资源
        Uri uri = Uri.parse(videoUrl);
        MediaSource mediaSource = buildMediaSource(uri);

        // 准备并播放视频
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);
    }

    private MediaSource buildMediaSource(Uri uri) {
        // 创建数据源工厂
        String userAgent = Util.getUserAgent(this, getString(R.string.app_name));
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, userAgent);

        // 创建渐进式媒体资源
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
    }
}
