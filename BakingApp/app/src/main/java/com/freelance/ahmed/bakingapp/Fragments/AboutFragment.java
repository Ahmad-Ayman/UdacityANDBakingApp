package com.freelance.ahmed.bakingapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelance.ahmed.bakingapp.R;

//import mehdi.sakout.aboutpage.AboutPage;
//import mehdi.sakout.aboutpage.Element;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        Element versionElement = new Element();
//        versionElement.setTitle("Version 1.0");
//        View aboutPage = new AboutPage(getContext())
//                .isRTL(false)
//                .setImage(R.drawable.udacity)
//                .setDescription(getResources().getString(R.string.about_app))
//                .addItem(versionElement)
//                .addGroup("Connect with us")
//                .addEmail("ahmed.ayman1708@gmail.com")
//                .addWebsite("http://ahmed-ayman1708.me/")
//                .addFacebook("a.ayman1996")
//                .addTwitter("Ahmeda1708")
//                .addGitHub("Ahmeda1708")
//                .create();
    }
}
