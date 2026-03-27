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
import com.example.prj2.data.entity.Showtime;
import com.example.prj2.data.entity.Theater;
import com.example.prj2.data.entity.Ticket;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.TicketViewHolder> {

    private Context context;
    private List<Ticket> ticketList;
    private List<Showtime> showtimeList;
    private List<Movie> movieList;
    private List<Theater> theaterList;

    public TicketAdapter(Context context, List<Ticket> ticketList,
                         List<Showtime> showtimeList, List<Movie> movieList,
                         List<Theater> theaterList) {
        this.context = context;
        this.ticketList = ticketList;
        this.showtimeList = showtimeList;
        this.movieList = movieList;
        this.theaterList = theaterList;
    }

    @NonNull
    @Override
    public TicketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ticket, parent, false);
        return new TicketViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TicketViewHolder holder, int position) {
        Ticket ticket = ticketList.get(position);

        Showtime showtime = null;
        for (Showtime s : showtimeList) {
            if (s.getId() == ticket.getShowtimeId()) {
                showtime = s;
                break;
            }
        }

        String movieTitle = "Unknown";
        String theaterName = "Unknown";
        String date = "";
        String time = "";

        if (showtime != null) {
            date = showtime.getDate();
            time = showtime.getTime();
            for (Movie m : movieList) {
                if (m.getId() == showtime.getMovieId()) {
                    movieTitle = m.getTitle();
                    break;
                }
            }
            for (Theater t : theaterList) {
                if (t.getId() == showtime.getTheaterId()) {
                    theaterName = t.getName();
                    break;
                }
            }
        }

        holder.tvTicketId.setText("Mã vé: #" + ticket.getId());
        holder.tvMovieTitle.setText(movieTitle);
        holder.tvTheaterName.setText(theaterName);
        holder.tvDate.setText("Ngày chiếu: " + date);
        holder.tvTime.setText("Giờ chiếu: " + time);
        holder.tvSeat.setText("Ghế: " + ticket.getSeatNumber());

        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        holder.tvPrice.setText("Tổng tiền: " + nf.format(ticket.getTotalPrice()) + " VND");
        holder.tvStatus.setText("Trạng thái: " + ticket.getStatus());
    }

    @Override
    public int getItemCount() {
        return ticketList != null ? ticketList.size() : 0;
    }

    static class TicketViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvTicketId;
        TextView tvMovieTitle;
        TextView tvTheaterName;
        TextView tvDate;
        TextView tvTime;
        TextView tvSeat;
        TextView tvPrice;
        TextView tvStatus;

        TicketViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_ticket);
            tvTicketId = itemView.findViewById(R.id.tv_ticket_id);
            tvMovieTitle = itemView.findViewById(R.id.tv_ticket_movie);
            tvTheaterName = itemView.findViewById(R.id.tv_ticket_theater);
            tvDate = itemView.findViewById(R.id.tv_ticket_date);
            tvTime = itemView.findViewById(R.id.tv_ticket_time);
            tvSeat = itemView.findViewById(R.id.tv_ticket_seat);
            tvPrice = itemView.findViewById(R.id.tv_ticket_price);
            tvStatus = itemView.findViewById(R.id.tv_ticket_status);
        }
    }
}
