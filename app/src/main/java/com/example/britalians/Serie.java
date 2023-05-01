package com.example.britalians;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Serie implements Serializable {
        String title;
        String content;
        String id;
        String thumbnail_image;
        String thumbnail_image169;
        String serie_page_image;
        String serie_trailer_url;
        String series_category_to_show;
        List<Season> seasons;
    public Serie() {
        this.title = "";
        this.content = "";
        this.id = "";
        this.thumbnail_image = "";
        this.thumbnail_image169 = "";
        this.serie_page_image = "";
        this.serie_trailer_url = "";
        this.series_category_to_show = "";
    }
    public Serie(String title, String content, String id, String thumbnail_image, String thumbnail_image169, String serie_page_image, String serie_trailer_url, String series_category_to_show, List<Season> seasons) {
        this.title = title;
        this.content = content;
        this.id = id;
        this.thumbnail_image = thumbnail_image;
        this.thumbnail_image169 = thumbnail_image169;
        this.serie_page_image = serie_page_image;
        this.serie_trailer_url = serie_trailer_url;
        this.series_category_to_show = series_category_to_show;
        this.seasons = seasons;
    }
}

class Season implements Serializable{
    String title;
    String id;
    List<Video> videos;

    public Season(String id, String title, List<Video> videos) {
        this.id = id;
        this.title = title;
        this.videos = videos;
    }
    public Season(){
        title = "";
        id = "";
        videos = new ArrayList<>();
    }
}

class Video implements Serializable {
    String id;
    String rating;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getThumbnail169() {
        return thumbnail169;
    }

    public void setThumbnail169(String thumbnail169) {
        this.thumbnail169 = thumbnail169;
    }

    public String getReleaseyear() {
        return releaseyear;
    }

    public void setReleaseyear(String releaseyear) {
        this.releaseyear = releaseyear;
    }

    public String getSerieslogo() {
        return serieslogo;
    }

    public void setSerieslogo(String serieslogo) {
        this.serieslogo = serieslogo;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public List<VideoDetailsRow> getVideoDetailsRows() {
        return videoDetailsRows;
    }

    public void setVideoDetailsRows(List<VideoDetailsRow> videoDetailsRows) {
        this.videoDetailsRows = videoDetailsRows;
    }

    public Media getMedia() {
        return media;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    String title;
    String content;
    String link;
    String description;
    String thumbnail169;
    String releaseyear;
    String serieslogo;
    String duration;
    String pubDate;
    List<VideoDetailsRow> videoDetailsRows;
    Media media;

    public Video() {
        this.id = "";
        this.title = "";
        this.content = "";
        this.link = "";
        this.description = "";
        this.thumbnail169 = "";
        this.releaseyear = "";
        this.serieslogo = "";
        this.duration = "";
        this.videoDetailsRows = new ArrayList<>();
        pubDate = "";
        media = null;
        rating = "";
    }
    public Video(String id, String content, String link, String title, String description, String thumbnail169, String releaseYear, String seriesLogo, String duration, List<VideoDetailsRow> videoDetailsRows,String pubDate, Media media) {
        this.id = id;
        this.content = content;
        this.link = link;
        this.title = title;
        this.description = description;
        this.thumbnail169 = thumbnail169;
        this.releaseyear = releaseYear;
        this.serieslogo = seriesLogo;
        this.duration = duration;
        this.videoDetailsRows = videoDetailsRows;
        this.pubDate = pubDate;
        this.media = media;
    }


}

class RowItems implements Serializable{
    String details_name;
    String details_thumbnail;
    String details_thumbnail169;
    String details_logo;
    String details_description;

    public RowItems() {
        this.details_name = "";
        this.details_thumbnail = "";
        this.details_thumbnail169 = "";
        this.details_logo = "";
        this.details_description = "";
    }

    public RowItems(String details_name, String details_thumbnail, String details_thumbnail169, String details_logo, String details_description) {
        this.details_name = details_name;
        this.details_thumbnail = details_thumbnail;
        this.details_thumbnail169 = details_thumbnail169;
        this.details_logo = details_logo;
        this.details_description = details_description;
    }

}


class VideoDetailsRow implements Serializable{
    String title;
    List <RowItems> row_items;
    public VideoDetailsRow() {
        this.title = "";
        this.row_items = new ArrayList<>();
    }

    public VideoDetailsRow(String title, List<RowItems> row_items) {
        this.title = title;
        this.row_items = row_items;
    }
}

class Row {
    String title;
    List<Video> items;

    public Row(){
        title = "";
        items = new ArrayList<>();
    }
    public Row(String title, List<Video> items) {
        this.title = title;
        this.items = items;
    }
}

class RowList {
    List<Row> rows;
    SerieList serieList;
     public RowList(List<Row> rows,SerieList serieList) {
        this.serieList  = serieList;
         this.rows = rows;
    }
    public RowList(){
            rows = new ArrayList<>();
    }
}

class SerieList implements Serializable{
    List<Serie> series;
    SerieList(List<Serie> series){
        this.series = series;
    }
    SerieList(){
        series = new ArrayList<>();
    }
}

