package com.freelance.ahmed.bakingapp.Clients;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by ahmed on 3/14/2018.
 */

public class RetrofitClient {
    public static String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/";
    public static Retrofit retrofit = null;


    public static Retrofit getRetrofitClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder().
                    baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
