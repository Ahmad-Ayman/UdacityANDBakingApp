package com.freelance.ahmed.bakingapp.Activities;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.freelance.ahmed.bakingapp.R;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;

public class StepsDetailsActivity extends AppCompatActivity {
    SimpleExoPlayer player;
    SimpleExoPlayerView playerView;
    String videoUrl="https://d17h27t6h515a5.cloudfront.net/topher/2017/April/58ffd974_-intro-creampie/-intro-creampie.mp4";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);
        playerView = findViewById(R.id.video_player);
        BandwidthMeter bandwidthMeter= new DefaultBandwidthMeter();
        TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
        player = ExoPlayerFactory.newSimpleInstance(this,trackSelector);
        Uri videoUri = Uri.parse(videoUrl);

        DefaultHttpDataSourceFactory dataSource = new DefaultHttpDataSourceFactory("exoplayer_video");
        ExtractorsFactory extractorsFactory= new DefaultExtractorsFactory();
        MediaSource videoSource = new ExtractorMediaSource(videoUri,dataSource,extractorsFactory,null,null);
        playerView.setPlayer(player);
        player.prepare(videoSource);
        player.setPlayWhenReady(true);

    }
}
