package com.example.movies.domain.repository;

import androidx.lifecycle.LiveData;

import com.example.movies.domain.model.Movie;
import com.example.movies.domain.model.Review;
import com.example.movies.domain.model.Trailer;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface MovieRepository {
    Single<List<Movie>> loadMovies(int page);
    Single<List<Trailer>> loadTrailers(int movieId);
    Single<List<Review>> loadReviews(int movieId);

    LiveData<Movie> getFavouriteMovie(int movieId);
    LiveData<List<Movie>> getFavouriteMovies();

    Completable addToFavourite(Movie movie);
    Completable removeFromFavourite(int movieId);
}

