package com.example.britalians;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

public class GlobalItemActivity extends FragmentActivity {
    RowItems rowItem;
    RowList rowList = null;
    Row rowNew = new Row();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_item);
        if (savedInstanceState == null) {
            rowItem = (RowItems) getIntent().getSerializableExtra("rowitem");

            ParseXmlTask parseXmlTask = new ParseXmlTask(getApplicationContext(), "https://btv-rss.s3.eu-west-2.amazonaws.com/uniquerss.rss", rowList -> {
                // Use a handler to post the FragmentTransaction to the main thread
                new Handler(Looper.getMainLooper()).post(() -> {
                    for (Row row : rowList.rows) {
                        for (Video video : row.items) {
                            for (VideoDetailsRow detailsRow : video.videoDetailsRows) {
                                for (RowItems items : detailsRow.row_items) {
                                    if (items.details_name.equals(rowItem.details_name)) {
                                        rowNew.items.add(video);
                                    }
                                }
                            }
                        }
                    }
                    GlobalItemFragment fragment = new GlobalItemFragment(rowNew);
                    // Use a FragmentTransaction to add the fragment to the activity
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.globalItemActivityFrame, fragment);
                    transaction.commitAllowingStateLoss(); // Use commitAllowingStateLoss() instead of commit()
                });
            });
            parseXmlTask.execute();
        }
    }
}
