package com.gyumin.udacitymovieappaac.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gyumin.udacitymovieappaac.data.FavoriteMovie;
import com.gyumin.udacitymovieappaac.data.FavoriteMovieDao;
import com.gyumin.udacitymovieappaac.data.Movie;
import com.gyumin.udacitymovieappaac.data.MovieRepository;
import com.gyumin.udacitymovieappaac.data.ReviewResponse;
import com.gyumin.udacitymovieappaac.data.VideoResponse;
import com.gyumin.udacitymovieappaac.utils.Constants;

public class DetailViewModel extends ViewModel {

    private LiveData<FavoriteMovie> favoriteMovie;
    private MutableLiveData<Movie> movie;
    private MutableLiveData<ReviewResponse> reviews;
    private MutableLiveData<VideoResponse> videos;
    private MovieRepository movieRepository;

    public void getDetail(int id) {
        movieRepository = MovieRepository.getInstance();
        movie = movieRepository.getMovie(Constants.API_KEY, id);
    }

    public void getReviews(int id) {
        Log.d("DebugLog", "Getting Reviews");

        movieRepository = MovieRepository.getInstance();
        reviews = movieRepository.getReviews(Constants.API_KEY, id);
    }

    public void getVideos(int id) {
        Log.d("DebugLog", "Getting Videos");

        movieRepository = MovieRepository.getInstance();
        videos = movieRepository.getVideos(Constants.API_KEY, id);
    }

    public void getFavoriteMovieById(int id, FavoriteMovieDao dao) {
        movieRepository = MovieRepository.getInstance();

        favoriteMovie = dao.loadFavoriteMovieById(id);
    }

    public LiveData<Movie> getMovieDetailRepository() {
        return movie;
    }

    public LiveData<ReviewResponse> getReviewRepository() {
        return reviews;
    }

    public LiveData<VideoResponse> getVideoRepository() {
        return videos;
    }

    public LiveData<FavoriteMovie> getFavoriteRepository() {
        return favoriteMovie;
    }

}
