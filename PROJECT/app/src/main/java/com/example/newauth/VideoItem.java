package com.example.newauth;


public class VideoItem {
   String title, videoUrl, thumbnail, subtitleUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getSubtitleUrl() {
        return subtitleUrl;
    }

    public void setSubtitleUrl(String subtitleUrl) {
        this.subtitleUrl = subtitleUrl;
    }

    public VideoItem(String subtitleUrl, String thumbnail, String title, String videoUrl) {
        this.title = title;
        this.videoUrl = videoUrl;
        this.thumbnail = thumbnail;
        this.subtitleUrl = subtitleUrl;
    }

    public VideoItem() {
    }
}
