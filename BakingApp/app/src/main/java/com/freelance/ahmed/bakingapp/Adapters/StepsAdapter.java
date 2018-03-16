package com.freelance.ahmed.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.freelance.ahmed.bakingapp.Activities.StepsDetailsActivity;
import com.freelance.ahmed.bakingapp.Interfaces.ItemClickListener;
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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 3/15/2018.
 */

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsAdapterViewHolder> {
    private Context mContext;
    private List<Recipes.Steps> allStepsData;
    private String shortDesc;
    private String longDesc;
    private String videoUrl;
    private String thumb;
    public StepsAdapter(Context context, List<Recipes.Steps> sList) {
        mContext = context;
        allStepsData = sList;
    }

    @Override
    public StepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.detail_recipe_list_item, null);
        StepsAdapter.StepsAdapterViewHolder rcv = new StepsAdapter.StepsAdapterViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(final StepsAdapterViewHolder holder, int position) {
        shortDesc = allStepsData.get(position).getShortDesc();
        longDesc = allStepsData.get(position).getDesc();
        videoUrl = allStepsData.get(position).getVideourl();
        thumb = allStepsData.get(position).getThumb();
        holder.title.setText(shortDesc);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                if(holder.stepsTwopane != null){
//                    SharedPreferences appSharedPrefs = PreferenceManager
//                            .getDefaultSharedPreferences(mContext);
//                    SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
//                    prefsEditor.putString("SHORT_DESC_KEY", shortDesc);
//                    prefsEditor.putString("LONG_DESC_KEY", longDesc);
//                    prefsEditor.putString("VIDEO_URL_KEY", videoUrl);
//                    prefsEditor.putString("THUMB_KEY",thumb);
//                    prefsEditor.putInt("position",pos);
//                    prefsEditor.apply();

                    SimpleExoPlayer exoPlayer;
                    String videoLink = allStepsData.get(pos).getVideourl();
                    String thumbLink = allStepsData.get(pos).getThumb();

                    String longdes = allStepsData.get(pos).getDesc();
                    String shortdes = allStepsData.get(pos).getShortDesc();
                    if (videoLink == null || videoLink.isEmpty()) {
                        if (thumbLink == null || thumbLink.isEmpty()) {
                            holder.exoPlayerView = v.findViewById(R.id.video_player);
                            holder.exoPlayerView.setVisibility(View.INVISIBLE);
                            holder.holderImage.setVisibility(View.VISIBLE);
                        } else {
                            holder.exoPlayerView = v.findViewById(R.id.video_player);
                            holder.exoPlayerView.setVisibility(View.VISIBLE);
                            holder.holderImage.setVisibility(View.INVISIBLE);
                           // initializeExoPlayer(thumbLink);
                            try {
                                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                                TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                                exoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

                                Uri videoURI = Uri.parse(thumbLink);

                                DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                                MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                                holder.exoPlayerView.setPlayer(exoPlayer);
                                holder.exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                                exoPlayer.prepare(mediaSource);
                                exoPlayer.setPlayWhenReady(true);
                            } catch (Exception e) {
                                Log.e("MainAcvtivity", " exoplayer error " + e.toString());
                            }
                        }
                    } else {
                        holder.exoPlayerView = v.findViewById(R.id.video_player);
                        holder.exoPlayerView.setVisibility(View.VISIBLE);
                        holder.holderImage.setVisibility(View.INVISIBLE);
                        //initializeExoPlayer(videoLink);
                        try {
                            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
                            exoPlayer = ExoPlayerFactory.newSimpleInstance(mContext, trackSelector);

                            Uri videoURI = Uri.parse(videoLink);

                            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("exoplayer_video");
                            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                            MediaSource mediaSource = new ExtractorMediaSource(videoURI, dataSourceFactory, extractorsFactory, null, null);

                            holder.exoPlayerView.setPlayer(exoPlayer);
                            holder.exoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                            exoPlayer.prepare(mediaSource);
                            exoPlayer.setPlayWhenReady(true);
                        } catch (Exception e) {
                            Log.e("MainAcvtivity", " exoplayer error " + e.toString());
                        }
                    }

                }else {
                    Intent i = new Intent(mContext, StepsDetailsActivity.class);
                    shortDesc = allStepsData.get(pos).getShortDesc();
                    longDesc = allStepsData.get(pos).getDesc();
                    videoUrl = allStepsData.get(pos).getVideourl();
                    thumb = allStepsData.get(pos).getThumb();
                    i.putExtra("SHORT_DESC_KEY", shortDesc);
                    i.putExtra("LONG_DESC_KEY", longDesc);
                    i.putExtra("VIDEO_URL_KEY", videoUrl);
                    i.putExtra("THUMB_KEY", thumb);
                    i.putExtra("position", pos);
                    mContext.startActivity(i);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        if (null == allStepsData)
            return 0;
        return allStepsData.size();
    }

    public void setAllStepsData(List<Recipes.Steps> stepsData) {
        allStepsData = stepsData;
        notifyDataSetChanged();
    }


    //////////////////////////////////////// View Holder Class //////////////////////////
    public class StepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private LinearLayout stepsTwopane;
        SimpleExoPlayerView exoPlayerView;
       

        private ImageView holderImage;
        private TextView longDesc;


        private ItemClickListener itemClickListener;

        public StepsAdapterViewHolder(View view) {
            super(view);
            // view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            stepsTwopane = view.findViewById(R.id.activitySteptwoPane);
            view.setOnClickListener(this);
            exoPlayerView = view.findViewById(R.id.video_player);
            longDesc=view.findViewById(R.id.longdesc);
            holderImage = view.findViewById(R.id.holderimage);

        }

        @Override
        public void onClick(View view) {
            this.itemClickListener.onItemClick(view, getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener ic) {
            this.itemClickListener = ic;
        }
    }
    //////////////////////////////////////////////////
    private void initializeExoPlayer(String url) {

    }
}
