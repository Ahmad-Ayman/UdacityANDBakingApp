package com.freelance.ahmed.bakingapp.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.freelance.ahmed.bakingapp.Fragments.IngredientsListFragment;
import com.freelance.ahmed.bakingapp.Fragments.StepsListFragment;
import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.freelance.ahmed.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class StepsActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.navigation_ingred:
                IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
                FragmentManager manager = getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.recyclerViewDataLayoutForRecipe, ingredientsListFragment).commit();
                return true;
            case R.id.navigation_steps:
                StepsListFragment stepsListFragment = new StepsListFragment();
                FragmentManager managerSearch = getSupportFragmentManager();
                managerSearch.beginTransaction().replace(R.id.recyclerViewDataLayoutForRecipe, stepsListFragment).commit();
                return true;
        }
        return false;
    }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);


        BottomNavigationView navigation = findViewById(R.id.navigation2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        IngredientsListFragment ingredientsListFragment = new IngredientsListFragment();
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.recyclerViewDataLayoutForRecipe, ingredientsListFragment).commit();
    }




}
