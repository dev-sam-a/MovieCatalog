package com.sinful.movies.data.api;// package ...;

import com.sinful.movies.data.dto.MovieResponse;
import com.sinful.movies.data.dto.ReviewResponse;
import com.sinful.movies.data.dto.TrailerResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    @GET("movie")
    Single<MovieResponse> loadMovie(
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("sortField") String sortField,
            @Query("sortType") int sortType,
            @Query("rating.kp") String ratingRange
    );

    @GET("movie/{id}")
    Single<TrailerResponse> loadTrailers(@Path("id") int id);

    @GET("review")
    Single<ReviewResponse> loadReviews(
            @Query("movieId") int movieId,
            @Query("page") int page,
            @Query("limit") int limit
    );
}
