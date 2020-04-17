package com.gyumin.udacitymovieappaac.data;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMovie.class}, version = 1, exportSchema = false)
public abstract class FavoriteMovieDatabase extends RoomDatabase {

    private static final String DB_NAME = "favoriteMovie";
    private static FavoriteMovieDatabase INSTANCE;

    public abstract FavoriteMovieDao favoriteMovieDao();

    public static FavoriteMovieDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context,
                    FavoriteMovieDatabase.class, FavoriteMovieDatabase.DB_NAME)
                    .build();
        }
        return INSTANCE;
    }

}
