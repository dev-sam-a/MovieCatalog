package com.sinful.movies.data.repository;

import androidx.lifecycle.LiveData;

import com.sinful.movies.data.db.MovieDao;
import com.sinful.movies.domain.model.Movie;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

public class FavouritesRepository {

    private final MovieDao movieDao;

    public FavouritesRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public LiveData<List<Movie>> getAllFavourites() {
        return movieDao.getAllFavouriteMovies();
    }

    public LiveData<Movie> getFavourite(int movieId) {
        return movieDao.getFavouriteMovie(movieId);
    }

    public Completable insert(Movie movie) {
        return movieDao.insertMovie(movie);
    }

    public Completable remove(int movieId) {
        return movieDao.removeMovie(movieId);
    }
}
