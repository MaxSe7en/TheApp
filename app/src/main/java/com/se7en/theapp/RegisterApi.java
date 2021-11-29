package com.se7en.theapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterApi {
    @POST("iferchbeta/maxapi/register.php")
    Call<RegisterModel> registerData(@Body RegisterModel registerModel);
}
