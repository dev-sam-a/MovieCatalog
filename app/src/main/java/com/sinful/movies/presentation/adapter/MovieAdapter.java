package com.sinful.movies.presentation.adapter;// package ...;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sinful.movies.R;
import com.sinful.movies.domain.model.Movie;

import java.util.Locale;
import java.util.Objects;

public class MovieAdapter extends ListAdapter<Movie, MovieAdapter.MovieViewHolder> {

    public interface OnReachEndListener { void onReachEnd(); }
    public interface OnMovieClickListener { void onMovieClick(Movie movie); }

    private OnReachEndListener onReachEndListener;
    private OnMovieClickListener onMovieClickListener;

    public MovieAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setOnReachEndListener(OnReachEndListener l) { this.onReachEndListener = l; }
    public void setOnMovieClickListener(OnMovieClickListener l) { this.onMovieClickListener = l; }

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Movie>() {
                @Override
                public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.getId() == newItem.getId();
                }

                @Override
                public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
                    return oldItem.getId() == newItem.getId()
                            && oldItem.getYear() == newItem.getYear()
                            && Objects.equals(oldItem.getName(), newItem.getName())
                            && Objects.equals(oldItem.getDescription(), newItem.getDescription())
                            && Double.compare(oldItem.getRating().getKp(), newItem.getRating().getKp()) == 0
                            && Objects.equals(oldItem.getPoster().getUrl(), newItem.getPoster().getUrl());
                }
            };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.movie_item,
                parent,
                false
        );
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);

        Glide.with(holder.itemView)
                .load(movie.getPoster().getUrl())
                .into(holder.imageViewPoster);

        double rating = movie.getRating().getKp();
        int backgroundId;
        if (rating > 7) backgroundId = R.drawable.circle_green;
        else if (rating > 5) backgroundId = R.drawable.circle_orange;
        else backgroundId = R.drawable.circle_red;

        Drawable background = ContextCompat.getDrawable(holder.itemView.getContext(), backgroundId);
        holder.textViewRating.setBackground(background);
        holder.textViewRating.setText(String.format(Locale.US, "%.1f", rating));

        if (position >= getItemCount() - 10 && onReachEndListener != null) {
            onReachEndListener.onReachEnd();
        }

        holder.itemView.setOnClickListener(v -> {
            if (onMovieClickListener != null) onMovieClickListener.onMovieClick(movie);
        });
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        final ImageView imageViewPoster;
        final TextView textViewRating;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewPoster = itemView.findViewById(R.id.imageViewPoster);
            textViewRating = itemView.findViewById(R.id.textViewRating);
        }
    }
}
