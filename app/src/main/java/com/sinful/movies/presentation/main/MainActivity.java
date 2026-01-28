package com.sinful.movies.presentation.main;// package ...;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sinful.movies.R;
import com.sinful.movies.presentation.adapter.MovieAdapter;
import com.sinful.movies.presentation.detail.MovieDetailActivity;
import com.sinful.movies.presentation.favourite.FavouriteMoviesActivity;

public class MainActivity extends AppCompatActivity {

    private MainViewModel viewModel;

    private MovieAdapter moviesAdapter;
    private ProgressBar progressBarLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WindowCompat.setDecorFitsSystemWindows(getWindow(), false);

        progressBarLoading = findViewById(R.id.progressBarLoading);

        RecyclerView recyclerViewMovies = findViewById(R.id.recyclerViewMovies);
        moviesAdapter = new MovieAdapter();
        recyclerViewMovies.setAdapter(moviesAdapter);
        recyclerViewMovies.setLayoutManager(new GridLayoutManager(this, 2));

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        viewModel.getMovies().observe(this, moviesAdapter::submitList);
        viewModel.getIsLoading().observe(this, isLoading ->
                progressBarLoading.setVisibility(isLoading ? View.VISIBLE : View.GONE)
        );

        moviesAdapter.setOnReachEndListener(() -> viewModel.loadMovies());
        moviesAdapter.setOnMovieClickListener(movie -> {
            Intent intent = MovieDetailActivity.newIntent(MainActivity.this, movie);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.itemFavourite) {
            startActivity(FavouriteMoviesActivity.newIntent(this));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
