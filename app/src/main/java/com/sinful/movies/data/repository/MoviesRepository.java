package com.sinful.movies.data.repository;// package ...;

import com.sinful.movies.data.api.ApiService;
import com.sinful.movies.data.dto.MovieResponse;
import com.sinful.movies.data.dto.ReviewResponse;
import com.sinful.movies.domain.model.Movie;
import com.sinful.movies.domain.model.Review;
import com.sinful.movies.domain.model.Trailer;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class MoviesRepository {

    private static final int DEFAULT_LIMIT = 20;
    private static final String DEFAULT_SORT_FIELD = "votes.kp";
    private static final int DEFAULT_SORT_TYPE = -1;
    private static final String DEFAULT_RATING_RANGE = "4-10";

    private static final int DEFAULT_REVIEWS_PAGE = 1;
    private static final int DEFAULT_REVIEWS_LIMIT = 10;

    private final ApiService api;

    public MoviesRepository(ApiService api) {
        this.api = api;
    }

    public Single<List<Movie>> loadPopularMovies(int page) {
        return api.loadMovie(
                page,
                DEFAULT_LIMIT,
                DEFAULT_SORT_FIELD,
                DEFAULT_SORT_TYPE,
                DEFAULT_RATING_RANGE
        ).map(MovieResponse::getMovies);
    }

    public Single<List<Trailer>> loadTrailers(int movieId) {
        return api.loadTrailers(movieId)
                .map(r -> r.getTrailersList().getTrailers());
    }

    public Single<List<Review>> loadReviews(int movieId) {
        return api.loadReviews(movieId, DEFAULT_REVIEWS_PAGE, DEFAULT_REVIEWS_LIMIT)
                .map(ReviewResponse::getReviews);
    }
}
