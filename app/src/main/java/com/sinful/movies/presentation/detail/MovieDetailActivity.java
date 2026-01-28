package com.sinful.movies.presentation.detail;// package ...;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sinful.movies.R;
import com.sinful.movies.domain.model.Movie;
import com.sinful.movies.presentation.adapter.ReviewsAdapter;
import com.sinful.movies.presentation.adapter.TrailersAdapter;

import java.util.concurrent.atomic.AtomicBoolean;

public class MovieDetailActivity extends AppCompatActivity {

    private static final String EXTRA_MOVIE = "movie";

    private MovieDetailViewModel viewModel;

    private ImageView imageViewPoster;
    private ImageView imageViewFavourite;
    private TextView textViewTitle;
    private TextView textViewYear;
    private TextView textViewDescription;

    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        viewModel = new ViewModelProvider(this).get(MovieDetailViewModel.class);

        initViews();

        trailersAdapter = new TrailersAdapter();
        reviewsAdapter = new ReviewsAdapter();

        RecyclerView recyclerViewTrailers = findViewById(R.id.recyclerViewTrailers);
        RecyclerView recyclerViewReviews = findViewById(R.id.recyclerViewReviews);
        recyclerViewTrailers.setAdapter(trailersAdapter);
        recyclerViewReviews.setAdapter(reviewsAdapter);

        Movie movie = (Movie) getIntent().getSerializableExtra(EXTRA_MOVIE);

        Glide.with(this)
                .load(movie.getPoster().getUrl())
                .into(imageViewPoster);

        textViewTitle.setText(movie.getName());
        textViewYear.setText(String.valueOf(movie.getYear()));
        textViewDescription.setText(movie.getDescription());

        // Trailers
        trailersAdapter.setOnTrailerClickListener(trailer -> {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getUrl()));
            intent.setPackage("com.android.chrome");
            try {
                startActivity(intent);
            } catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getUrl())));
            }
        });

        viewModel.getTrailers().observe(this, trailersAdapter::submitList);
        viewModel.loadTrailers(movie.getId());

        // Reviews
        viewModel.getReviews().observe(this, reviewsAdapter::submitList);
        viewModel.loadReviews(movie.getId());

        // Favourite toggling (one listener, flag changes from observer)
        Drawable favouriteOff = ContextCompat.getDrawable(this, R.drawable.fav_icon_fill_0);
        Drawable favouriteOn = ContextCompat.getDrawable(this, R.drawable.fav_icon_fill_1);
        AtomicBoolean isFav = new AtomicBoolean(false);

        imageViewFavourite.setOnClickListener(v -> {
            if (isFav.get()) viewModel.removeMovie(movie.getId());
            else viewModel.insertMovie(movie);
        });

        viewModel.getFavouriteMovie(movie.getId()).observe(this, movieFromDb -> {
            boolean fav = movieFromDb != null;
            isFav.set(fav);
            imageViewFavourite.setImageDrawable(fav ? favouriteOn : favouriteOff);
        });
    }

    private void initViews() {
        imageViewPoster = findViewById(R.id.imageViewPoster);
        imageViewFavourite = findViewById(R.id.imageViewFavourite);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewYear = findViewById(R.id.textViewYear);
        textViewDescription = findViewById(R.id.textViewDescription);
    }

    public static Intent newIntent(Context context, Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(EXTRA_MOVIE, movie);
        return intent;
    }
}
