package com.freelance.ahmed.walima;

import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private IngredientsAdapter rcAdapter;
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    String videoURL = null;
    private ImageView holder;
    private TextView longDesc;
    private int pos;
    String thumbURL = null;
    String longdes, shortdes;
    LinearLayout prev, nex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        FrameLayout stepdetails = findViewById(R.id.steps_frame_layout);
        FrameLayout listofIngredients = findViewById(R.id.ing_frame_layout);
        String from = getIntent().getStringExtra("from");
        if(from.equals("ingredients")){
            stepdetails.setVisibility(View.INVISIBLE);
            listofIngredients.setVisibility(View.VISIBLE);
            setTitle(getResources().getString(R.string.ingred));
            rView = findViewById(R.id.rv_ing);
            lLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            rView.setLayoutManager(lLayout);
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            Type type = new TypeToken<List<Recipes.Ingredients>>() {
            }.getType();
            String response = appSharedPrefs.getString("ingred", "");
            ArrayList<Recipes.Ingredients> ingredients = gson.fromJson(response, type);


            if (ingredients != null && !ingredients.isEmpty()) {
                rcAdapter = new IngredientsAdapter(this, ingredients);
                rView.setAdapter(rcAdapter);
                RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
                itemAnimator.setAddDuration(1000);
                itemAnimator.setRemoveDuration(1000);
                rView.setItemAnimator(itemAnimator);

            } else {
                Log.e("ingr", "Ingredientsis null");
            }
        }else if ( from.equals("StepsAdapter")){
            listofIngredients.setVisibility(View.INVISIBLE);
            stepdetails.setVisibility(View.VISIBLE);
            ActionBar ab = getSupportActionBar();
            // Enable the Up button
            ab.setDisplayHomeAsUpEnabled(true);
            // first time after being here from adapter
            videoURL = getIntent().getStringExtra("VIDEO_URL_KEY");
            thumbURL = getIntent().getStringExtra("THUMB_KEY");
            longdes = getIntent().getStringExtra("LONG_DESC_KEY");
            shortdes = getIntent().getStringExtra("SHORT_DESC_KEY");
            pos = getIntent().getIntExtra("position", -1);
            prev = findViewById(R.id.prev_linear);
            nex = findViewById(R.id.LinearNext);

            updateUI(pos);
            // End of first time
            SharedPreferences appSharedPrefs = PreferenceManager
                    .getDefaultSharedPreferences(this);
            Gson gson = new Gson();
            Type type = new TypeToken<List<Recipes.Steps>>() {
            }.getType();
            String response = appSharedPrefs.getString("steps", "");
            final ArrayList<Recipes.Steps> steps = gson.fromJson(response, type);

            prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos = pos - 1;
                    if (pos == 0) {
                        Toast.makeText(DetailActivity.this, "First step", Toast.LENGTH_SHORT).show();
                        prev.setVisibility(View.INVISIBLE);
                    }
                    if (exoPlayer != null)
                        releaseExoPlayer();
                    updateUI(pos);
                }
            });
            nex.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pos = pos + 1;
                    if (pos == steps.size() - 1) {
                        Toast.makeText(DetailActivity.this, "Last step", Toast.LENGTH_SHORT).show();
                        nex.setVisibility(View.INVISIBLE);
                    }
                    if (exoPlayer != null)
                        releaseExoPlayer();
                    updateUI(pos);
                }
            });


            Log.i("Link of Video", videoURL);




        }

    }
    private void updateUI(int position) {
        String shortDes;
        String longDes;
        String videoLink;
        String thumbLink;

        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(this);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipes.Steps>>() {
        }.getType();
        String response = appSharedPrefs.getString("steps", "");
        final ArrayList<Recipes.Steps> steps = gson.fromJson(response, type);
        if (position == 0) {
            prev.setVisibility(View.INVISIBLE);
        } else if (position == steps.size() - 1) {
            nex.setVisibility(View.INVISIBLE);
        } else {
            prev.setVisibility(View.VISIBLE);
            nex.setVisibility(View.VISIBLE);
        }
        shortDes = steps.get(position).getShortDesc();
        longDes = steps.get(position).getDesc();
        videoLink = steps.get(position).getVideourl();
        thumbLink = steps.get(position).getThumb();
        setTitle(shortDes);
        longDesc = findViewById(R.id.longdesc);
        holder = findViewById(R.id.holderimage);
        longDesc.setText(longDes);
        if (videoLink == null || videoLink.isEmpty()) {
            if (thumbLink == null || thumbLink.isEmpty()) {
                exoPlayerView = findViewById(R.id.video_player);
                exoPlayerView.setVisibility(View.INVISIBLE);
                holder.setVisibility(View.VISIBLE);
            } else {
                exoPlayerView = findViewById(R.id.video_player);
                exoPlayerView.setVisibility(View.VISIBLE);
                holder.setVisibility(View.INVISIBLE);
                initializeExoPlayer(thumbLink);
            }
        } else {
            exoPlayerView = findViewById(R.id.video_player);
            exoPlayerView.setVisibility(View.VISIBLE);
            holder.setVisibility(View.INVISIBLE);
            initializeExoPlayer(videoLink);
        }

    }

    private void initializeExoPlayer(String url) {
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            Uri videoURI = Uri.parse(url);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            exoPlayerView.setPlayer(exoPlayer);
            exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        } catch (Exception e) {
            Log.e("MainAcvtivity", " exoplayer error " + e.toString());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (exoPlayer != null)
            releaseExoPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (exoPlayer != null)
            releaseExoPlayer();
    }

    private void releaseExoPlayer() {
        exoPlayer.stop();
        exoPlayer.release();
        exoPlayer = null;
    }
}
