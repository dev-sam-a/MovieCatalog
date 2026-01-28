package com.sinful.movies.data.api;// package ...;

import com.sinful.movies.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class ApiFactory {

    private static final String BASE_URL = "https://api.kinopoisk.dev/v1.4/";


    private static final String TOKEN = BuildConfig.API_KEY;

    private static final OkHttpClient CLIENT = new OkHttpClient.Builder()
            .addInterceptor(chain -> {
                Request request = chain.request().newBuilder()
                        .addHeader("X-API-KEY", TOKEN)
                        .build();
                return chain.proceed(request);
            })
            .build();

    private static final Retrofit RETROFIT = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(CLIENT)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();

    public static ApiService apiService() {
        return RETROFIT.create(ApiService.class);
    }

    private ApiFactory() { }
}
