package com.example.pawan.moviedb;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    ProgressBar progressBar;
    RecyclerView recyclerView;
    Movies.Movie[] movie;
    String query;
    SearchResultAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        query = getIntent().getStringExtra("query");
        progressBar = (ProgressBar)findViewById(R.id.searchprogress);
        recyclerView = (RecyclerView) findViewById(R.id.searchlist);
        fetchSearch();

    }


    private void fetchSearch() {
        progressBar = (ProgressBar)findViewById(R.id.searchprogress);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        Tmdbservice tmdbservice = retrofit.create(Tmdbservice.class);
        Call<Movies> call = null;
        call = tmdbservice.getsearch(query);
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                Movies movies = response.body();
                movie = movies.getResults();
                setmyadapter();
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
                Snackbar.make(recyclerView,"URL ERROR OCCURED",BaseTransientBottomBar.LENGTH_INDEFINITE).show();
            }
        });

    }

    public void setmyadapter(){
        progressBar.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adapter = new SearchResultAdapter(this,movie);
        }
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager= new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
    }

    }
