package com.kauveryhospital.fieldforce.UserAdmin.contactadmin.savedataadmin;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterfaceSaveadmin {


    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })



    @POST("savedata")
    Call<ExampleSaveadmin> getResult(@Body JsonObject post);
}

