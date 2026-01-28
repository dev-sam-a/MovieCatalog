package com.sinful.movies.data.repository;// package ...;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.sinful.movies.data.db.MovieDao;
import com.sinful.movies.domain.model.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    private static final String DB_NAME = "movie.db";
    private static volatile MovieDatabase instance;

    public static MovieDatabase getInstance(Application application) {
        if (instance == null) {
            synchronized (MovieDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(application, MovieDatabase.class, DB_NAME)
                            .build();
                }
            }
        }
        return instance;
    }

    public abstract MovieDao movieDao();
}
