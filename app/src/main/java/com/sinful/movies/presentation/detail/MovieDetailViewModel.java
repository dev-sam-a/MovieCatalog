package com.sinful.movies.presentation.detail;// package ...;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sinful.movies.data.api.ApiFactory;
import com.sinful.movies.data.repository.FavouritesRepository;
import com.sinful.movies.data.repository.MovieDatabase;
import com.sinful.movies.data.repository.MoviesRepository;
import com.sinful.movies.domain.model.Movie;
import com.sinful.movies.domain.model.Review;
import com.sinful.movies.domain.model.Trailer;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MovieDetailViewModel extends AndroidViewModel {

    private static final String TAG = "MovieDetailViewModel";

    private final MoviesRepository moviesRepository;
    private final FavouritesRepository favouritesRepository;

    private final CompositeDisposable cd = new CompositeDisposable();

    private final MutableLiveData<List<Trailer>> trailers = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> reviews = new MutableLiveData<>();

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(ApiFactory.apiService());
        favouritesRepository = new FavouritesRepository(
                MovieDatabase.getInstance(application).movieDao()
        );
    }

    public LiveData<Movie> getFavouriteMovie(int movieId) {
        return favouritesRepository.getFavourite(movieId);
    }

    public LiveData<List<Trailer>> getTrailers() {
        return trailers;
    }

    public LiveData<List<Review>> getReviews() {
        return reviews;
    }

    public void loadTrailers(int movieId) {
        Disposable d = moviesRepository.loadTrailers(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(trailers::setValue, t -> Log.d(TAG, t.toString()));
        cd.add(d);
    }

    public void loadReviews(int movieId) {
        Disposable d = moviesRepository.loadReviews(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(reviews::setValue, t -> Log.d(TAG, t.toString()));
        cd.add(d);
    }

    public void insertMovie(Movie movie) {
        Disposable d = favouritesRepository.insert(movie)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> {},
                        t -> Log.d(TAG, t.toString())
                );
        cd.add(d);
    }

    public void removeMovie(int movieId) {
        Disposable d = favouritesRepository.remove(movieId)
                .subscribeOn(Schedulers.io())
                .subscribe(
                        () -> {},
                        t -> Log.d(TAG, t.toString())
                );
        cd.add(d);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        cd.dispose();
    }
}
