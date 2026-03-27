package com.example.prj2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj2.R;
import com.example.prj2.data.entity.Movie;
import com.example.prj2.data.entity.Showtime;
import com.example.prj2.data.entity.Theater;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ShowtimeViewHolder> {

    private Context context;
    private List<Showtime> showtimeList;
    private List<Movie> movieList;
    private List<Theater> theaterList;
    private OnBookClickListener listener;

    public interface OnBookClickListener {
        void onBookClick(Showtime showtime);
    }

    public ShowtimeAdapter(Context context, List<Showtime> showtimeList,
                           List<Movie> movieList, List<Theater> theaterList,
                           OnBookClickListener listener) {
        this.context = context;
        this.showtimeList = showtimeList;
        this.movieList = movieList;
        this.theaterList = theaterList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ShowtimeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_showtime, parent, false);
        return new ShowtimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowtimeViewHolder holder, int position) {
        Showtime showtime = showtimeList.get(position);

        String movieTitle = "Unknown";
        for (Movie m : movieList) {
            if (m.getId() == showtime.getMovieId()) {
                movieTitle = m.getTitle();
                break;
            }
        }

        String theaterName = "Unknown";
        for (Theater t : theaterList) {
            if (t.getId() == showtime.getTheaterId()) {
                theaterName = t.getName();
                break;
            }
        }

        holder.tvMovieTitle.setText(movieTitle);
        holder.tvTheaterName.setText(theaterName);
        holder.tvDate.setText("Ngày: " + showtime.getDate());
        holder.tvTime.setText("Giờ: " + showtime.getTime());

        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        holder.tvPrice.setText("Giá: " + nf.format(showtime.getPrice()) + " VND");
        holder.tvAvailableSeats.setText("Còn " + showtime.getAvailableSeats() + " ghế trống");

        holder.btnBook.setOnClickListener(v -> {
            if (listener != null) {
                listener.onBookClick(showtime);
            }
        });
    }

    @Override
    public int getItemCount() {
        return showtimeList != null ? showtimeList.size() : 0;
    }

    public void updateList(List<Showtime> newList) {
        this.showtimeList = newList;
        notifyDataSetChanged();
    }

    static class ShowtimeViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvMovieTitle;
        TextView tvTheaterName;
        TextView tvDate;
        TextView tvTime;
        TextView tvPrice;
        TextView tvAvailableSeats;
        Button btnBook;

        ShowtimeViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_showtime);
            tvMovieTitle = itemView.findViewById(R.id.tv_showtime_movie);
            tvTheaterName = itemView.findViewById(R.id.tv_showtime_theater);
            tvDate = itemView.findViewById(R.id.tv_showtime_date);
            tvTime = itemView.findViewById(R.id.tv_showtime_time);
            tvPrice = itemView.findViewById(R.id.tv_showtime_price);
            tvAvailableSeats = itemView.findViewById(R.id.tv_showtime_seats);
            btnBook = itemView.findViewById(R.id.btn_book_showtime);
        }
    }
}
