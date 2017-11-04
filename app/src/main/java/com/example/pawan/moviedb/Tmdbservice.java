package com.example.pawan.moviedb;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Pawan on 26-10-2017.
 */

public interface Tmdbservice {
    @GET("movie/popular?api_key=641d26b846df1adc97db0f44718ee254&language=en-US&page")
    Call<Movies> getPopular(@Query("page") int page );

    @GET("movie/upcoming?api_key=641d26b846df1adc97db0f44718ee254&language=en-US&page")
    Call<Movies> getUpcoming(@Query("page") int page);

    @GET("movie/now_playing?api_key=641d26b846df1adc97db0f44718ee254&language=en-US&page")
    Call<Movies> getnowshowing(@Query("page")int page);

    @GET("movie/top_rated?api_key=641d26b846df1adc97db0f44718ee254&language=en-US&page")
    Call<Movies> gettoprated(@Query("page") int page);

    @GET("search/multi?api_key=641d26b846df1adc97db0f44718ee254&language=en-US&page&include_adult=false&query")
    Call<Movies> getsearch(@Query("page") int page,@Query("query") String query);


}
