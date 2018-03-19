package com.freelance.ahmed.bakingapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.freelance.ahmed.bakingapp.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IngredientsFrameFragment extends Fragment {

    public IngredientsFrameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_ingredients_frame, container, false);
        final ImageView imageView = rootView.findViewById(R.id.ingredient_part_image_view);
        return rootView;

    }


}
