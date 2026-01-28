package com.example.movies.presentation.favourite;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.movies.data.db.MovieDao;
import com.example.movies.data.db.MovieDatabase;
import com.example.movies.domain.model.Movie;

import java.util.List;

public class FavouriteMoviesViewModel extends AndroidViewModel {

    private final MovieDao movieDao;

    public FavouriteMoviesViewModel(@NonNull Application application) {
        super(application);
        movieDao = MovieDatabase.getInstance(application).movieDao();
    }

    public LiveData<List<Movie>> getMovies(){
        return movieDao.getAllFavouriteMovies();
    }
}
