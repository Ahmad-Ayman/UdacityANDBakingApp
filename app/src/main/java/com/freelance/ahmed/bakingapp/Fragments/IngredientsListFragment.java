package com.freelance.ahmed.bakingapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelance.ahmed.bakingapp.Adapters.IngredientsAdapter;
import com.freelance.ahmed.bakingapp.Adapters.StepsAdapter;
import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.freelance.ahmed.bakingapp.R;
import com.google.android.exoplayer2.util.Util;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


public class IngredientsListFragment extends Fragment {
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private IngredientsAdapter rcAdapter;
    int lastFirstVisiblePosition;
    private static final String BUNDLE_RECYCLER_LAYOUT = "bakingapp.recycler.layout";

    public IngredientsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_ingredients_list, container, false);
        getActivity().setTitle(getResources().getString(R.string.ingred));
        rView = view.findViewById(R.id.rv_ing);

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getContext());
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipes.Ingredients>>() {
        }.getType();
        String response = appSharedPrefs.getString("ingred", "");
        ArrayList<Recipes.Ingredients> ingredients = gson.fromJson(response, type);
        lLayout = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(lLayout);
        if (ingredients != null && !ingredients.isEmpty()) {
            rcAdapter = new IngredientsAdapter(getContext(), ingredients);
            rView.setAdapter(rcAdapter);
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setAddDuration(1000);
            itemAnimator.setRemoveDuration(1000);
            rView.setItemAnimator(itemAnimator);

        } else {
            Log.e("ingr", "Ingredientsis null");
        }

        return view;
    }

    @Override
    public void onPause() {
        super.onPause();

        lastFirstVisiblePosition = ((LinearLayoutManager) rView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onStop() {
        super.onStop();
        lastFirstVisiblePosition = ((LinearLayoutManager) rView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((LinearLayoutManager) rView.getLayoutManager()).scrollToPosition(lastFirstVisiblePosition);
        lastFirstVisiblePosition=0;
    }
}
