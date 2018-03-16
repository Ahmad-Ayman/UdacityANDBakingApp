package com.freelance.ahmed.walima;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ahmed on 3/16/2018.
 */

public interface ApiInterface {
    @GET("59121517_baking/baking.json")
    Call<List<Recipes>> getAllRecipes ();
}
