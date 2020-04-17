package com.gyumin.udacitymovieappaac.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.gyumin.udacitymovieappaac.R;
import com.gyumin.udacitymovieappaac.adapter.MovieAdapter;
import com.gyumin.udacitymovieappaac.data.FavoriteMovie;
import com.gyumin.udacitymovieappaac.data.FavoriteMovieDatabase;
import com.gyumin.udacitymovieappaac.data.Movie;
import com.gyumin.udacitymovieappaac.data.MovieResponse;
import com.gyumin.udacitymovieappaac.utils.Constants;
import com.gyumin.udacitymovieappaac.viewmodel.MainViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener {

    // Constant for logging
    private static final String TAG = MainActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private MovieAdapter movieAdapter;
    private FavoriteMovieDatabase favoriteMovieDatabase;
    private ArrayList<Movie> movieArrayList = new ArrayList<>();
    private MainViewModel mainViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.movie_rv);

        setupViewModel();
        setupRecyclerView();
        favoriteMovieDatabase = FavoriteMovieDatabase.getInstance(getApplicationContext());
    }

    private void setupRecyclerView() {
        int columnNum = 2;
        recyclerView.setLayoutManager(new GridLayoutManager(this, columnNum));
        movieAdapter = new MovieAdapter(this, movieArrayList);
        movieAdapter.setMovieClickListener(this);
        recyclerView.setAdapter(movieAdapter);
    }

    private void setupViewModel() {
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        mainViewModel.getPopularMovies();
        mainViewModel.getMovieRepository().observe(this, new Observer<MovieResponse>() {
            @Override
            public void onChanged(MovieResponse movieResponse) {
                if(movieResponse.getResults() != null) {
                    ArrayList<Movie> movieList = movieResponse.getResults();
                    movieArrayList.addAll(movieList);
                    movieAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public void onMovieClick(View view, int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        int movieId = movieArrayList.get(position).getId();
        intent.putExtra(Constants.MOVIE_ID, movieId);
        startActivity(intent);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_popular :
                movieAdapter.clearData();
                mainViewModel.getPopularMovies();
                mainViewModel.getMovieRepository().observe(this, new Observer<MovieResponse>() {
                    @Override
                    public void onChanged(MovieResponse movieResponse) {
                        ArrayList<Movie> movieList = movieResponse.getResults();
                        movieArrayList.addAll(movieList);
                        movieAdapter.notifyDataSetChanged();
                    }
                });
                return true;

            case R.id.action_rate :
                movieAdapter.clearData();
                mainViewModel.getTopRatedMovies();
                mainViewModel.getMovieRepository().observe(this, new Observer<MovieResponse>() {
                    @Override
                    public void onChanged(MovieResponse movieResponse) {
                        ArrayList<Movie> movieList = movieResponse.getResults();
                        movieArrayList.addAll(movieList);
                        movieAdapter.notifyDataSetChanged();
                    }
                });
                return true;

            case R.id.action_favorite :
                movieAdapter.clearData();
                Executor myExecutor = Executors.newSingleThreadExecutor();

                mainViewModel.getFavoriteMovies(favoriteMovieDatabase.favoriteMovieDao().getFavoriteMovies());
                mainViewModel.getFavoriteMovieRepository().observe(this, new Observer<List<FavoriteMovie>>() {
                    @Override
                    public void onChanged(List<FavoriteMovie> favoriteMovies) {
                        System.out.println("Favorite List");
                        ArrayList<Movie> movieList = new ArrayList<>();
                        for(FavoriteMovie favoriteMovie : favoriteMovies) {
                            System.out.println(favoriteMovie.getTitle());
                            movieList.add(new Movie(favoriteMovie.getId(), favoriteMovie.getTitle(), favoriteMovie.getImage()));
                        }
                        movieArrayList.clear();
                        movieArrayList.addAll(movieList);
                        movieAdapter.notifyDataSetChanged();
                    }
                });

                return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
