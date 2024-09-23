package com.kauveryhospital.fieldforce.OHCOnly.getdata;

import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.getdata.Getcheckout;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public  interface APIInterfaceOhc {
  @Headers({
          "Accept: application/json",
          "Content-Type: application/json"
  })

  @POST("getchoices")
  Call<GetcheckoutOhc> getResult(@Body JsonObject user9);
}

