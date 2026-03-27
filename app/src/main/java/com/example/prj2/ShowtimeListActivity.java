package com.example.prj2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj2.adapter.ShowtimeAdapter;
import com.example.prj2.data.database.AppDatabase;
import com.example.prj2.data.entity.Movie;
import com.example.prj2.data.entity.Showtime;
import com.example.prj2.data.entity.Theater;
import com.example.prj2.utils.SessionManager;

import java.util.List;

public class ShowtimeListActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    public static final String EXTRA_MOVIE_TITLE = "extra_movie_title";

    private RecyclerView recyclerView;
    private ShowtimeAdapter adapter;
    private TextView tvNoShowtimes;
    private AppDatabase db;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showtime_list);

        db = AppDatabase.getInstance(this);
        sessionManager = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);
            String movieTitle = getIntent().getStringExtra(EXTRA_MOVIE_TITLE);
            if (movieTitle != null) {
                getSupportActionBar().setTitle("Lịch chiếu: " + movieTitle);
            } else {
                getSupportActionBar().setTitle("Lịch chiếu phim");
            }
        }

        recyclerView = findViewById(R.id.recycler_showtimes);
        tvNoShowtimes = findViewById(R.id.tv_no_showtimes);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadShowtimes();
    }

    private void loadShowtimes() {
        int movieId = getIntent().getIntExtra(EXTRA_MOVIE_ID, -1);

        List<Showtime> showtimes;
        if (movieId != -1) {
            showtimes = db.showtimeDao().getShowtimesByMovie(movieId);
        } else {
            showtimes = db.showtimeDao().getAllShowtimes();
        }

        List<Movie> movies = db.movieDao().getAllMovies();
        List<Theater> theaters = db.theaterDao().getAllTheaters();

        if (showtimes.isEmpty()) {
            tvNoShowtimes.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoShowtimes.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter = new ShowtimeAdapter(this, showtimes, movies, theaters, showtime -> {
            if (sessionManager.isLoggedIn()) {
                Intent intent = new Intent(this, SeatSelectionActivity.class);
                intent.putExtra(SeatSelectionActivity.EXTRA_SHOWTIME_ID, showtime.getId());
                startActivity(intent);
            } else {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.putExtra(LoginActivity.EXTRA_SHOWTIME_ID, showtime.getId());
                startActivity(intent);
            }
        });
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
