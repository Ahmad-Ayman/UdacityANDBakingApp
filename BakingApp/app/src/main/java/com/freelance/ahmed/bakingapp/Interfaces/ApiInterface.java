package com.freelance.ahmed.bakingapp.Interfaces;

import com.freelance.ahmed.bakingapp.POJO.RecipesList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ahmed on 3/14/2018.
 */

public interface ApiInterface {
    @GET("59121517_baking/baking.json")
    Call<RecipesList> getAllRecipes ();
}
