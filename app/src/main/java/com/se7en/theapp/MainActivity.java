package com.se7en.theapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 5000;
    //Variables
    private Animation topAnimation, bottomAnimation;
    private ImageView image;
    private TextView main, sub_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for making the app fullscreen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        //finding animations resources
        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        main = findViewById(R.id.se7en);
        sub_main = findViewById(R.id.subSe7en);
        image = findViewById(R.id.worldMap);

        main.setAnimation(bottomAnimation);
        sub_main.setAnimation(bottomAnimation);
        image.setAnimation(topAnimation);

        new Handler().postDelayed(() -> {
            //using Shared Preferences to keep the user signed in even after closing the app
            SharedPreferences sharedPreferences = getSharedPreferences(SignIn1Activity.PREFS_NAME, 0);
            boolean isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false);

            if (isLoggedIn){
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
                finish();
            }else{
                startActivity(new Intent(MainActivity.this, PageActivity.class));
                finish();
            }
        }, SPLASH_SCREEN);

    }

}
