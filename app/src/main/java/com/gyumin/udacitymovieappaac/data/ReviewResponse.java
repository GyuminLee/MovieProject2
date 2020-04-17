package com.gyumin.udacitymovieappaac.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ReviewResponse {

    @SerializedName("results")
    public ArrayList<Review> results;

    public ReviewResponse(ArrayList<Review> results) {
        this.results = results;
    }

    public void setResults(ArrayList<Review> results) {
        this.results = results;
    }

    public ArrayList<Review> getResults() {
        return results;
    }
}
