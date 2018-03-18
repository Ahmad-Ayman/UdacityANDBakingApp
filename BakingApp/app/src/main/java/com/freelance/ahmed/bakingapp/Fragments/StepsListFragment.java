package com.freelance.ahmed.bakingapp.Fragments;


import android.app.Activity;
import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
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
    public interface OnStepClickListener {
        void onStepClick(int position);
    }

    OnStepClickListener mCallBack;

    public StepsListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallBack = (OnStepClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement on Step Click");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_steps_list, container, false);
        getActivity().setTitle(getResources().getString(R.string.stepsofrecipe));
        ListView gridView = (ListView) rootView.findViewById(R.id.stepslist);
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this.getContext());
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipes.Steps>>() {
        }.getType();
        String response = appSharedPrefs.getString("steps", "");
        ArrayList<Recipes.Steps> steps = gson.fromJson(response, type);

        StepsAdapter sAdapter = new StepsAdapter(getContext(),R.layout.detail_recipe_list_item, steps);
        gridView.setAdapter(sAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                mCallBack.onStepClick(position);
            }
        });
        gridView.setItemsCanFocus(true);
        return rootView;


    }


}



