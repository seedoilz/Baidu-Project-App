package com.seedoilz.mybrowser;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.seedoilz.mybrowser.adapter.BarAdapter;
import com.seedoilz.mybrowser.adapter.VideoAdapter;
import com.seedoilz.mybrowser.bottombar.home;
import com.seedoilz.mybrowser.bottombar.user;
import com.seedoilz.mybrowser.bottombar.video;
import com.seedoilz.mybrowser.model.Video;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Fragment> mFragments;   //存放视图
    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private List<String> mtitle;  //存放底部标题
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.seedoilz.mybrowser.R.layout.activity_main);

        initView();
    }

    private void initView() {
        mTabLayout = findViewById(com.seedoilz.mybrowser.R.id.tablayout);
        viewPager = findViewById(com.seedoilz.mybrowser.R.id.viewpager);

        mFragments=new ArrayList<Fragment>();
        mFragments.add(new home());
        mFragments.add(new video());
        mFragments.add(new user());

        mtitle=new ArrayList<String>();
        mtitle.add("主页");
        mtitle.add("视频");
        mtitle.add("我的");

        //实例化适配器
        BarAdapter adapt = new BarAdapter(getSupportFragmentManager(), mFragments, mtitle);
        viewPager.setAdapter(adapt);

        mTabLayout.setupWithViewPager(viewPager);//给tab设置一个viewpager
        //viewpager的监听
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override  //选中
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        Toast.makeText(MainActivity.this, "这是主页", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        videoLoad();
                        Toast.makeText(MainActivity.this, "这是视频", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(MainActivity.this, "这是我的", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override   // //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
            public void onPageScrollStateChanged(int state) {

            }
        });



    }

    private void videoLoad(){
        // TODO
        // video标题数据获取
        ArrayList<Video> videos = new ArrayList<>();
        videos.add(new Video("Video 1", "https://i0.hdslb.com/bfs/archive/ffe9ca68c239b4f2523e43211ae1b516259ebd20.jpg@672w_378h_1c_!web-home-common-cover", "https://box.nju.edu.cn/seafhttp/files/3443e8f1-13b8-4b5b-a9d9-872b2e3ff0f4/Apex%20Legends%202023.06.17%20-%2015.27.20.06.DVR.mp4"));
        videos.add(new Video("Video 2", "http://example.com/thumbnail2.jpg", "http://example.com/video2.mp4"));
        videos.add(new Video("Video 3", "http://example.com/thumbnail3.jpg", "http://example.com/video3.mp4"));
        videos.add(new Video("Video 4", "http://example.com/thumbnail4.jpg", "http://example.com/video4.mp4"));

        VideoAdapter videoAdapter = new VideoAdapter(videos);

        RecyclerView recyclerView = findViewById(com.seedoilz.mybrowser.R.id.video_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(videoAdapter);

    }

}