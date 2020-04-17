package com.gyumin.udacitymovieappaac.network;

import com.gyumin.udacitymovieappaac.data.Movie;
import com.gyumin.udacitymovieappaac.data.MovieResponse;
import com.gyumin.udacitymovieappaac.data.ReviewResponse;
import com.gyumin.udacitymovieappaac.data.VideoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieApi {
    @GET("popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("{id}/videos")
    Call<VideoResponse> getVideos(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("{id}/reviews")
    Call<ReviewResponse> getReviews(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("{id}")
    Call<Movie> getMovie(@Path("id") int id, @Query("api_key") String apiKey);


}
