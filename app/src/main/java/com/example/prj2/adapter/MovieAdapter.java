package com.example.prj2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj2.R;
import com.example.prj2.data.entity.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onMovieClick(Movie movie);
    }

    public MovieAdapter(Context context, List<Movie> movieList, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvGenre.setText(movie.getGenre());
        holder.tvDuration.setText(movie.getDuration() + " phút");
        holder.tvRating.setText("★ " + movie.getRating());
        holder.tvYear.setText(String.valueOf(movie.getReleaseYear()));

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onMovieClick(movie);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public void updateList(List<Movie> newList) {
        this.movieList = newList;
        notifyDataSetChanged();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTitle;
        TextView tvGenre;
        TextView tvDuration;
        TextView tvRating;
        TextView tvYear;

        MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_movie);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvGenre = itemView.findViewById(R.id.tv_movie_genre);
            tvDuration = itemView.findViewById(R.id.tv_movie_duration);
            tvRating = itemView.findViewById(R.id.tv_movie_rating);
            tvYear = itemView.findViewById(R.id.tv_movie_year);
        }
    }
}
