package com.freelance.ahmed.bakingapp.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.freelance.ahmed.bakingapp.R;

import java.util.ArrayList;
import java.util.List;

public class StepsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("STEPS");
        ArrayList<Recipes.Steps> steps = (ArrayList<Recipes.Steps>) args.getSerializable("STEPS_ARRAYLIST");
        Bundle argsIng = intent.getBundleExtra("INGREDIENTS");
        ArrayList<Recipes.Ingredients> ingredients = (ArrayList<Recipes.Ingredients>) argsIng.getSerializable("INGR_ARRAYLIST");
    }
}
