package com.wasthdev.my_tic_tac_toe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    TextView poweredBy;
    ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        poweredBy = findViewById(R.id.powerdBy);
        poweredBy.setAlpha(0f);

        //animation to text
        poweredBy.animate()
                .translationY(poweredBy.getHeight())
                .alpha(1f)
                .setStartDelay(1000)
                .setDuration(1200);

        logo = findViewById(R.id.logo);
        logo.setAlpha(0f);

        //animate to image
        logo.animate()
                .translationY(poweredBy.getHeight())
                .alpha(1f)
                .setDuration(800);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreen.this,MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        },4000);
    }
}