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
import com.example.prj2.data.entity.Theater;

import java.util.List;

public class TheaterAdapter extends RecyclerView.Adapter<TheaterAdapter.TheaterViewHolder> {

    private Context context;
    private List<Theater> theaterList;
    private OnTheaterClickListener listener;

    public interface OnTheaterClickListener {
        void onTheaterClick(Theater theater);
    }

    public TheaterAdapter(Context context, List<Theater> theaterList, OnTheaterClickListener listener) {
        this.context = context;
        this.theaterList = theaterList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TheaterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_theater, parent, false);
        return new TheaterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterViewHolder holder, int position) {
        Theater theater = theaterList.get(position);
        holder.tvName.setText(theater.getName());
        holder.tvAddress.setText("Địa chỉ: " + theater.getAddress());
        holder.tvSeats.setText("Số ghế: " + theater.getTotalSeats());

        holder.cardView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onTheaterClick(theater);
            }
        });
    }

    @Override
    public int getItemCount() {
        return theaterList != null ? theaterList.size() : 0;
    }

    static class TheaterViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        TextView tvName;
        TextView tvAddress;
        TextView tvSeats;

        TheaterViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.card_theater);
            tvName = itemView.findViewById(R.id.tv_theater_name);
            tvAddress = itemView.findViewById(R.id.tv_theater_address);
            tvSeats = itemView.findViewById(R.id.tv_theater_seats);
        }
    }
}
