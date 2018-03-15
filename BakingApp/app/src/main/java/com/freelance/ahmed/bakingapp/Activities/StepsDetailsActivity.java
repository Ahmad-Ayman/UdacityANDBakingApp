package com.freelance.ahmed.bakingapp.Activities;

import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.freelance.ahmed.bakingapp.R;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
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
import com.google.android.exoplayer2.util.Util;

import org.w3c.dom.Text;

public class StepsDetailsActivity extends AppCompatActivity {
    SimpleExoPlayerView exoPlayerView;
    SimpleExoPlayer exoPlayer;
    String videoURL = null;
    private ImageView holder;
    private TextView longDesc;
    String thumbURL=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps_details);
        exoPlayerView = findViewById(R.id.video_player);
        holder=findViewById(R.id.holderimage);
        videoURL = getIntent().getStringExtra("VIDEO_URL_KEY");
        thumbURL = getIntent().getStringExtra("THUMB_KEY");
        Log.i("Link of Video", videoURL);
        if(videoURL == null || videoURL.isEmpty()){
            if(thumbURL ==null || thumbURL.isEmpty()) {
                exoPlayerView.setVisibility(View.GONE);
                holder.setVisibility(View.VISIBLE);
            }
            else {
                initializeExoPlayer(thumbURL);
            }
        } else{
            initializeExoPlayer(videoURL);
        }
        longDesc = findViewById(R.id.longdesc);
        longDesc.setText(getIntent().getStringExtra("LONG_DESC_KEY"));
    }
    private void initializeExoPlayer(String url){
        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, trackSelector);

            Uri videoURI = Uri.parse(url);

            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

            exoPlayerView.setPlayer(exoPlayer);
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
