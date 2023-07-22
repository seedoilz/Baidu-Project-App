package com.seedoilz.mybrowser.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.seedoilz.mybrowser.FullScreenActivity;
import com.seedoilz.mybrowser.R;
import com.seedoilz.mybrowser.model.Video;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    private List<Video> videos;

    public VideoAdapter(List<Video> videos) {
        this.videos = videos;
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        PlayerView playerView;
        ImageView imageView;

        public VideoViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_video_title);
            playerView = itemView.findViewById(R.id.player_view);
            imageView = itemView.findViewById(R.id.exo_fullscreen);
        }
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_item, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        Video video = videos.get(position);
        holder.title.setText(video.getTitle());
        String thumbnailUrl = video.getThumbnailUrl();

        SimpleExoPlayer player = new SimpleExoPlayer.Builder(holder.itemView.getContext())
                .setTrackSelector(new DefaultTrackSelector(holder.itemView.getContext()))
                .build();

        holder.playerView.setPlayer(player);

        String videoUrl = video.getVideoUrl();
        String userAgent = "MyVideoApp/1.0";

        DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory(userAgent);
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(MediaItem.fromUri(videoUrl));

        player.setMediaSource(mediaSource);
        player.prepare();

        holder.playerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!player.isPlaying()) {
                    player.play();
                }
            }
        });

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player.isPlaying()){
                    player.stop();
                }
                Intent fullscreenIntent = new Intent(holder.itemView.getContext(), FullScreenActivity.class);
                fullscreenIntent.putExtra("videoUrl", video.getVideoUrl());
                fullscreenIntent.putExtra("videoTitle", video.getTitle());
                holder.itemView.getContext().startActivity(fullscreenIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }
}
