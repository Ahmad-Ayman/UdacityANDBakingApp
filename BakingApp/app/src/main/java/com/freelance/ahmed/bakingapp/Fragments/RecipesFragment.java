package com.freelance.ahmed.bakingapp.Fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.freelance.ahmed.bakingapp.Adapters.RecipesAdapter;
import com.freelance.ahmed.bakingapp.Clients.RetrofitClient;
import com.freelance.ahmed.bakingapp.Interfaces.ApiInterface;
import com.freelance.ahmed.bakingapp.POJO.Recipes;
import com.freelance.ahmed.bakingapp.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipesFragment extends Fragment {
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private RecipesAdapter rcAdapter;
    private ProgressBar mLoadingIndicator;
    private RelativeLayout emptyView;
    private SwipeRefreshLayout swiping;
    private ApiInterface apiInterface;

    public RecipesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.allData));
        mLoadingIndicator =  view.findViewById(R.id.pb_loading_indicator);
        emptyView = view.findViewById(R.id.empty_view);
        rView = view.findViewById(R.id.rv_recipes);
        swiping =  view.findViewById(R.id.swiperefresh);
        lLayout = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        rView.setLayoutManager(lLayout);
        getAllRecipesData();
        swiping.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        rView.setVisibility(View.VISIBLE);
                        getAllRecipesData();
                        swiping.setRefreshing(false);
                    }
                }
        );

    }

    private void getAllRecipesData() {
        rView.setAdapter(null);
        apiInterface = RetrofitClient.getRetrofitClient().create(ApiInterface.class);
        Call<List<Recipes>> call = apiInterface.getAllRecipes();
        mLoadingIndicator.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<List<Recipes>>() {
            @Override
            public void onResponse(Call<List<Recipes>> call, Response<List<Recipes>> response) {
                List<Recipes> rlist = response.body();
                if(rlist !=null && !rlist.isEmpty()){
                    rcAdapter = new RecipesAdapter(getContext(), rlist);
                    rView.setAdapter(rcAdapter);
                    RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
                    itemAnimator.setAddDuration(1000);
                    itemAnimator.setRemoveDuration(1000);
                    rView.setItemAnimator(itemAnimator);
                    showRecipesDataView();
                }
                else{
                   showErrorEmptyView();
                }
            }

            @Override
            public void onFailure(Call<List<Recipes>> call, Throwable t) {
                Log.d("onFailure", t.toString());
                showErrorEmptyView();
            }
        });
    }
    private void showRecipesDataView() {
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.INVISIBLE);
        rView.setVisibility(View.VISIBLE);
    }
    private void showErrorEmptyView(){
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        rView.setVisibility(View.INVISIBLE);
        emptyView.setVisibility(View.VISIBLE);
    }
}
