package com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.getdata;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public  interface APIInterfaceTrvPlan {
  @Headers({
          "Accept: application/json",
          "Content-Type: application/json"
  })

  @POST("getchoices")
  Call<GetcheckoutTrv> getResult(@Body JsonObject user9);
}

