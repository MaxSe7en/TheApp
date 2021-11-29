package com.se7en.theapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface SignInApi {

    @POST("iferchbeta/maxapi/signin.php")
    Call<SignModel> signInData(@Body SignModel signModel);
}
