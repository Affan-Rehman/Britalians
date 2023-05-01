package com.example.britalians;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class GlobalFragment extends Fragment  {
    Context context;
    VideoDetailsRow items;
    ImageView logoImageView;
    ImageView topRightImageView;
    TextView titleTextView;
    RecyclerView recyclerView;
    StateAdapter rowItemAdapter;
    String type;
    public GlobalFragment() {
        // Required empty public constructor
    }
    public GlobalFragment(Context context,String type) {
        this.context = context;
        this.type = type;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_global, container, false);
        try {
            if(type.equals("global"))
                items = StateXmlParser.parseStateXml(context,R.xml.states);
            else if(type.equals("brands"))
                items = StateXmlParser.parseStateXml(context,R.xml.brands);
            else if(type.equals("humans"))
                items = StateXmlParser.parseStateXml(context,R.xml.humans);
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        logoImageView = view.findViewById(R.id.logo_image_view);
        topRightImageView = view.findViewById(R.id.top_right_image_view);
        titleTextView = view.findViewById(R.id.title_text_view);
        recyclerView = view.findViewById(R.id.recycler_view);

        rowItemAdapter = new StateAdapter(items.row_items,this);

        recyclerView.setAdapter(rowItemAdapter);
        return view;
    }
    public void updateFocusedRowItem(RowItems rowItem) {
        Glide.with(this)
                .load(rowItem.details_thumbnail169)
                .into(topRightImageView);
        titleTextView.setText(rowItem.details_name);
        Glide.with(this)
                .load(rowItem.details_logo)
                .into(logoImageView);
    }
}