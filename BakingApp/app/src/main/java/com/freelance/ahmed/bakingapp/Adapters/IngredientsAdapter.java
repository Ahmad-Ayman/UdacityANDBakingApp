package com.freelance.ahmed.bakingapp.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.freelance.ahmed.bakingapp.Interfaces.ItemClickListener;
import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.freelance.ahmed.bakingapp.R;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by ahmed on 3/15/2018.
 */

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder> {
    private Context mContext;
    private List<Recipes.Ingredients> allIngrData;
    private String ingred;
    private String measure;
    private double quantity;

    public IngredientsAdapter(Context context, List<Recipes.Ingredients> iList) {
        mContext = context;
        allIngrData = iList;
    }

    @Override
    public IngredientsAdapter.IngredientsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingred_list_item, null);
        IngredientsAdapter.IngredientsViewHolder rcv = new IngredientsAdapter.IngredientsViewHolder(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(IngredientsAdapter.IngredientsViewHolder holder, int position) {
        ingred = allIngrData.get(position).getIngred();
        measure = allIngrData.get(position).getMeasure();
        quantity = allIngrData.get(position).getQuantity();
        holder.ing.setText(ingred);
        holder.meas.setText(measure);
        holder.quan.setText(String.valueOf(quantity));

    }

    @Override
    public int getItemCount() {
        if (null == allIngrData)
            return 0;
        return allIngrData.size();
    }

    public void setAllIngredientssData(List<Recipes.Ingredients> ingredientssData) {
        allIngrData = ingredientssData;
        notifyDataSetChanged();
    }


    //////////////////////////////////////// View Holder Class //////////////////////////
    public class IngredientsViewHolder extends RecyclerView.ViewHolder {
        private TextView ing;
        private TextView meas;
        private TextView quan;



        public IngredientsViewHolder(View view) {
            super(view);
            // view.setOnClickListener(this);
            ing = (TextView) view.findViewById(R.id.ingred);
            meas = (TextView) view.findViewById(R.id.tv_measure);
            quan = (TextView) view.findViewById(R.id.quantity);

        }

    }
    //////////////////////////////////////////////////
}
