package com.freelance.ahmed.bakingapp.Fragments;


import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.freelance.ahmed.bakingapp.Activities.StepsDetailsActivity;
import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.freelance.ahmed.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StepsDetailsFragment extends Fragment {
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    ImageView holderImage;
    TextView longDescTv;
    String videoURL = null;
    private int pos;
    String thumbURL = null;

    String longdes, shortdes;
    LinearLayout prev;
    LinearLayout nex;
    ArrayList<Recipes.Steps> stepsList;

    public StepsDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_steps_details, container, false);
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(getContext());
        if(savedInstanceState != null) {
            videoURL =savedInstanceState.getString("VIDEO_URL");
            thumbURL=savedInstanceState.getString("THUMB_URL");
            pos=savedInstanceState.getInt("POSITION");
            longdes=savedInstanceState.getString("LONG_DESC");
            shortdes=savedInstanceState.getString("SHORT_DESC");
        }else{
            shortdes = appSharedPrefs.getString("shortDesc", "");
            longdes = appSharedPrefs.getString("longDesc", "");
            videoURL = appSharedPrefs.getString("vid", "");
            thumbURL = appSharedPrefs.getString("thum", "");
            pos = appSharedPrefs.getInt("position", -1);
        }
        final View x = view;
        Log.i("info in fragment", "Steps Details Fragment Created Successfully");

        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipes.Steps>>() {
        }.getType();
        String response = appSharedPrefs.getString("steps", "");
        stepsList = gson.fromJson(response, type);

        updateUI(x, pos);
        exoPlayerView = view.findViewById(R.id.video_player);
        longDescTv = view.findViewById(R.id.longdesc);
        holderImage = view.findViewById(R.id.holderimage);
        prev = view.findViewById(R.id.prev_linear);
        nex = view.findViewById(R.id.LinearNext);
        if (getActivity().findViewById(R.id.steps_act_sw600dp) != null) {
            prev.setVisibility(View.INVISIBLE);
            nex.setVisibility(View.INVISIBLE);
        } else {
            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vieww) {
                    pos = pos - 1;
                    if (pos == 0) {
                        Toast.makeText(getContext(), "First step", Toast.LENGTH_SHORT).show();
                        prev.setVisibility(View.INVISIBLE);
                    }
                    if (exoPlayer != null)
                        releaseExoPlayer();
                    updateUI(x, pos);
                }
            });
            nex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View vieww) {
                    pos = pos + 1;
                    if (pos == stepsList.size() - 1) {
                        Toast.makeText(getContext(), "Last step", Toast.LENGTH_SHORT).show();
                        nex.setVisibility(View.INVISIBLE);
                    }
                    if (exoPlayer != null)
                        releaseExoPlayer();
                    updateUI(x, pos);
                }
            });
        }

        return view;
    }


    private void updateUI(View v, int position) {
        SimpleExoPlayerView exoPlayerViewUI = v.findViewById(R.id.video_player);
        ImageView holderImage = v.findViewById(R.id.holderimage);
        TextView longDescTv = v.findViewById(R.id.longdesc);
        LinearLayout prevUI = v.findViewById(R.id.prev_linear);
        LinearLayout nexUI = v.findViewById(R.id.LinearNext);
        if (position == 0) {
            prevUI.setVisibility(View.INVISIBLE);
        } else if (position == stepsList.size() - 1) {
            nexUI.setVisibility(View.INVISIBLE);
        } else {
            prevUI.setVisibility(View.VISIBLE);
            nexUI.setVisibility(View.VISIBLE);
        }
        getActivity().setTitle(stepsList.get(position).getShortDesc());

        longDescTv.setText(stepsList.get(position).getDesc());
        if ((stepsList.get(position).getVideourl()) == null || (stepsList.get(position).getVideourl()).isEmpty()) {
            if ((stepsList.get(position).getThumb()) == null || (stepsList.get(position).getThumb()).isEmpty()) {

                exoPlayerViewUI.setVisibility(View.INVISIBLE);
                holderImage.setVisibility(View.VISIBLE);
            } else {

                exoPlayerViewUI.setVisibility(View.INVISIBLE);
                Picasso.with(getContext()).load(stepsList.get(position).getThumb()).into(holderImage);
                holderImage.setVisibility(View.VISIBLE);
                //initializeExoPlayer((stepsList.get(position).getThumb()), v);

            }
        } else {

            exoPlayerViewUI.setVisibility(View.VISIBLE);
            holderImage.setVisibility(View.INVISIBLE);
            initializeExoPlayer((stepsList.get(position).getVideourl()), v);
        }

    }

    private void initializeExoPlayer(String url, View v) {
        SimpleExoPlayerView exoPlayerViewInitialize = v.findViewById(R.id.video_player);

        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);

            Uri videoURI = Uri.parse(url);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            exoPlayerViewInitialize.setPlayer(exoPlayer);
            exoPlayerViewInitialize.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        } catch (Exception e) {
            Log.e("MainAcvtivity", " exoplayer error " + e.toString());
        }
    }

    private void releaseExoPlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null)
            releaseExoPlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (exoPlayer != null)
            releaseExoPlayer();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null)
            releaseExoPlayer();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("VIDEO_URL",videoURL);
        outState.putString("THUMB_URL",thumbURL);
        outState.putInt("POSITION",pos);
        outState.putString("LONG_DESC",longdes);
        outState.putString("SHORT_DESC",shortdes);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) {
            videoURL = savedInstanceState.getString("VIDEO_URL");
            thumbURL = savedInstanceState.getString("THUMB_URL");
            pos = savedInstanceState.getInt("POSITION");
            longdes = savedInstanceState.getString("LONG_DESC");
            shortdes = savedInstanceState.getString("SHORT_DESC");
        }
    }
}
