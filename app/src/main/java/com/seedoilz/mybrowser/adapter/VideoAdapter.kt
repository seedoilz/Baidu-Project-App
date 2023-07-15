package com.seedoilz.mybrowser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.seedoilz.mybrowser.R
import com.seedoilz.mybrowser.model.Video

class VideoAdapter(private val videos: List<Video>) : RecyclerView.Adapter<VideoAdapter.VideoViewHolder>() {

    inner class VideoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.tv_video_title)
        val thumbnail: ImageView = itemView.findViewById(R.id.iv_video_thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.video_item, parent, false)
        return VideoViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = videos[position]
        holder.title.text = video.title
        // Assuming you are using Glide to load images
        Glide.with(holder.thumbnail.context).load(video.thumbnailUrl).into(holder.thumbnail)

        holder.itemView.setOnClickListener {
            // TODO
            // Implement your video playing logic here
            // For example, you can use an Intent to open the video in a new Activity
        }
    }

    override fun getItemCount() = videos.size
}
