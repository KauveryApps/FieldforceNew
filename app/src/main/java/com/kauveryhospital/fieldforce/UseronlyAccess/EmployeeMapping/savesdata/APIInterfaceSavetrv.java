package com.kauveryhospital.fieldforce.UseronlyAccess.EmployeeMapping.savesdata;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterfaceSavetrv {


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })



    @POST("savedata")
    Call<ExampleSave> getResult(@Body JsonObject post);
}

