package com.example.pawan.moviedb;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Pawan on 26-10-2017.
 */

public interface Tmdbservice {
    @GET("movie/popular?api_key=641d26b846df1adc97db0f44718ee254")
    Call<Movies> getPopular();
}
