package com.seedoilz.mybrowser;

import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.seedoilz.mybrowser.bottombar.BarAdapter;
import com.seedoilz.mybrowser.bottombar.home;
import com.seedoilz.mybrowser.bottombar.user;
import com.seedoilz.mybrowser.bottombar.video;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    private List<Fragment> mFragments;   //存放视图
    private ViewPager viewPager;
    private TabLayout mTabLayout;
    private List<String> mtitle;  //存放底部标题
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mTabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        mFragments=new ArrayList<Fragment>();
        mFragments.add(new home());
        mFragments.add(new video());
        mFragments.add(new user());

        mtitle=new ArrayList<String>();
        mtitle.add("信息");
        mtitle.add("联系人");
        mtitle.add("发现");
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
                        Toast.makeText(MainActivity2.this, "这是信息", Toast.LENGTH_SHORT).show();
                        break;

                    case 1:
                        Toast.makeText(MainActivity2.this, "这是联系人", Toast.LENGTH_SHORT).show();
                        break;

                    case 2:
                        Toast.makeText(MainActivity2.this, "这是发现", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(MainActivity2.this, "这是我的", Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override   // //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}