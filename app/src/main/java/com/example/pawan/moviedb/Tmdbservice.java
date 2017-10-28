package com.example.pawan.moviedb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Pawan on 26-10-2017.
 */

public interface Tmdbservice {
    @GET("movie/popular?api_key=641d26b846df1adc97db0f44718ee254")
    Call<Movies> getPopular();

    @GET("movie/upcoming?api_key=641d26b846df1adc97db0f44718ee254&language=en-US&page=2")
    Call<Movies> getUpcoming();

    @GET("movie/now_playing?api_key=641d26b846df1adc97db0f44718ee254&language=en-US&page=1")
    Call<Movies> getnowshowing();

    @GET("movie/top_rated?api_key=641d26b846df1adc97db0f44718ee254&language=en-US&page=1")
    Call<Movies> gettoprated();

    @GET("search/multi?api_key=641d26b846df1adc97db0f44718ee254&language=en-US&page=1&include_adult=false&query")
    Call<Movies> getsearch(@Query("query") String query);


}
