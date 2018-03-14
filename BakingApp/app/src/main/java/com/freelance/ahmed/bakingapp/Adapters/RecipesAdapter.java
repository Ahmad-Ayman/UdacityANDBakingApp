package com.freelance.ahmed.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freelance.ahmed.bakingapp.Activities.StepsActivity;
import com.freelance.ahmed.bakingapp.Interfaces.ItemClickListener;
import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.freelance.ahmed.bakingapp.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ahmed on 3/14/2018.
 */

public class RecipesAdapter extends RecyclerView.Adapter<RecipesAdapter.RecipesAdapterViewHolder> {

    private Context mContext;
    private List<Recipes> allRecipesData;
    private List<Recipes.Steps> recipesSteps;
    private List<Recipes.Ingredients> recipesIngredients;
    private String recipeName;

    public RecipesAdapter (Context context, List<Recipes> rList){
        mContext=context;
        allRecipesData=rList;
    }


    @Override
    public RecipesAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipes_list_item,null);
        RecipesAdapter.RecipesAdapterViewHolder rcv = new RecipesAdapter.RecipesAdapterViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(RecipesAdapterViewHolder holder, int position) {
        recipeName = allRecipesData.get(position).getName();
        int stepsCount = allRecipesData.get(position).getSteps().size();
        recipesSteps= allRecipesData.get(position).getSteps();
        recipesIngredients = allRecipesData.get(position).getIngredients();
        String stepsCountString = String.valueOf(stepsCount);
        holder.mRecipeName.setText(recipeName);
        holder.mStepsCount.setText(stepsCountString);
        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                Intent i = new Intent(mContext, StepsActivity.class);
                recipeName = allRecipesData.get(pos).getName();
                recipesSteps= allRecipesData.get(pos).getSteps();
                recipesIngredients = allRecipesData.get(pos).getIngredients();
                i.putExtra("NAME",recipeName);
                Bundle stepsArgs = new Bundle();
                stepsArgs.putSerializable("STEPS_ARRAYLIST",(Serializable)recipesSteps);
                Bundle ingArgs = new Bundle();
                ingArgs.putSerializable("INGR_ARRAYLIST",(Serializable) recipesIngredients);
                i.putExtra("STEPS",stepsArgs);
                i.putExtra("INGREDIENTS",ingArgs);
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == allRecipesData)
            return 0;
        return allRecipesData.size();
    }
    public void setAllRecipesData(List<Recipes> recipesData) {
        allRecipesData = recipesData;
        notifyDataSetChanged();
    }
    //////////////////////////////////////// View Holder Class //////////////////////////
    public class RecipesAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mRecipeName;
        private TextView mStepsCount;

        private ItemClickListener itemClickListener;

        public RecipesAdapterViewHolder(View view) {
            super(view);
            // view.setOnClickListener(this);
            mRecipeName = (TextView) view.findViewById(R.id.recipetitle);
            mStepsCount = (TextView) view.findViewById(R.id.stepsnumber);
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
