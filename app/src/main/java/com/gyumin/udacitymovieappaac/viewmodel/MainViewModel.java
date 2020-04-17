package com.gyumin.udacitymovieappaac.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gyumin.udacitymovieappaac.data.FavoriteMovie;
import com.gyumin.udacitymovieappaac.data.FavoriteMovieDao;
import com.gyumin.udacitymovieappaac.data.Movie;
import com.gyumin.udacitymovieappaac.data.MovieRepository;
import com.gyumin.udacitymovieappaac.data.MovieResponse;
import com.gyumin.udacitymovieappaac.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends ViewModel {

    private MutableLiveData<MovieResponse> movies;
    private LiveData<List<FavoriteMovie>> favoriteMovies;
    private MovieRepository movieRepository;

    public void getPopularMovies() {
        Log.d("DebugLog", "Getting Popular");

        movieRepository = MovieRepository.getInstance();
        movies = movieRepository.getPopularMovies(Constants.API_KEY);
    }
    public void getTopRatedMovies() {
        Log.d("DebugLog", "Getting TopRated");

        movieRepository = MovieRepository.getInstance();
        movies = movieRepository.getTopRatedMovies(Constants.API_KEY);
    }

    public void getFavoriteMovies(LiveData<List<FavoriteMovie>> favoriteMovies) {
        Log.d("DebugLog", "Getting FavoriteMovies");

        movieRepository = MovieRepository.getInstance();
        this.favoriteMovies = favoriteMovies;
    }

    public LiveData<MovieResponse> getMovieRepository() {
        return movies;
    }
    public LiveData<List<FavoriteMovie>> getFavoriteMovieRepository() {
        return favoriteMovies;
    }


}
