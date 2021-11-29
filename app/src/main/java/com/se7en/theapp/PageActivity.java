package com.se7en.theapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class PageActivity extends AppCompatActivity implements View.OnClickListener {

    TextView signIn, register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getLifecycle().addObserver(new MainActivityObserver());
        signIn = findViewById(R.id.homeSignIn);
        register = findViewById(R.id.homeRegister);

        signIn.setOnClickListener(PageActivity.this);
        register.setOnClickListener(PageActivity.this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.homeSignIn:
                Intent intent = new Intent(PageActivity.this, SignIn1Activity.class);
                startActivity(intent);
                break;
            case R.id.homeRegister:
                Intent i = new Intent(PageActivity.this, Register.class);
                startActivity(i);

        }

    }
}