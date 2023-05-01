package com.example.britalians;

import android.content.Context;
import android.os.AsyncTask;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class ParseXmlTask extends AsyncTask<Void, Void, RowList> {
    Context context;
    OnParseXmlCompleteListener listener;
    String xmlUrl;
    public ParseXmlTask(Context context, String xmlUrl, OnParseXmlCompleteListener listener) {
        this.context = context;
        this.xmlUrl = xmlUrl;
        this.listener = listener;
    }

    @Override
    protected RowList doInBackground(Void... voids) {
        RowList rowList = null;
        try {
            rowList = myXmlParser.parseXml(context, xmlUrl);
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }
        return rowList;
    }

    @Override
    protected void onPostExecute(RowList rowList) {
        listener.onParseXmlComplete(rowList);
    }

    public interface OnParseXmlCompleteListener {
        void onParseXmlComplete(RowList rowList);
    }
}
