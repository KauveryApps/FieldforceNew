package com.kauveryhospital.fieldforce.UseronlyAccess.travelExpenses.uploadfile;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;

public interface APIInterfaceSaveupl {


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })


    @POST("uploadfile")

    Call<ExampleSaveupl> getResult(@Body JsonObject post);
}

