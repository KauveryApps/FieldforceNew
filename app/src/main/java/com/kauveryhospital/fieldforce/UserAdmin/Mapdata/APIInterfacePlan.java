package com.kauveryhospital.fieldforce.UserAdmin.Mapdata;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public  interface APIInterfacePlan {
  @Headers({
          "Accept: application/json",
          "Content-Type: application/json"
  })

  @POST("getchoices")
  Call<Getcheckout> getResult(@Body JsonObject user9);
}

