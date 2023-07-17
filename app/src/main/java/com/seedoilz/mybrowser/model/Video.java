package com.seedoilz.mybrowser.model;

public class Video {
    private String title;
    private String thumbnailUrl;
    private String videoUrl;

    public Video(String title, String thumbnailUrl, String videoUrl) {
        this.title = title;
        this.thumbnailUrl = thumbnailUrl;
        this.videoUrl = videoUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }
}
