package com.example.britalians;

import android.content.Context;
import android.util.Xml;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.URL;

public class StateXmlParser {


    public static VideoDetailsRow parseStateXml(Context context,int xml) throws XmlPullParserException, IOException {
        XmlPullParser parser = context.getResources().getXml(xml);

//         URL url = new URL(xmlUrl);
//        XmlPullParser parser = Xml.newPullParser();
//        parser.setInput(url.openStream(), null);
        VideoDetailsRow videoDetailsRow = new VideoDetailsRow();
        int eventType = parser.getEventType();
        RowItems currentRowItem = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String tagName = parser.getName();
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if ("row_items".equals(tagName)) {
                        currentRowItem = new RowItems();
                    } else if (currentRowItem != null) {
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

        return videoDetailsRow;
    }
}