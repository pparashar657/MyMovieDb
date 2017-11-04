package com.example.pawan.moviedb;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Collection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Pawan on 26-10-2017.
 */

public class MoviesFragment extends Fragment{

    int page;
    private boolean pagesOver = false;
    private boolean loading = true;
    RecyclerView recyclerView;
    ArrayList<Movies.Movie> mresults;
    MovieCustomAdapter adapter;
    View view;
    private int previousTotal = 0;
    ProgressBar progressBar;
    String type;
    static int temp =0;
    int visibleItemCount = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.movieshow, container, false);
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerview);
        mresults = new ArrayList<>();
        Bundle bundle = getArguments();
        type = bundle.getString("type");
        page = 1;
        temp = 0;
        progressBar=(ProgressBar)view.findViewById(R.id.progress);
        progressBar.setVisibility(View.VISIBLE);
        setmyadapter();
        return view;

    }
    private void fetchPopular() {
        if(pagesOver) {
            return;
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://api.themoviedb.org/3/").addConverterFactory(GsonConverterFactory.create()).build();
        Tmdbservice tmdbservice = retrofit.create(Tmdbservice.class);
        Call<Movies> call=null;
        Log.i("typeeee",type);
        switch(type){
            case Constants.movie_popular: call = tmdbservice.getPopular(page);
                                            break;
            case Constants.movie_upcoming: call = tmdbservice.getUpcoming(page);
                                        break;
            case Constants.movie_nowshowing: call = tmdbservice.getnowshowing(page);
                break;
            case Constants.movie_toprated: call = tmdbservice.gettoprated(page);
                break;

        }

        call.enqueue(new Callback<Movies>() {
            @Override
            public void onResponse(Call<Movies> call, Response<Movies> response) {
                progressBar.setVisibility(View.GONE);
                if (response.body() == null) return;
                if (response.body().getResults() == null) return;
                for (Movies.Movie movie : response.body().getResults()) {
                    if (movie != null && movie.getTitle() != null && movie.getPoster_path() != null)
                        mresults.add(movie);
                }
                adapter.notifyDataSetChanged();
                page++;
                }
            @Override
            public void onFailure(Call<Movies> call, Throwable t) {
            }
        });
    }

    public void setmyadapter(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            adapter = new MovieCustomAdapter(mresults,getContext(),new MovieCustomAdapter.ClickListener() {
                @Override
                public void onItemClick(int position) {
                    Movies.Movie movie1 = mresults.get(position);
                    Snackbar.make(recyclerView,movie1.getTitle(), BaseTransientBottomBar.LENGTH_LONG).show();
                }
            });
        }
        recyclerView.setAdapter(adapter);
         final RecyclerView.LayoutManager layoutManager= new StaggeredGridLayoutManager(2,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();

                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && ((totalItemCount - visibleItemCount) <= (temp - 5))) {
                    fetchPopular();
                    loading = true;
                }
            }
        });
        fetchPopular();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        mresults.clear();
        super.onDestroy();
    }
}
