package com.seedoilz.mybrowser.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.seedoilz.mybrowser.R;
import com.seedoilz.mybrowser.activity.ArticleDisplayActivity;
import com.seedoilz.mybrowser.activity.FullScreenActivity;
import com.seedoilz.mybrowser.model.Article;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder> {
    private List<Article> articles;

    public ArticleAdapter(List<Article> articles) {
        this.articles = articles;
    }

    @NonNull
    @Override
    public ArticleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 这里你需要将你的列表项布局传入 LayoutInflater 的 inflate 方法
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_article, parent, false);
        return new ArticleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleViewHolder holder, int position) {
        // 获取对应位置的 Article
        Article article = articles.get(position);
        // 绑定数据到 ViewHolder 上
        holder.bind(article);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fullscreenIntent = new Intent(holder.itemView.getContext(), ArticleDisplayActivity.class);
                fullscreenIntent.putExtra("article_id", String.valueOf(article.id));
                holder.itemView.getContext().startActivity(fullscreenIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (articles == null){
            return 0;
        }
        return articles.size();
    }

    class ArticleViewHolder extends RecyclerView.ViewHolder {
        TextView title, summary;
        ImageView thumbnail, image;

        public ArticleViewHolder(@NonNull View itemView) {
            super(itemView);
            // 在这里找到你的视图元素并初始化它们
            title = itemView.findViewById(R.id.title);
            summary = itemView.findViewById(R.id.summary);
            thumbnail = itemView.findViewById(R.id.thumbnail);
        }

        public void bind(Article article) {
            title.setText(article.title);
            summary.setText(article.summary);
            if (article.thumbnailPath.startsWith("http")){
                Glide.with(itemView.getContext()).load(article.thumbnailPath).into(thumbnail);
            }
            else{
                Glide.with(itemView.getContext()).load(new File(article.thumbnailPath)).into(thumbnail);
            }
        }
    }

    public void setData(List<Article> newData) {
        this.articles = newData;
        notifyDataSetChanged();
    }
}

