package com.gyumin.udacitymovieappaac.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class VideoResponse {
    @SerializedName("results")
    public ArrayList<Video> results;

    public VideoResponse(ArrayList<Video> results) {
        this.results = results;
    }

    public void setResults(ArrayList<Video> results) {
        this.results = results;
    }

    public ArrayList<Video> getResults() {
        return results;
    }
}
