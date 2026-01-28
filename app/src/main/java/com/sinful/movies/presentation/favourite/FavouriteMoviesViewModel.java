package com.sinful.movies.presentation.favourite;// package ...;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.sinful.movies.data.repository.FavouritesRepository;
import com.sinful.movies.data.repository.MovieDatabase;
import com.sinful.movies.domain.model.Movie;

import java.util.List;

public class FavouriteMoviesViewModel extends AndroidViewModel {

    private final FavouritesRepository favouritesRepository;

    public FavouriteMoviesViewModel(@NonNull Application application) {
        super(application);
        favouritesRepository = new FavouritesRepository(
                MovieDatabase.getInstance(application).movieDao()
        );
    }

    public LiveData<List<Movie>> getMovies() {
        return favouritesRepository.getAllFavourites();
    }
}
