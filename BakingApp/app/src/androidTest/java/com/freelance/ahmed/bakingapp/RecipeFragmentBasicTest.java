package com.freelance.ahmed.bakingapp;

import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.freelance.ahmed.bakingapp.Activities.MainActivity;
import com.freelance.ahmed.bakingapp.Activities.StepsActivity;
import com.freelance.ahmed.bakingapp.Activities.StepsDetailsActivity;
import com.freelance.ahmed.bakingapp.Fragments.StepsListFragment;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onData;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

/**
 * Created by ahmed on 3/19/2018.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeFragmentBasicTest {
    public static final String STEPS_DESC = "Description";
    @Rule
    public ActivityTestRule<StepsActivity> stepActivityTestRule
            = new ActivityTestRule<>(StepsActivity.class);
    @Before
    public void init() throws Exception{
        StepsListFragment stepsListFragment = new StepsListFragment();
        stepActivityTestRule.getActivity()
                .getSupportFragmentManager().beginTransaction().replace(R.id.master_list, stepsListFragment).commit();
    }
    @Test
    public void clickStepListItem_OpensStepsDetailsActivity(){
        onData(anything()).inAdapterView(withId(R.id.stepslist)).atPosition(0).perform(click());
        onView(withId(R.id.titleDesc)).check(matches(withText(STEPS_DESC)));
        onView(withId(R.id.prev_linear)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.INVISIBLE)));
        onView(withId(R.id.LinearNext)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }
}
