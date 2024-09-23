package com.kauveryhospital.fieldforce.UseronlyAccess.planned.retrievevisit;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterfacevisit {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })





   @POST("getchoices")
    Call<Example> getResult(@Body JsonObject user9);


}

