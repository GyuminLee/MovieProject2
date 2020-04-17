package com.gyumin.udacitymovieappaac.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteMovieDao {

    @Query("SELECT * FROM favorite_movie_table")
    LiveData<List<FavoriteMovie>> getFavoriteMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(FavoriteMovie movie);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateFavoriteMovie(FavoriteMovie movie);

    @Delete
    void deleteMovie(FavoriteMovie movie);

    @Query("SELECT * FROM favorite_movie_table WHERE id = :id")
    LiveData<FavoriteMovie> loadFavoriteMovieById(int id);
}
