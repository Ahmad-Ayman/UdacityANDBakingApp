package com.freelance.ahmed.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.freelance.ahmed.bakingapp.Activities.MainActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by ahmed on 3/19/2018.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeFragmentBasicTest {
    @Rule public ActivityTestRule<MainActivity> mainActivityActivityTestRule
            = new ActivityTestRule<>(MainActivity.class);
    @Before
    public void init(){
        mainActivityActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction();
    }
    @Test
    public void showRecipesList(){

    }
}
