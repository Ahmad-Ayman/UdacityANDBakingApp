package com.freelance.ahmed.bakingapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelance.ahmed.bakingapp.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepsHolderFragment extends Fragment {


    public StepsHolderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps_holder, container, false);
    }

}
