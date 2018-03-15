package com.freelance.ahmed.bakingapp.Fragments;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.freelance.ahmed.bakingapp.Adapters.RecipesAdapter;
import com.freelance.ahmed.bakingapp.Adapters.StepsAdapter;
import com.freelance.ahmed.bakingapp.Interfaces.ApiInterface;
import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.freelance.ahmed.bakingapp.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsListFragment extends Fragment {
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private StepsAdapter rcAdapter;

    public StepsListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.stepsofrecipe));
        rView = view.findViewById(R.id.rv_stp);
        lLayout = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(lLayout);
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getContext());
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipes.Steps>>() {
        }.getType();
        String response = appSharedPrefs.getString("steps", "");
        ArrayList<Recipes.Steps> steps = gson.fromJson(response, type);


        if (steps != null && !steps.isEmpty()) {
            rcAdapter = new StepsAdapter(getContext(), steps);
            rView.setAdapter(rcAdapter);
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setAddDuration(1000);
            itemAnimator.setRemoveDuration(1000);
            rView.setItemAnimator(itemAnimator);

        }
        else {
            Log.e("stp", "Steps null");
        }
    }
}



