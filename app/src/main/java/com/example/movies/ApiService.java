package com.example.movies;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {

    String TOKEN = "DFBJ9NE-GJTMH96-QS1020K-756S0SD";

    @GET("movie?limit=20&sortField=votes.kp&sortType=-1&rating.kp=4-10")
    @Headers("X-API-KEY:" + TOKEN)
    Single<MovieResponse> loadMovie(@Query("page") int page);

    @GET("movie/{id}")
    @Headers("X-API-KEY:" + TOKEN)
    Single<TrailerResponse> loadTrailers(@Path("id") int id);

    @GET("review?page=1&limit=10")
    @Headers("X-API-KEY:" + TOKEN)
    Single<ReviewResponse> loadReviews(@Query("movieId") int id);
}
