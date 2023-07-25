package com.seedoilz.mybrowser.activity;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.seedoilz.mybrowser.R;

public class SearchActivity extends AppCompatActivity {
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_results);

        mWebView = findViewById(R.id.web_view);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(final WebView view, final String url) {

                WebView.HitTestResult hit = view.getHitTestResult();
                //hit.getExtra()为null或者hit.getType() == 0都表示即将加载的URL会发生重定向，需要做拦截处理
                if (TextUtils.isEmpty(hit.getExtra()) || hit.getType() == 0) {
                    //通过判断开头协议就可解决大部分重定向问题了，有另外的需求可以在此判断下操作
                    Log.e("重定向", "重定向: " + hit.getType() + " && EXTRA（）" + hit.getExtra() + "------");
                    Log.e("重定向", "GetURL: " + view.getUrl() + "\n" +"getOriginalUrl()"+ view.getOriginalUrl());
                    Log.d("重定向", "URL: " + url);
                }

                if (url.startsWith("http://") || url.startsWith("https://")) { //加载的url是http/https协议地址
                    view.loadUrl(url);
                    return true; //返回false表示此url默认由系统处理,url未加载完成，会继续往下走
                } else { //加载的url是自定义协议地址
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        startActivity(intent);
                        return true;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return true;
                }

            }
        });


        ImageView back = findViewById(R.id.back);
        ImageView forward = findViewById(R.id.forward);
        ImageView home = findViewById(R.id.home);

        back.setOnClickListener(v -> {
            if (mWebView.canGoBack()) {
                mWebView.goBack();
            }
        });

        forward.setOnClickListener(v -> {
            if (mWebView.canGoForward()) {
                mWebView.goForward();
            }
        });

        home.setOnClickListener(v -> mWebView.loadUrl("https://www.baidu.com"));


        String query = getIntent().getStringExtra("query");
        mWebView.loadUrl("https://www.baidu.com/s?wd=" + Uri.encode(query));
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

}
