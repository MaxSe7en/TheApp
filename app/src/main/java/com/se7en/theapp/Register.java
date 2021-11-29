package com.se7en.theapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Register extends AppCompatActivity {

    private EditText emailText, nameText, pass1Text, pass2Text;
    private Button regBtn;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        nameText = findViewById(R.id.editName);
        emailText = findViewById(R.id.editEmail);
        pass1Text = findViewById(R.id.editPass1);
        pass2Text = findViewById(R.id.editPass2);


        regBtn = findViewById(R.id.registerBtn);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUSer();
            }
        });

    }

    private void registerUSer() {

        String fullName = nameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String pass1 = pass1Text.getText().toString().trim();
        String pass2 = pass2Text.getText().toString().trim();

        if (fullName.isEmpty()){
            nameText.setError("Enter full name");
            nameText.requestFocus();
            return;
        }
        if (email.isEmpty()){
            emailText.setError("Email cannot be blank");
            emailText.requestFocus();
            return;
        }if (pass1.isEmpty()){
            pass1Text.setError("Password cannot be blank");
            pass1Text.requestFocus();
            return;
        }if (pass2.isEmpty()){
            pass2Text.setError("Password cannot be blank");
            pass2Text.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Please provide a valid email address");
            emailText.requestFocus();
            return;
        }
        if (!pass1.equals(pass2)){
            pass2Text.setError("Passwords must match");
            pass2Text.requestFocus();
            return;
        }

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        registerUserData();


    }

    private void registerUserData() {
        String fullName = nameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String pass1 = pass1Text.getText().toString().trim();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.iferch.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RegisterApi registerApi = retrofit.create(RegisterApi.class);

        RegisterModel registerModel = new RegisterModel(fullName, email, pass1);

        Call<RegisterModel> call = registerApi.registerData(registerModel);

        call.enqueue(new Callback<RegisterModel>() {
            @Override
            public void onResponse(Call<RegisterModel> call, Response<RegisterModel> response) {
                if(response.code() == 409){
                    Toast.makeText(Register.this, "Email already registered", Toast.LENGTH_SHORT).show();
                }

                if (response.isSuccessful()){
                    Toast.makeText(Register.this, "Sign in successful", Toast.LENGTH_LONG).show();
                    Log.i("Tage", ""+response);
                    progressBar.setVisibility(View.GONE);
                    startActivity(new Intent(Register.this, SignIn1Activity.class));
                    finish();
                }else {
                    Toast.makeText(Register.this, "Unsuccessful "+ response , Toast.LENGTH_LONG).show();
                    Log.i("Tag", ""+response);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<RegisterModel> call, Throwable t) {
                Toast.makeText(Register.this, "Check Internet Connection "+ t , Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }


}