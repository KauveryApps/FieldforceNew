package com.kauveryhospital.fieldforce.Loginscreen;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

interface APIInterface {
  @Headers({
          "Accept: application/json",
          "Content-Type: application/json"

  })

  @POST("Login")
 Call<Example>getResult(@Body JsonObject user9);
}