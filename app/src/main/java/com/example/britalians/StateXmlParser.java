package com.example.britalians;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class StateXmlParser {
    private static final int BUFFER_SIZE = 4096; // Adjust the buffer size as needed

    public static void parseStateXml(Context context, String xmlUrl, OnXmlParseCompleteListener listener) {
        new AsyncTask<String, Void, VideoDetailsRow>() {
            @Override
            protected VideoDetailsRow doInBackground(String... params) {
                VideoDetailsRow videoDetailsRow = new VideoDetailsRow();
                HttpURLConnection connection = null;
                InputStream inputStream = null;

                try {
                    URL url = new URL(xmlUrl);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setConnectTimeout(5000); // Adjust the connection timeout if needed
                    connection.setReadTimeout(5000); // Adjust the read timeout if needed

                    inputStream = new BufferedInputStream(connection.getInputStream(), BUFFER_SIZE);

                    XmlPullParser parser = Xml.newPullParser();
                    parser.setInput(inputStream, null);

                    boolean inRowItems = false;
                    RowItems currentRowItem = null;
                    int eventType = parser.getEventType();

                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        String tagName = parser.getName();
                        switch (eventType) {
                            case XmlPullParser.START_TAG:
                                if ("row_items".equals(tagName)) {
                                    inRowItems = true;
                                    currentRowItem = new RowItems();
                                } else if (inRowItems && currentRowItem != null) {
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
                                break;

                            case XmlPullParser.END_TAG:
                                if ("row_items".equals(tagName) && currentRowItem != null) {
                                    videoDetailsRow.row_items.add(currentRowItem);
                                    currentRowItem = null;
                                }
                                break;
                        }

                        eventType = parser.next();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }

                return videoDetailsRow;
            }

            @Override
            protected void onPostExecute(VideoDetailsRow videoDetailsRow) {
                if (listener != null) {
                    listener.onXmlParseComplete(videoDetailsRow);
                }
            }
        }.execute(xmlUrl);
    }

    public interface OnXmlParseCompleteListener {
        void onXmlParseComplete(VideoDetailsRow videoDetailsRow);
    }
}
