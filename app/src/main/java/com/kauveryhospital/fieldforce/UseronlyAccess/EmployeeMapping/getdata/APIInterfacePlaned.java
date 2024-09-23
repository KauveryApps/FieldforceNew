package com.kauveryhospital.fieldforce.UseronlyAccess.EmployeeMapping.getdata;

import com.google.gson.JsonObject;
import com.kauveryhospital.fieldforce.workstartserviceuseronly.getdata.Getcheckout;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public  interface APIInterfacePlaned {
  @Headers({
          "Accept: application/json",
          "Content-Type: application/json"
  })

  @POST("getchoices")
  Call<Getcheckout> getResult(@Body JsonObject user9);
}

