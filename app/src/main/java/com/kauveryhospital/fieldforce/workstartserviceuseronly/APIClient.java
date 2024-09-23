package com.kauveryhospital.fieldforce.workstartserviceuseronly;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIClient {
   private static Retrofit retrofit = null;

   public static Retrofit getClient() {

       HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
       interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
       OkHttpClient client = new OkHttpClient.Builder().connectTimeout(100, TimeUnit.SECONDS)
               .readTimeout(100, TimeUnit.SECONDS)
               .addInterceptor(interceptor).build();
        retrofit = new Retrofit.Builder()
               .baseUrl("https://kforce.kauveryhospital.com/fieldforcescripts/ASBTStructRest.dll/datasnap/rest/TASBTStruct/")
               .addConverterFactory(GsonConverterFactory.create())
               .client(client)
               .build();
        return retrofit;
   }
}

                                                                                                                                                                                                                                                                                                                                                                                      