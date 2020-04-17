package com.gyumin.udacitymovieappaac.data;

import android.os.Parcel;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gyumin.udacitymovieappaac.network.MovieApi;
import com.gyumin.udacitymovieappaac.network.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieRepository {
    private static MovieRepository movieRepository;
    public static MovieRepository getInstance() {
        if (movieRepository == null) {
            movieRepository = new MovieRepository();
        }
        return movieRepository;
    }

    private MovieApi movieApi;

    public MovieRepository() {
        movieApi = RetrofitService.createService(MovieApi.class);
    }

   public MutableLiveData<MovieResponse> getPopularMovies(String apiKey) {
        final MutableLiveData<MovieResponse> movies = new MutableLiveData<>();
       Log.d("DebugLog", "Repository Popular Movie");

       movieApi.getPopularMovies(apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    movies.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                movies.setValue(null);
                Log.d("DebugLog", "fail to get response from server");
            }
        });
        return movies;
   }

    public MutableLiveData<MovieResponse> getTopRatedMovies(String apiKey) {
        final MutableLiveData<MovieResponse> movies = new MutableLiveData<>();
        Log.d("DebugLog", "Repository Top Rated Movie");

        movieApi.getTopRatedMovies(apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful()) {
                    movies.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                movies.setValue(null);
                Log.d("DebugLog", "fail to get response from server");
            }
        });
        return movies;
    }

    public MutableLiveData<ReviewResponse> getReviews(String apiKey, int id) {
        final MutableLiveData<ReviewResponse> reviews = new MutableLiveData<>();
        Log.d("DebugLog", "Repository Review");

        movieApi.getReviews(id, apiKey).enqueue(new Callback<ReviewResponse>() {

            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                if (response.isSuccessful()) {
                    reviews.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.d("DebugLog", "fail to get response from server");
                reviews.setValue(null);
            }
        });
        return reviews;
    }

    public MutableLiveData<VideoResponse> getVideos(String apiKey, int id) {
        final MutableLiveData<VideoResponse> videos = new MutableLiveData<>();
        Log.d("DebugLog", "Repository Trailers");

        movieApi.getVideos(id, apiKey).enqueue(new Callback<VideoResponse>() {
            @Override
            public void onResponse(Call<VideoResponse> call, Response<VideoResponse> response) {
                if (response.isSuccessful()) {
                    videos.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<VideoResponse> call, Throwable t) {
                Log.d("DebugLog", "fail to get response from server");
                videos.setValue(null);
            }
        });

        return videos;
    }

    public MutableLiveData<ArrayList<Movie>> getFavoriteMovies(String apiKey, List<FavoriteMovie> favoriteMovies) {
        final MutableLiveData<ArrayList<Movie>> result = new MutableLiveData<>();
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        if(favoriteMovies != null) {
            for (FavoriteMovie favoriteMovie : favoriteMovies) {
                movieArrayList.add(getMovie(apiKey, favoriteMovie.getId()).getValue());
            }
        }
        MovieResponse movieResponse = new MovieResponse(movieArrayList);
        result.setValue(movieArrayList);
        return result;
    }

    public MutableLiveData<Movie> getMovie(String apiKey, int id) {
        Log.d("DebugLog", "Repository Movie with id");
        final MutableLiveData<Movie> result = new MutableLiveData<>();

        movieApi.getMovie(id, apiKey).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if(response.isSuccessful()) {
                    result.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                result.setValue(null);
            }
        });
        return result;
    }
}
