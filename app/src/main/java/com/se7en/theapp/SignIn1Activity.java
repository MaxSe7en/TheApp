package com.se7en.theapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class SignIn1Activity extends AppCompatActivity implements View.OnClickListener {

    public static final String PREFS_NAME = "MyPrefs";
    private TextView registerHere;
    private Button signIn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        registerHere = findViewById(R.id.registerHere);
        signIn = findViewById(R.id.signInbtn);
        registerHere.setOnClickListener(this);
        signIn.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.registerHere:
                startActivity(new Intent(SignIn1Activity.this, Register.class));
                break;
            case R.id.signInbtn:
                Toast.makeText(this, "clicked", Toast.LENGTH_SHORT).show();
                sign();
                break;
        }
    }

    private void sign() {
        EditText emailText, passText;
        emailText = findViewById(R.id.emailAddress);
        passText = findViewById(R.id.editPassword);
        String email = emailText.getText().toString().trim();
        String password = passText.getText().toString().trim();

        if (email.isEmpty()){
            emailText.setError("email cannot be blank");
            return;
        }

        if (password.isEmpty()){
            passText.setError("password cannot be blank");
            return;
        }
        if (password.length() < 6){
            passText.setError("must be 6 or more");
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Please provide a valid email address");
            emailText.requestFocus();
        }
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        //to keep users logged in even when they close the app
        SharedPreferences sharedPreferences = getSharedPreferences(SignIn1Activity.PREFS_NAME, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", true);
        editor.apply();
        startActivity(new Intent(SignIn1Activity.this, MapsActivity.class));
        finish();
    }
}