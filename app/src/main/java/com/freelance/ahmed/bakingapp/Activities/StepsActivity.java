package com.freelance.ahmed.bakingapp.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.freelance.ahmed.bakingapp.Fragments.IngredientsFrameFragment;
import com.freelance.ahmed.bakingapp.Fragments.IngredientsListFragment;
import com.freelance.ahmed.bakingapp.Fragments.StepsListFragment;
import com.freelance.ahmed.bakingapp.Widget.IngredientsWidget;
import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.freelance.ahmed.bakingapp.R;
import com.freelance.ahmed.bakingapp.Fragments.StepsDetailsFragment;
import com.freelance.ahmed.bakingapp.Fragments.StepsHolderFragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StepsActivity extends AppCompatActivity implements StepsListFragment.OnStepClickListener {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            LinearLayout stepsdetails = findViewById(R.id.steps_act_sw600dp);
            switch (item.getItemId()) {
                case R.id.navigation_ingred:
                    IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    if (stepsdetails != null) {
                        IngredientsFrameFragment ingredientsFrameFragment = new IngredientsFrameFragment();
                        FragmentManager mgr = getSupportFragmentManager();
                        mgr.beginTransaction().replace(R.id.steps_details_frame_for_fragment, ingredientsFrameFragment).commit();
                    }
                    manager.beginTransaction().replace(R.id.master_list, ingredientsListFragment).commit();
                    return true;
                case R.id.navigation_steps:
                    StepsListFragment stepsListFragment = new StepsListFragment();
                    FragmentManager managerSearch = getSupportFragmentManager();
                    if (stepsdetails != null) {
                        StepsHolderFragment stepsHolderFragment = new StepsHolderFragment();
                        FragmentManager mgr = getSupportFragmentManager();
                        mgr.beginTransaction().replace(R.id.steps_details_frame_for_fragment, stepsHolderFragment).commit();
                    }
                    managerSearch.beginTransaction().replace(R.id.master_list, stepsListFragment).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        BottomNavigationView navigation = findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
        FragmentManager manager = getSupportFragmentManager();
        LinearLayout stepsdetails = findViewById(R.id.steps_act_sw600dp);
        if (stepsdetails != null) {
            IngredientsFrameFragment ingredientsFrameFragment = new IngredientsFrameFragment();
            FragmentManager mgr = getSupportFragmentManager();
            mgr.beginTransaction().replace(R.id.steps_details_frame_for_fragment, ingredientsFrameFragment).commit();
        }
        manager.beginTransaction().replace(R.id.master_list, ingredientsListFragment).commit();

    }


    @Override
    public void onStepClick(int position) {
        Toast.makeText(this, "Position Clicked " + position, Toast.LENGTH_SHORT).show();
        if (findViewById(R.id.steps_act_sw600dp) != null) {
            saveInPref(position);
            StepsDetailsFragment stepsListFragment = new StepsDetailsFragment();
            FragmentManager mgr = getSupportFragmentManager();
            mgr.beginTransaction().replace(R.id.steps_details_frame_for_fragment, stepsListFragment).commit();
        } else {
            saveInPref(position);
            Intent i = new Intent(StepsActivity.this, StepsDetailsActivity.class);
            startActivity(i);
        }

    }

    private void saveInPref(int position) {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipes.Steps>>() {
        }.getType();
        String response = appSharedPrefs.getString("steps", "");
        ArrayList<Recipes.Steps> steps = gson.fromJson(response, type);
        SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
        String shortDesc = steps.get(position).getShortDesc();
        String longDesc = steps.get(position).getDesc();
        String vid = steps.get(position).getVideourl();
        String thum = steps.get(position).getThumb();
        prefsEditor.putString("shortDesc", shortDesc);
        prefsEditor.putString("longDesc", longDesc);
        prefsEditor.putString("vid", vid);
        prefsEditor.putString("thum", thum);
        prefsEditor.putInt("position", position);
        prefsEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            Type type = new TypeToken<List<Recipes.Ingredients>>() {
            }.getType();
            Type type2 = new TypeToken<List<Recipes.Steps>>() {
            }.getType();
            String response = appSharedPrefs.getString("ingred", "");
            String stepsResponse = appSharedPrefs.getString("steps", "");

            ArrayList<Recipes.Ingredients> ing = gson.fromJson(response, type);
            ArrayList<Recipes.Steps> st = gson.fromJson(stepsResponse, type2);

            SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
            Gson gsonSave = new Gson();
            String jsonSave = gsonSave.toJson(ing);
            String jsonSaveStp = gsonSave.toJson(st);
            prefsEditor.putString("ingred_widget", jsonSave);
            prefsEditor.putString("steps_widget", jsonSaveStp);
            prefsEditor.apply();
            IngredientsWidget.sendRefreshBroadcast(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
