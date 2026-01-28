package com.sinful.movies.presentation.favourite;// package ...;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sinful.movies.R;
import com.sinful.movies.presentation.adapter.MovieAdapter;
import com.sinful.movies.presentation.detail.MovieDetailActivity;

public class FavouriteMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        RecyclerView recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        MovieAdapter movieAdapter = new MovieAdapter();
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewMovies.setAdapter(movieAdapter);

        movieAdapter.setOnMovieClickListener(movie -> {
            Intent intent = MovieDetailActivity.newIntent(FavouriteMoviesActivity.this, movie);
            startActivity(intent);
        });

        FavouriteMoviesViewModel viewModel = new ViewModelProvider(this).get(FavouriteMoviesViewModel.class);
        viewModel.getMovies().observe(this, movieAdapter::submitList);
    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteMoviesActivity.class);
    }
}
