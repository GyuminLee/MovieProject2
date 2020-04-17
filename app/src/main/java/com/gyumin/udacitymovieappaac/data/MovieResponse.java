package com.gyumin.udacitymovieappaac.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class MovieResponse {
    @SerializedName("results")
    public ArrayList<Movie> results;

    public MovieResponse(ArrayList<Movie> results) {
        this.results = results;
    }

    public ArrayList<Movie> getResults() {
        return results;
    }

    public void setResults(ArrayList<Movie> results) {
        this.results = results;
    }

    public void addMovie(Movie movie) {
        results.add(movie);
    }

}