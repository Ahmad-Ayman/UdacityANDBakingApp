package com.freelance.ahmed.walima;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rView;
    private LinearLayoutManager lLayout;
    private RecipesAdapter rcAdapter;
    private ProgressBar mLoadingIndicator;
    private RelativeLayout emptyView;
    private SwipeRefreshLayout swiping;
    private ApiInterface apiInterface;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(getResources().getString(R.string.allData));
        mLoadingIndicator =  findViewById(R.id.pb_loading_indicator);
        emptyView = findViewById(R.id.empty_view);
        rView = findViewById(R.id.rv_recipes);
        swiping =  findViewById(R.id.swiperefresh);
        if (findViewById(R.id.activitymain_large) != null) {
            lLayout = new GridLayoutManager(this, 3);
            mTwoPane=true;
        }
        else {
            lLayout = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            mTwoPane=false;
        }
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
                    rcAdapter = new RecipesAdapter(MainActivity.this, rlist);
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
