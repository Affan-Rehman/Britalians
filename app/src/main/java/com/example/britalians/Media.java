package com.example.britalians;

import java.io.Serializable;

public class Media implements Serializable {
    String channels;
    String bitrate;
    String duration;
    String fileSize;
    String framerate;
    String height;
    String type;
    String width;
    String isDefault;
    String url;
    String dim;
    String description;
    String keywords;
    String thumbnail_url;
    String title;

    public Media() {
        this.channels = "";
        this.bitrate = "";
        this.duration = "";
        this.fileSize = "";
        this.framerate = "";
        this.height = "";
        this.type = "";
        this.width = "";
        this.isDefault = "";
        this.url = "";
        this.dim = "";
        this.description = "";
        this.keywords = "";
        this.thumbnail_url = "";
        this.title = "";
    }

    public Media(String channels, String bitrate, String duration, String fileSize, String framerate, String height, String type, String width, String isDefault, String url, String dim, String description, String keywords, String thumbnail_url, String title) {
        this.channels = channels;
        this.bitrate = bitrate;
        this.duration = duration;
        this.fileSize = fileSize;
        this.framerate = framerate;
        this.height = height;
        this.type = type;
        this.width = width;
        this.isDefault = isDefault;
        this.url = url;
        this.dim = dim;
        this.description = description;
        this.keywords = keywords;
        this.thumbnail_url = thumbnail_url;
        this.title = title;
    }
}