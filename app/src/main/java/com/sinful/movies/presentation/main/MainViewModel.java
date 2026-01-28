package com.sinful.movies.presentation.main;// package ...;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sinful.movies.data.api.ApiFactory;
import com.sinful.movies.data.repository.MoviesRepository;
import com.sinful.movies.domain.model.Movie;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";

    private final MoviesRepository moviesRepository;

    private final MutableLiveData<List<Movie>> movies = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);

    private final CompositeDisposable cd = new CompositeDisposable();

    private int page = 1;

    public MainViewModel(@NonNull Application application) {
        super(application);
        moviesRepository = new MoviesRepository(ApiFactory.apiService());
        loadMovies();
    }

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loadMovies() {
        Boolean loading = isLoading.getValue();
        if (loading != null && loading) return;

        Disposable d = moviesRepository.loadPopularMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> isLoading.setValue(true))
                .doFinally(() -> isLoading.setValue(false))
                .subscribe(loaded -> {
                    List<Movie> current = movies.getValue();
                    List<Movie> merged = new ArrayList<>(current != null ? current : new ArrayList<>());
                    merged.addAll(loaded);
                    movies.setValue(merged);
                    Log.d(TAG, "Loaded: " + page);
                    page++;
                }, throwable -> Log.d(TAG, throwable.toString()));

        cd.add(d);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        cd.dispose();
    }
}
