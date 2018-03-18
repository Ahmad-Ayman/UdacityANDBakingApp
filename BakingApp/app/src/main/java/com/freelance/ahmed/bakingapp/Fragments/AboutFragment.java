package com.freelance.ahmed.bakingapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freelance.ahmed.bakingapp.R;
import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

public class AboutFragment extends Fragment {


    public AboutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Element versionElement = new Element();
        versionElement.setTitle("Version 1.0");
        View aboutPage = new AboutPage(getContext())
                .isRTL(false)
                .setDescription(getResources().getString(R.string.desc_about))
                .setImage(R.drawable.bbq)
                .addItem(versionElement)
                .addGroup("Connect with us")
                .addEmail("ahmed.ayman1708@gmail.com")
                .addWebsite("http://ahmed-ayman1708.me/")
                .addFacebook("a.ayman1996")
                .addTwitter("Ahmeda1708")
                .addGitHub("Ahmeda1708")
                .create();
        return (aboutPage);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("About Walima");




    }
}
