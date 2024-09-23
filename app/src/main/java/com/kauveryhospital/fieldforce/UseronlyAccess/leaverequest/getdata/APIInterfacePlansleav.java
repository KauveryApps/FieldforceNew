package com.kauveryhospital.fieldforce.UseronlyAccess.leaverequest.getdata;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public  interface APIInterfacePlansleav {
  @Headers({
          "Accept: application/json",
          "Content-Type: application/json"
  })

  @POST("getchoices")
  Call<Getcheckoutleave> getResult(@Body JsonObject user9);
}

