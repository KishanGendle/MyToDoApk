package com.example.mytodoapk;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;

public class SplashActivity extends AppCompatActivity {

    private ProgressBar loadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        loadingBar = findViewById(R.id.loadingBar);
        simulateLoading();
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        },4000);
    }
    private void simulateLoading() {
        loadingBar.setVisibility(View.VISIBLE);

        // Simulating a delay of 4 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadingBar.setVisibility(View.GONE);
            }
        }, 4000);
    }
}