package com.sinful.movies.presentation.adapter;// package ...;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.sinful.movies.R;
import com.sinful.movies.domain.model.Trailer;

import java.util.Objects;

public class TrailersAdapter extends ListAdapter<Trailer, TrailersAdapter.TrailersViewHolder> {

    public interface OnTrailerClickListener { void onTrailerClick(Trailer trailer); }

    private OnTrailerClickListener onTrailerClickListener;

    public TrailersAdapter() {
        super(DIFF_CALLBACK);
    }

    public void setOnTrailerClickListener(OnTrailerClickListener l) {
        this.onTrailerClickListener = l;
    }

    private static final DiffUtil.ItemCallback<Trailer> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Trailer>() {
                @Override
                public boolean areItemsTheSame(@NonNull Trailer oldItem, @NonNull Trailer newItem) {
                    return Objects.equals(oldItem.getUrl(), newItem.getUrl());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Trailer oldItem, @NonNull Trailer newItem) {
                    return Objects.equals(oldItem.getName(), newItem.getName())
                            && Objects.equals(oldItem.getUrl(), newItem.getUrl());
                }
            };

    @NonNull
    @Override
    public TrailersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.trailer_item,
                parent,
                false
        );
        return new TrailersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersViewHolder holder, int position) {
        Trailer trailer = getItem(position);
        holder.textViewTrailerName.setText(trailer.getName());
        holder.itemView.setOnClickListener(v -> {
            if (onTrailerClickListener != null) onTrailerClickListener.onTrailerClick(trailer);
        });
    }

    static class TrailersViewHolder extends RecyclerView.ViewHolder {

        final TextView textViewTrailerName;

        TrailersViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTrailerName = itemView.findViewById(R.id.textViewTrailerName);
        }
    }
}
