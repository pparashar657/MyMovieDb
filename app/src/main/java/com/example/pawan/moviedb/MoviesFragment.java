package com.example.pawan.moviedb;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pawan on 26-10-2017.
 */

public class MoviesFragment extends Fragment{

    RecyclerView recyclerView;
    Movies.Movie movie[];
    MovieCustomAdapter adapter;
    View view;
    ProgressBar progressBar;
    String type;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movieshow, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        fetchPopular();
        return view;

    }

    private void fetchPopular() {
        progressBar=(ProgressBar)view.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        Tmdbservice tmdbservice = retrofit.create(Tmdbservice.class);
        Call<Movies> call=null;
        Log.i("typeeee",type);
        switch(type){
            case Constants.movie_popular: call = tmdbservice.getPopular();
                                            break;
            case Constants.movie_upcoming: call = tmdbservice.getUpcoming();
                                        break;
            case Constants.movie_nowshowing: call = tmdbservice.getnowshowing();
                break;
            case Constants.movie_toprated: call = tmdbservice.gettoprated();
                break;

        }

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
            adapter = new MovieCustomAdapter(movie,getContext(),new MovieCustomAdapter.ClickListener() {
                @Override
                public void onItemClick(int position) {
                    Movies.Movie movie1 = movie[position];
                    Snackbar.make(recyclerView,movie1.getTitle(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        }
        recyclerView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager= new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter.notifyDataSetChanged();
    }
}
