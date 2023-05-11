package com.example.britalians;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class myXmlParser {

    public static RowList parseXml(Context context,String xmlUrl) throws XmlPullParserException, IOException {
        List<Row> rows = new ArrayList<>();
        SerieList serieList = new SerieList();
        int count = 0;
        int counter = 0;
//        URL url = new URL(xmlUrl);
//        XmlPullParser parser = Xml.newPullParser();
//        parser.setInput(url.openStream(), null);
        Resources res = context.getResources();
        XmlResourceParser parser = res.getXml(R.xml.sample);

        int eventType = parser.getEventType();
        Row currentRow = null;
        Serie currentSerie = null;
        Season currentSeason = null;
        boolean checkTitle = true;
        Video currentVideo = null;
        boolean seriesCheck = true;
        VideoDetailsRow currentVideoDetailsRow = null;
        RowItems currentRowItem = null;
        boolean checkOne = false;

        boolean check = true;
        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if(checkTitle) {
                        if ("row".equals(tagName)) {
                            currentRow = new Row(null, new ArrayList<>());
                        }
                        else if (currentRow != null) {

                            if (currentVideo == null && "title".equals(tagName)) {
                                currentRow.title = parser.nextText();
//                                Log.d(TAG, currentRow.title +" "+ counter) ;
                                seriesCheck = Objects.equals(currentRow.title, "Series");
                                checkTitle = false;

                                if (seriesCheck) {
                                    currentRow = null;
                                    count = counter;
                                }
                                counter++;
                            }
                        }
                    }

                       else  if(!seriesCheck) {
                            if (currentVideo == null && "item".equals(tagName)) {
                                currentVideo = new Video();
                            }
                            else if (currentVideo != null && currentVideoDetailsRow == null) {            //dealing with video here
                                if (tagName.equals("id")) {
                                    currentVideo.id = parser.nextText();
                                } else if (tagName.equals("title")) {
                                    currentVideo.title = parser.nextText();
                                } else if (check && tagName.equals("content")) {
                                    currentVideo.content = parser.nextText();
                                    check = false;
                                } else if (tagName.equals("rating")) {
                                    currentVideo.rating = parser.nextText();
                                }else if (tagName.equals("link")) {
                                    currentVideo.link = parser.nextText();
                                } else if (tagName.equals("description")) {
                                    currentVideo.description = parser.nextText();
                                } else if (tagName.equals("thumbnail169")) {
                                    currentVideo.thumbnail169 = parser.nextText();
                                } else if (tagName.equals("releaseyear")) {
                                    currentVideo.releaseyear = parser.nextText();
                                } else if (tagName.equals("pubDate")) {
                                    currentVideo.pubDate = parser.nextText();
                                } else if (tagName.equals("serieslogo")) {
                                    currentVideo.serieslogo = parser.nextText();
                                } else if (tagName.equals("duration")) {
                                    currentVideo.duration = parser.nextText();
                                } else if (tagName.equals("video_details_row")) {
                                    currentVideoDetailsRow = new VideoDetailsRow();
                                } else if (tagName.startsWith("guid")) {
                                    //used for skipping guid
                                } else if (!check && tagName.equals("content")) {
                                    Media media = new Media();
                                    media.channels = parser.getAttributeValue(null, "channels");
                                    media.bitrate = parser.getAttributeValue(null, "bitrate");
                                    media.duration = parser.getAttributeValue(null, "duration");
                                    media.fileSize = parser.getAttributeValue(null, "fileSize");
                                    media.framerate = parser.getAttributeValue(null, "framerate");
                                    media.height = parser.getAttributeValue(null, "height");
                                    media.type = parser.getAttributeValue(null, "type");
                                    media.width = parser.getAttributeValue(null, "width");
                                    media.isDefault = parser.getAttributeValue(null, "isDefault");
                                    media.url = parser.getAttributeValue(null, "url");
                                    while (parser.next() != XmlPullParser.END_TAG) {
                                        if (parser.getEventType() != XmlPullParser.START_TAG) {
                                            continue;
                                        }
                                        String name = parser.getName();
                                        switch (name) {
                                            case "dim":
                                                media.dim = readText(parser);
                                                break;
                                            case "description":
                                                media.description = readText(parser);
                                                break;
                                            case "keywords":
                                                media.keywords = readText(parser);
                                                break;
                                            case "thumbnail":
                                                media.thumbnail_url = parser.getAttributeValue(null, "url");
                                                parser.nextTag();
                                                break;
                                            case "title":
                                                media.title = readText(parser);
                                                break;
                                        }
                                    }
                                    currentVideo.media = media;
                                    check = false;
                                }
                            }
                            else if (currentVideoDetailsRow != null && currentRowItem == null) {
                                if ("title".equals(tagName)) {
                                    currentVideoDetailsRow.title = parser.nextText();
                                }
                                else if ("row_items".equals(tagName)) {
                                    currentRowItem = new RowItems();
                                }
                            }
                            else if (currentRowItem != null) {
                                switch (tagName) {
                                    case "details_name":
                                        currentRowItem.details_name = parser.nextText();
                                        break;
                                    case "details_thumbnail":
                                        currentRowItem.details_thumbnail = parser.nextText();
                                        break;
                                    case "details_thumbnail169":
                                        currentRowItem.details_thumbnail169 = parser.nextText();
                                        break;
                                    case "details_logo":
                                        currentRowItem.details_logo = parser.nextText();
                                        break;
                                    case "details_description":
                                        currentRowItem.details_description = parser.nextText();
                                        break;
                                }
                            }
                        }
                       else if(!checkTitle) {

                             if (currentSerie==null && "item".equals(tagName)) {
                                currentSerie = new Serie();

                            }
                             else if (currentSerie != null && currentSerie.seasons==null) {
                                if ("title".equals(tagName)) {
                                    currentSerie.title = parser.nextText();
                                } else if ("content".equals(tagName)) {
                                    currentSerie.content = parser.nextText();
                                } else if ("id".equals(tagName)) {
                                    currentSerie.id = parser.nextText();
                                } else if ("thumbnail_image".equals(tagName)) {
                                    currentSerie.thumbnail_image = parser.nextText();
                                } else if ("thumbnail_image169".equals(tagName)) {
                                    currentSerie.thumbnail_image169 = parser.nextText();
                                } else if ("serie_page_image".equals(tagName)) {
                                    currentSerie.serie_page_image = parser.nextText();
                                } else if ("serie_trailer_url".equals(tagName)) {
                                    currentSerie.serie_trailer_url = parser.nextText();
                                } else if ("series_category_to_show".equals(tagName)) {
                                    currentSerie.series_category_to_show = parser.nextText();

                                }else if("series".equals(tagName)){
                                    currentSerie.seasons = new ArrayList<>();

                                }
                            }
                             else if (currentSerie.seasons != null && currentSeason==null) {
                                 if("season".equals(tagName)) {
                                     currentSeason = new Season();
                                     currentSeason.title = parser.getAttributeValue(null, "title");
                                 }
                            }
                             else if (currentSeason != null && currentVideo==null) {
                                if ("id".equals(tagName)) {
                                    currentSeason.id = parser.nextText();
                                } else if ("item".equals(tagName)) {
                                    currentVideo = new Video();
                                }
                            }
                             else if (currentVideo != null && currentVideoDetailsRow == null) {            //dealing with video here
                                 if (tagName.equals("id")) {
                                     currentVideo.id = parser.nextText();
                                 } else if (tagName.equals("title")) {
                                     currentVideo.title = parser.nextText();
                                 } else if (check && tagName.equals("content")) {
                                     currentVideo.content = parser.nextText();
                                     check = false;
                                 }else if (tagName.equals("rating")) {
                                     currentVideo.rating = parser.nextText();
                                 } else if (tagName.equals("link")) {
                                     currentVideo.link = parser.nextText();
                                 } else if (tagName.equals("description")) {
                                     currentVideo.description = parser.nextText();
                                 } else if (tagName.equals("thumbnail169")) {
                                     currentVideo.thumbnail169 = parser.nextText();
                                 } else if (tagName.equals("releaseyear")) {
                                     currentVideo.releaseyear = parser.nextText();
                                 } else if (tagName.equals("pubDate")) {
                                     currentVideo.pubDate = parser.nextText();
                                 } else if (tagName.equals("serieslogo")) {
                                     currentVideo.serieslogo = parser.nextText();
                                 } else if (tagName.equals("duration")) {
                                     currentVideo.duration = parser.nextText();
                                 } else if (tagName.equals("video_details_row")) {
                                     currentVideoDetailsRow = new VideoDetailsRow();
                                 } else if (tagName.startsWith("guid")) {
                                     //used for skipping guid
                                 } else if (!check && tagName.equals("content")) {
                                     Media media = new Media();
                                     media.channels = parser.getAttributeValue(null, "channels");
                                     media.bitrate = parser.getAttributeValue(null, "bitrate");
                                     media.duration = parser.getAttributeValue(null, "duration");
                                     media.fileSize = parser.getAttributeValue(null, "fileSize");
                                     media.framerate = parser.getAttributeValue(null, "framerate");
                                     media.height = parser.getAttributeValue(null, "height");
                                     media.type = parser.getAttributeValue(null, "type");
                                     media.width = parser.getAttributeValue(null, "width");
                                     media.isDefault = parser.getAttributeValue(null, "isDefault");
                                     media.url = parser.getAttributeValue(null, "url");
                                     while (parser.next() != XmlPullParser.END_TAG) {
                                         if (parser.getEventType() != XmlPullParser.START_TAG) {
                                             continue;
                                         }
                                         String name = parser.getName();
                                         switch (name) {
                                             case "dim":
                                                 media.dim = readText(parser);
                                                 break;
                                             case "description":
                                                 media.description = readText(parser);
                                                 break;
                                             case "keywords":
                                                 media.keywords = readText(parser);
                                                 break;
                                             case "thumbnail":
                                                 media.thumbnail_url = parser.getAttributeValue(null, "url");
                                                 parser.nextTag();
                                                 break;
                                             case "title":
                                                 media.title = readText(parser);
                                                 break;
                                         }
                                     }
                                     currentVideo.media = media;
                                     check = false;
                                 }
                             }
                             else if (currentVideoDetailsRow != null && currentRowItem == null) {
                                 if ("title".equals(tagName)) {
                                     currentVideoDetailsRow.title = parser.nextText();
                                 } else if ("row_items".equals(tagName)) {
                                     currentRowItem = new RowItems();
                                 }
                             }
                             else if (currentRowItem != null) {
                                 switch (tagName) {

                                     case "details_name":
                                         currentRowItem.details_name = parser.nextText();
                                         break;
                                     case "details_thumbnail":
                                         currentRowItem.details_thumbnail = parser.nextText();
                                         break;
                                     case "details_thumbnail169":
                                         currentRowItem.details_thumbnail169 = parser.nextText();
                                         break;
                                     case "details_logo":
                                         currentRowItem.details_logo = parser.nextText();
                                         break;
                                     case "details_description":
                                         currentRowItem.details_description = parser.nextText();
                                         break;
                                 }
                             }

                        }


                    break;

                case XmlPullParser.END_TAG:
                    if(!checkTitle) {
                        if (!seriesCheck) {
                            if ("row_items".equals(tagName) && currentRowItem != null) {
                                currentVideoDetailsRow.row_items.add(currentRowItem);
                                currentRowItem = null;
                            } else if ("video_details_row".equals(tagName) && currentVideoDetailsRow != null) {
                                currentVideo.videoDetailsRows.add(currentVideoDetailsRow);
                                currentVideoDetailsRow = null;
                            } else if ("item".equals(tagName) && currentVideo != null) {
                                currentRow.items.add(currentVideo);
                                currentVideo = null;
                            } else if ("row".equals(tagName) && currentRow != null) {
                                rows.add(currentRow);
                                currentRow = null;
                                checkTitle = true;
                            }
                        }
                        else {
                            if ("row_items".equals(tagName) && currentRowItem != null) {
                                currentVideoDetailsRow.row_items.add(currentRowItem);
                                currentRowItem = null;
                            } else if ("video_details_row".equals(tagName) && currentVideoDetailsRow != null) {
                                currentVideo.videoDetailsRows.add(currentVideoDetailsRow);
                                currentVideoDetailsRow = null;
                            } else if ("item".equals(tagName) && currentVideo != null) {
                                currentSeason.videos.add(currentVideo);
                                currentVideo = null;
                            } else if ("season".equals(tagName) && currentSeason != null) {
                                currentSerie.seasons.add(currentSeason);
                                currentSeason = null;
                            }else if("series".equals(tagName)){
                                serieList.series.add(currentSerie);
                                currentSerie =null;
                                currentRow = new Row(null,new ArrayList<>());
                            }
                            else if("row".equals(tagName)){
                                seriesCheck = false;
                                checkTitle = true;
                            }
                        }
                    }

            }
            eventType = parser.next();
        }
        return new RowList(rows,serieList,count);
    }


    private static String readText(XmlPullParser parser) throws IOException, XmlPullParserException {
        String result = "";
        if (parser.next() == XmlPullParser.TEXT) {
            result = parser.getText();
            parser.nextTag();
        }
        return result;
    }

}