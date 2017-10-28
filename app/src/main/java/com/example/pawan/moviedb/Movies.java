package com.example.pawan.moviedb;

import java.util.Date;

/**
 * Created by Pawan on 26-10-2017.
 */

public class Movies {

    private Movie results[]=new Movie[20];

    public class Movie {
        int id;
        float vote_average;


        String title;
        String poster_path;
        String backdrop_path;
        String overview;
        String release_date;
        String media_type;
        String name;

        public Movie(int id, float vote_average, String title, String poster_path, String backdrop_path, String overview, String release_date, String media_type, String name) {
            this.id = id;
            this.vote_average = vote_average;
            this.title = title;
            this.poster_path = poster_path;
            this.backdrop_path = backdrop_path;
            this.overview = overview;
            this.release_date = release_date;
            this.media_type = media_type;
            this.name = name;
        }




        public String getMedia_type() {
            return media_type;
        }

        public void setMedia_type(String media_type) {
            this.media_type = media_type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public float getVote_average() {
            return vote_average;
        }

        public void setVote_average(float vote_average) {
            this.vote_average = vote_average;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPoster_path() {
            return poster_path;
        }

        public void setPoster_path(String poster_path) {
            this.poster_path = poster_path;
        }

        public String getBackdrop_path() {
            return backdrop_path;
        }

        public void setBackdrop_path(String backdrop_path) {
            this.backdrop_path = backdrop_path;
        }

        public String getOverview() {
            return overview;
        }

        public void setOverview(String overview) {
            this.overview = overview;
        }

           }

    public Movie[] getResults() {
        return results;
    }

    public void setResults(Movie[] results) {
        this.results = results;
    }
}
