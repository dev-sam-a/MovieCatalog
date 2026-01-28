package com.example.movies.presentation.favourite;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movies.presentation.adapter.MovieAdapter;
import com.example.movies.R;
import com.example.movies.domain.model.Movie;
import com.example.movies.presentation.detail.MovieDetailActivity;

import java.util.List;

public class FavouriteMoviesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_movies);

        RecyclerView recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        MovieAdapter movieAdapter = new MovieAdapter();
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerViewMovies.setAdapter(movieAdapter);
        movieAdapter.setOnMovieClickListener(new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
                Intent intent = MovieDetailActivity.newIntent(
                        FavouriteMoviesActivity.this,
                        movie
                );
                startActivity(intent);
            }
        });

        FavouriteMoviesViewModel viewModel = new ViewModelProvider(this).get(
                FavouriteMoviesViewModel.class
        );
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                movieAdapter.setMovies(movies);
            }
        });

    }

    public static Intent newIntent(Context context) {
        return new Intent(context, FavouriteMoviesActivity.class);
    }
}