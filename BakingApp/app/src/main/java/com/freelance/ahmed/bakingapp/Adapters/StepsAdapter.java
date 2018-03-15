package com.freelance.ahmed.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freelance.ahmed.bakingapp.Activities.StepsDetailsActivity;
import com.freelance.ahmed.bakingapp.Interfaces.ItemClickListener;
import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.freelance.ahmed.bakingapp.R;

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
    public void onBindViewHolder(StepsAdapterViewHolder holder, int position) {
        shortDesc = allStepsData.get(position).getShortDesc();
        longDesc = allStepsData.get(position).getDesc();
        videoUrl = allStepsData.get(position).getVideourl();
        thumb = allStepsData.get(position).getThumb();
        holder.title.setText(shortDesc);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent i = new Intent(mContext, StepsDetailsActivity.class);
                shortDesc = allStepsData.get(pos).getShortDesc();
                longDesc = allStepsData.get(pos).getDesc();
                videoUrl = allStepsData.get(pos).getVideourl();
                thumb = allStepsData.get(pos).getThumb();
                i.putExtra("SHORT_DESC_KEY", shortDesc);
                i.putExtra("LONG_DESC_KEY", longDesc);
                i.putExtra("VIDEO_URL_KEY", videoUrl);
                i.putExtra("THUMB_KEY",thumb);
                i.putExtra("position",pos);
                mContext.startActivity(i);
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


        private ItemClickListener itemClickListener;

        public StepsAdapterViewHolder(View view) {
            super(view);
            // view.setOnClickListener(this);
            title = (TextView) view.findViewById(R.id.title);
            view.setOnClickListener(this);
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
}
