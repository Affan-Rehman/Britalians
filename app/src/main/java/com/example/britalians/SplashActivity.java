package com.example.britalians;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import androidx.fragment.app.FragmentActivity;

public class SplashActivity extends FragmentActivity {
    private static final long DELAY_TIME = 4000; // Delay time in milliseconds

    // Declare the Handler and Runnable
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialize the Handler and Runnable
        handler = new Handler();
        runnable = () -> startMainActivity();

        // Delay the transition to MainActivity
        handler.postDelayed(runnable, DELAY_TIME);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_ENTER || keyCode == KeyEvent.KEYCODE_NUMPAD_ENTER) {
            // Cancel the delay
            handler.removeCallbacks(runnable);

            // Start MainActivity
            startMainActivity();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
