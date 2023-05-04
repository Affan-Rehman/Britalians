package com.example.britalians;

import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
    TextView  globe, tag, people, exit;
    TextView home;
    LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        home = findViewById(R.id.home);
        globe = findViewById(R.id.globe);
        tag = findViewById(R.id.tag);
        people = findViewById(R.id.people);
        exit = findViewById(R.id.exit);
        linearLayout = findViewById(R.id.icon_layout);


        setupFocusListeners(linearLayout);
        Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
        View firstView = linearLayout.getChildAt(0);
        firstView.requestFocus();

        loadListFromXml(parsedRowList -> {
            MainFragment fragment1 = new MainFragment(getApplicationContext(), parsedRowList);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_one, fragment1).commit();
        });

        home.setOnClickListener(view -> {
            loadListFromXml(parsedRowList -> {
                MainFragment fragment1 = new MainFragment(getApplicationContext(), parsedRowList);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.frame_one, fragment1).commit();
            });
        });

        globe.setOnClickListener(view -> {
            GlobalFragment fragment1 = new GlobalFragment(getApplicationContext(),"global");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_one, fragment1).commit();
        });
        tag.setOnClickListener(view -> {
            GlobalFragment fragment1 = new GlobalFragment(getApplicationContext(),"brands");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_one, fragment1).commit();
        });
        people.setOnClickListener(view -> {
            GlobalFragment fragment1 = new GlobalFragment(getApplicationContext(),"humans");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_one, fragment1).commit();
        });
        exit.setOnClickListener(view -> {
            ExitDialogFragment exitDialogFragment = new ExitDialogFragment();
            exitDialogFragment.show(getSupportFragmentManager(), "ExitDialogFragment");
        });


    }
    private void loadListFromXml(OnRowListLoadCompleteListener callback) {
        String xmlUrl = "https://example.com/path/to/your/xml/file.xml";

        ParseXmlTask parseXmlTask = new ParseXmlTask(getApplicationContext(), xmlUrl, new ParseXmlTask.OnParseXmlCompleteListener() {
            @Override
            public void onParseXmlComplete(RowList parsedRowList) {
                callback.onRowListLoadComplete(parsedRowList);
            }
        });
        parseXmlTask.execute();
    }
    public interface OnRowListLoadCompleteListener {
        void onRowListLoadComplete(RowList rowList);
    }

    private void setupFocusListeners(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            view.setOnFocusChangeListener((v, hasFocus) -> {
                if (hasFocus) {
                    if (v == findViewById(R.id.home)) {
                        v.setBackground(null);
                        home.setText("HOME");
                    }
                    else if (v == findViewById(R.id.globe)){
                        v.setBackground(null);
                        globe.setText("STATES");
                    }
                    else if (v == findViewById(R.id.people)){
                        v.setBackground(null);
                        people.setText("HUMANS");
                    }
                    else if  (v == findViewById(R.id.tag)){
                        v.setBackground(null);
                        tag.setText("BRANDS");
                    }
                    else if  (v == findViewById(R.id.exit)){
                        v.setBackground(null);
                        exit.setText("EXIT");
                    }
                    }
                else {
                         if  (v == findViewById(R.id.home)) {
                            home.setText(null);
                            v.setBackground(getDrawable(R.drawable.ic_home));
                        }
                        else if  (v == findViewById(R.id.globe)){
                            globe.setText(null);
                            v.setBackground(getDrawable(R.drawable.ic_globe));
                        }
                         else if  (v == findViewById(R.id.people)){
                            people.setText(null);
                            v.setBackground(getDrawable(R.drawable.ic_people));
                        }
                         else if  (v == findViewById(R.id.tag)){
                            tag.setText(null);
                            v.setBackground(getDrawable(R.drawable.ic_tag));
                        }
                         else if  (v == findViewById(R.id.exit)){
                            exit.setText(null);
                            v.setBackground(getDrawable(R.drawable.ic_exit));
                        }

                    }

            });
        }
    }
}
