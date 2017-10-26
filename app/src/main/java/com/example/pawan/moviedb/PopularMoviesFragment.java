package com.example.pawan.moviedb;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pawan on 26-10-2017.
 */

public class PopularMoviesFragment extends Fragment{

    RecyclerView recyclerView;
    Movies.Movie movie[];
    CustomAdapter adapter;
    View view;
    ProgressBar progressBar;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movieshow, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        fetchPopular();
        return view;

    }

    private void fetchPopular() {
        progressBar=(ProgressBar)view.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        Tmdbservice tmdbservice = retrofit.create(Tmdbservice.class);
        Call<Movies> call = tmdbservice.getPopular();
        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                Movies movies = response.body();
                movie = movies.getResults();
                setmyadapter();
            }

            @Override
            public void onFailure(Call<Movies> call, Throwable t) {

            }
        });
    }

    public void setmyadapter(){
        progressBar.setVisibility(View.GONE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adapter = new CustomAdapter(movie,getContext(),new CustomAdapter.ClickListener() {
                @Override
                public void onItemClick(int position) {
                    Movies.Movie movie1 = movie[position];
                    Snackbar.make(recyclerView,movie1.getTitle(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        }
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager= new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
    }
}
