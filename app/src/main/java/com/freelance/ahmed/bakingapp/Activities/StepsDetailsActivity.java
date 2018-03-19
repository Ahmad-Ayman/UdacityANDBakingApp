package com.freelance.ahmed.bakingapp.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.freelance.ahmed.bakingapp.R;
import com.freelance.ahmed.bakingapp.Fragments.StepsDetailsFragment;

public class StepsDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);
        Log.i("Info", "Steps Activity");
        ActionBar ab = getSupportActionBar();
        // Enable the Up button
        ab.setDisplayHomeAsUpEnabled(true);

        StepsDetailsFragment stepsDetailsFragment = new StepsDetailsFragment();
        FragmentManager mgr = getSupportFragmentManager();
        mgr.beginTransaction().replace(R.id.steps_details_frame_for_fragment, stepsDetailsFragment).commit();
    }
}
