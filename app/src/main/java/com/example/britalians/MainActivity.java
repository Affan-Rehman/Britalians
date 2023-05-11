package com.example.britalians;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
    TextView globe, tag, people, exit;
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
            GlobalFragment fragment1 = new GlobalFragment(getApplicationContext(), "global");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_one, fragment1).commit();
        });
        tag.setOnClickListener(view -> {
            GlobalFragment fragment1 = new GlobalFragment(getApplicationContext(), "brands");
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_one, fragment1).commit();
        });
        people.setOnClickListener(view -> {
            GlobalFragment fragment1 = new GlobalFragment(getApplicationContext(), "humans");
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

    private void updateIconState(View view, boolean hasFocus) {
        int textColor = hasFocus ? Color.BLUE : Color.WHITE;

        if (view == findViewById(R.id.home)) {
            home.setText("HOME");
            globe.setBackground(null);
            people.setBackground(null);
            tag.setBackground(null);
            exit.setBackground(null);
        } else if (view == findViewById(R.id.globe)) {
            globe.setText("STATES");
            home.setBackground(null);
            people.setBackground(null);
            tag.setBackground(null);
            exit.setBackground(null);
        } else if (view == findViewById(R.id.people)) {
            people.setText("HUMANS");
            globe.setBackground(null);
            home.setBackground(null);
            tag.setBackground(null);
            exit.setBackground(null);
        } else if (view == findViewById(R.id.tag)) {
            tag.setText("BRANDS");
            globe.setBackground(null);
            home.setBackground(null);
            people.setBackground(null);
            exit.setBackground(null);
        } else if (view == findViewById(R.id.exit)) {
            exit.setText("EXIT");
            globe.setBackground(null);
            home.setBackground(null);
            tag.setBackground(null);
            people.setBackground(null);
        }

        if (view instanceof TextView) {
            ((TextView) view).setTextColor(textColor);
            updateIconSize(view, hasFocus);
        }
    }

    private void setupFocusListeners(LinearLayout linearLayout) {
        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view = linearLayout.getChildAt(i);
            view.setOnFocusChangeListener((v, hasFocus) -> {
                boolean anyIconHasFocus = false;
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    View otherView = linearLayout.getChildAt(j);
                    updateIconState(otherView, otherView.hasFocus());
                    if (otherView.hasFocus()) {
                        anyIconHasFocus = true;
                    }
                }

                // Calculate the new width for the LinearLayout
                int newWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, hasFocus ? 100 : 50, getResources().getDisplayMetrics());

                // Animate the width of the LinearLayout
                ValueAnimator animator = ValueAnimator.ofInt(linearLayout.getLayoutParams().width, newWidth);
                animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator valueAnimator) {
                        ViewGroup.LayoutParams layoutParams = linearLayout.getLayoutParams();
                        layoutParams.width = (int) valueAnimator.getAnimatedValue();
                        linearLayout.setLayoutParams(layoutParams);
                    }
                });
                animator.setDuration(300); // duration of the animation
                animator.start();

                // Rest of your code...
                if (!hasFocus) {
                    for (int j = 0; j < linearLayout.getChildCount(); j++) {
                        TextView view1 = (TextView) linearLayout.getChildAt(j);
                        int iconSize = 30;
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(iconSize), dpToPx(iconSize));
                        switch (view1.getId()) {
                            case R.id.home:
                                view1.setText(null);
                                view1.setBackground(getDrawable(R.drawable.ic_home));
                                view1.setLayoutParams(params);
                                break;
                            case R.id.globe:
                                view1.setText(null);
                                view1.setBackground(getDrawable(R.drawable.ic_globe));
                                view1.setLayoutParams(params);
                                break;
                            case R.id.tag:
                                view1.setText(null);
                                view1.setBackground(getDrawable(R.drawable.ic_tag));
                                view1.setLayoutParams(params);
                                break;
                            case R.id.people:
                                view1.setText(null);
                                view1.setBackground(getDrawable(R.drawable.ic_people));
                                view1.setLayoutParams(params);
                                break;
                            case R.id.exit:
                                view1.setText(null);
                                view1.setBackground(getDrawable(R.drawable.ic_exit));
                                view1.setLayoutParams(params);
                                break;
                        }
                    }
                }

                // Maintain the margin between icons
                for (int j = 0; j < linearLayout.getChildCount(); j++) {
                    TextView view1 = (TextView) linearLayout.getChildAt(j);
                    LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) view1.getLayoutParams();
                    int iconSize = anyIconHasFocus ? 60 : 30;
                    int marginBottom = view1.hasFocus() ? 0 : 10;
                    params.width = dpToPx(iconSize);
                    params.height = dpToPx(iconSize);
                    params.setMargins(0, dpToPx(marginBottom), 0, dpToPx(marginBottom));
                    view1.setLayoutParams(params);
                }
            });
        }
    }



    private int dpToPx(int dp) {
        float density = getResources().getDisplayMetrics().density;
        return Math.round(dp * density);
    }

    private void updateIconSize(View view, boolean hasFocus) {
        int iconSize = hasFocus ? 60 : 30;
        int marginBottom = hasFocus ? 0 : 10;

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View view1 = linearLayout.getChildAt(i);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(dpToPx(iconSize), dpToPx(iconSize));
            params.setMargins(0, 0, 0, dpToPx(marginBottom));
            view1.setLayoutParams(params);
        }
    }
    private void animateLayoutWidth(final View view, int width) {
        view.animate()
                .translationX(width)
                .setDuration(150) // Set your desired duration here
                .setInterpolator(new AccelerateDecelerateInterpolator()) // You can use any interpolator
                .start();
    }

}
