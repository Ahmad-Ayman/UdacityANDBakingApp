package com.freelance.ahmed.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 3/18/2018.
 */

public class MyWidgetRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context mContext;
    private List<Recipes.Ingredients> ingredientsList;
    public MyWidgetRemoteViewsFactory(Context mContext,Intent intent) {
        this.mContext = mContext;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        SharedPreferences appSharedPrefs = PreferenceManager
                .getDefaultSharedPreferences(mContext);
        Gson gson = new Gson();
        Type type = new TypeToken<List<Recipes.Ingredients>>() {}.getType();
        String response = appSharedPrefs.getString("ingred_widget", "");

        ingredientsList = gson.fromJson(response, type);
        if(ingredientsList == null) {
            Log.i("Factoryy","No List");
        }
        else{
            Log.i("Factoryy","there is a List");
            Log.i("Factorry2","Ingrediant name = "+ingredientsList.get(0).getIngred());
        }



    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientsList == null ? 0 : ingredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        if (i == AdapterView.INVALID_POSITION ||
                ingredientsList == null ) {
            return null;
        }
        RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.collection_widget_list_item);

        rv.setTextViewText(R.id.widgetItemTaskNameLabel, ingredientsList.get(i).getIngred());

        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;

    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
