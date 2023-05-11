package com.example.britalians;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sun.istack.Nullable;

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
            switch (type) {
                case "global":
                    items = StateXmlParser.parseStateXml(context, R.xml.states);
                    break;
                case "brands":
                    items = StateXmlParser.parseStateXml(context, R.xml.brands);
                    break;
                case "humans":
                    items = StateXmlParser.parseStateXml(context, R.xml.humans);
                    break;
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        logoImageView = view.findViewById(R.id.logo_image_view);
        topRightImageView = view.findViewById(R.id.top_right_image_view);
        titleTextView = view.findViewById(R.id.title_text_view);
        recyclerView = view.findViewById(R.id.recycler_view);

        rowItemAdapter = new StateAdapter(items.row_items,this);

        recyclerView.setAdapter(rowItemAdapter);
        recyclerView.requestFocus();
        recyclerView.scrollToPosition(0);
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

        Glide.with(this)
                .asBitmap()
                .load(rowItem.details_logo)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        int imageWidth = resource.getWidth();
                        int imageHeight = resource.getHeight();

                        // Get the ImageView LayoutParams
                        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) logoImageView.getLayoutParams();

                        // Set a fixed height in dp
                        int fixedHeightDp = 60;
                        params.height = dpToPx(fixedHeightDp);

                        // Adjust the width to maintain the image's aspect ratio
                        params.width = (int) (params.height * ((float) imageWidth / imageHeight));

                        // Apply the updated LayoutParams to the ImageView
                        logoImageView.setLayoutParams(params);

                        // Now that we've adjusted the size of the ImageView, load the image into it
                        Glide.with(getContext()).load(rowItem.details_logo).into(logoImageView);
                    }
                });
    }    int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

}