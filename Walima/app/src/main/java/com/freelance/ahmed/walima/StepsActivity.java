package com.freelance.ahmed.walima;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StepsActivity extends AppCompatActivity {
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private StepsAdapter rcAdapter;
    private Button ingred;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        setTitle("Ingredients and Steps");
        rView = findViewById(R.id.rv_stp);
        lLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(lLayout);
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipes.Steps>>() {
        }.getType();
        String response = appSharedPrefs.getString("steps", "");
        ArrayList<Recipes.Steps> steps = gson.fromJson(response, type);


        if (steps != null && !steps.isEmpty()) {
            rcAdapter = new StepsAdapter(this, steps);
            rView.setAdapter(rcAdapter);
            RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
            itemAnimator.setAddDuration(1000);
            itemAnimator.setRemoveDuration(1000);
            rView.setItemAnimator(itemAnimator);

        }
        else {
            Log.e("stp", "Steps null");
        }
        ingred = findViewById(R.id.ingred_btn);
        ingred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(),DetailActivity.class);
                i.putExtra("from","ingredients");
                startActivity(i);
            }
        });
    }
}
