package com.sinful.movies.presentation.adapter;// package ...;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.sinful.movies.R;
import com.sinful.movies.domain.model.Review;

import java.util.Objects;

public class ReviewsAdapter extends ListAdapter<Review, ReviewsAdapter.ReviewViewHolder> {

    private static final String TYPE_POSITIVE = "Позитивный";
    private static final String TYPE_NEGATIVE = "Негативный";

    public ReviewsAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Review> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Review>() {
                @Override
                public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
                    // Если у Review нет id, используем устойчивую комбинацию (не идеально, но лучше чем notifyDataSetChanged)
                    return Objects.equals(oldItem.getAuthor(), newItem.getAuthor())
                            && Objects.equals(oldItem.getReview(), newItem.getReview())
                            && Objects.equals(oldItem.getType(), newItem.getType());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
                    return Objects.equals(oldItem.getAuthor(), newItem.getAuthor())
                            && Objects.equals(oldItem.getReview(), newItem.getReview())
                            && Objects.equals(oldItem.getType(), newItem.getType());
                }
            };

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.review_item,
                parent,
                false
        );
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = getItem(position);
        holder.textViewAuthor.setText(review.getAuthor());
        holder.textViewReview.setText(review.getReview());

        String type = review.getType();
        int colorResId = R.color.light_gray;
        if (TYPE_NEGATIVE.equals(type)) {
            colorResId = R.color.light_red;
        } else if (TYPE_POSITIVE.equals(type)) {
            colorResId = R.color.light_green;
        }
        int color = ContextCompat.getColor(holder.itemView.getContext(), colorResId);
        holder.linearLayoutReview.setBackgroundColor(color);
    }

    static class ReviewViewHolder extends RecyclerView.ViewHolder {

        final LinearLayout linearLayoutReview;
        final TextView textViewAuthor;
        final TextView textViewReview;

        ReviewViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayoutReview = itemView.findViewById(R.id.linearLayoutReview);
            textViewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textViewReview = itemView.findViewById(R.id.textViewReview);
        }
    }
}
