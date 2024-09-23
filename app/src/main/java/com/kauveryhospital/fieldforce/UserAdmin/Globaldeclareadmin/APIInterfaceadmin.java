package com.kauveryhospital.fieldforce.UserAdmin.Globaldeclareadmin;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIInterfaceadmin {
    @Headers({
            "Accept: application/json",
            "Content-Type: application/json"
    })

    @POST("getchoices")
   Call<Exampleadmin>getResult(@Body JsonObject user9);
}

