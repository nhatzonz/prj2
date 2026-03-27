package com.example.prj2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj2.adapter.TicketAdapter;
import com.example.prj2.data.database.AppDatabase;
import com.example.prj2.data.entity.Movie;
import com.example.prj2.data.entity.Showtime;
import com.example.prj2.data.entity.Theater;
import com.example.prj2.data.entity.Ticket;
import com.example.prj2.utils.SessionManager;

import java.util.List;

public class MyTicketsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TicketAdapter adapter;
    private TextView tvNoTickets;
    private AppDatabase db;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tickets);

        db = AppDatabase.getInstance(this);
        sessionManager = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Vé của tôi");
        }

        recyclerView = findViewById(R.id.recycler_tickets);
        tvNoTickets = findViewById(R.id.tv_no_tickets);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTickets();
    }

    private void loadTickets() {
        int userId = sessionManager.getUserId();
        List<Ticket> tickets = db.ticketDao().getTicketsByUser(userId);
        List<Showtime> showtimes = db.showtimeDao().getAllShowtimes();
        List<Movie> movies = db.movieDao().getAllMovies();
        List<Theater> theaters = db.theaterDao().getAllTheaters();

        if (tickets.isEmpty()) {
            tvNoTickets.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoTickets.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter = new TicketAdapter(this, tickets, showtimes, movies, theaters);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
