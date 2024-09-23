package com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdatas;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterface {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })





   @POST("getchoices")
    Call<Example> getResult(@Body JsonObject user9);

  //  @POST("savedata")
  //  Call<Exam>getResult(@Body JsonObject1 post);
}

