package com.seedoilz.mybrowser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.seedoilz.mybrowser.R
import com.seedoilz.mybrowser.model.Video

class VideoAdapter(private val videos: List<Video>) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_video_title)
//        val thumbnail: ImageView = itemView.findViewById(R.id.iv_video_thumbnail)
        val playerView: PlayerView = itemView.findViewById(R.id.player_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.title.text = video.title
        val thumbnailUrl = video.thumbnailUrl // 替换为您的缩略图 URL
//        val previewImageView = playerView.findViewById<ImageView>(R.id.player_view)

//        Glide.with(holder.itemView.context)
//            .load(thumbnailUrl)
//            .into(holder.playerView.)

//        Glide.with(holder.thumbnail.context).load(video.thumbnailUrl).into(holder.thumbnail)

        val player: SimpleExoPlayer = SimpleExoPlayer.Builder(holder.itemView.context)
            .setTrackSelector(DefaultTrackSelector(holder.itemView.context))
            .build()

        holder.playerView.player = player

        val videoUrl = video.videoUrl
        val userAgent = "MyVideoApp/1.0" // 替换为您的 User Agent

        val dataSourceFactory = DefaultHttpDataSourceFactory(userAgent)
        val mediaSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
            .createMediaSource(MediaItem.fromUri(videoUrl))

        player.setMediaSource(mediaSource)
        player.prepare()

        holder.playerView.setOnClickListener {
            if (!player.isPlaying){
                player.play()
            }
        }
    }

    override fun getItemCount() = videos.size
}
