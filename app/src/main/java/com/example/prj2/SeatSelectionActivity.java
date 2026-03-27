package com.example.prj2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prj2.data.database.AppDatabase;
import com.example.prj2.data.entity.Movie;
import com.example.prj2.data.entity.Showtime;
import com.example.prj2.data.entity.Theater;
import com.example.prj2.data.entity.Ticket;
import com.example.prj2.utils.SessionManager;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SeatSelectionActivity extends AppCompatActivity {

    public static final String EXTRA_SHOWTIME_ID = "extra_showtime_id";

    private GridLayout gridSeats;
    private TextView tvSelectedSeat;
    private TextView tvMovieInfo;
    private TextView tvShowtimeInfo;
    private Button btnConfirm;

    private AppDatabase db;
    private SessionManager sessionManager;

    private String selectedSeat = null;
    private int showtimeId;
    private Showtime showtime;
    private Movie movie;
    private Theater theater;

    private static final char[] ROW_LABELS = {'A', 'B', 'C', 'D', 'E'};
    private static final int COLS = 8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat_selection);

        db = AppDatabase.getInstance(this);
        sessionManager = new SessionManager(this);

        showtimeId = getIntent().getIntExtra(EXTRA_SHOWTIME_ID, -1);
        if (showtimeId == -1) {
            Toast.makeText(this, "Lỗi: Không tìm thấy suất chiếu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Chọn ghế ngồi");
        }

        gridSeats = findViewById(R.id.grid_seats);
        tvSelectedSeat = findViewById(R.id.tv_selected_seat);
        tvMovieInfo = findViewById(R.id.tv_movie_info);
        tvShowtimeInfo = findViewById(R.id.tv_showtime_info);
        btnConfirm = findViewById(R.id.btn_confirm_booking);

        loadShowtimeInfo();
        buildSeatGrid();

        btnConfirm.setOnClickListener(v -> confirmBooking());
    }

    private void loadShowtimeInfo() {
        showtime = db.showtimeDao().getShowtimeById(showtimeId);
        if (showtime == null) {
            Toast.makeText(this, "Không tìm thấy suất chiếu", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        movie = db.movieDao().getMovieById(showtime.getMovieId());
        theater = db.theaterDao().getTheaterById(showtime.getTheaterId());

        if (movie != null) {
            tvMovieInfo.setText(movie.getTitle() + " (" + movie.getDuration() + " phút)");
        }

        if (theater != null && showtime != null) {
            NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
            tvShowtimeInfo.setText(theater.getName() + " | " + showtime.getDate() + " " + showtime.getTime()
                    + " | " + nf.format(showtime.getPrice()) + " VND");
        }
    }

    private void buildSeatGrid() {
        gridSeats.removeAllViews();

        List<String> bookedSeats = db.ticketDao().getBookedSeatsByShowtime(showtimeId);

        gridSeats.setColumnCount(COLS);
        gridSeats.setRowCount(ROW_LABELS.length);

        for (int row = 0; row < ROW_LABELS.length; row++) {
            for (int col = 1; col <= COLS; col++) {
                String seatLabel = ROW_LABELS[row] + String.valueOf(col);
                boolean isBooked = bookedSeats.contains(seatLabel);

                Button seatBtn = new Button(this);
                seatBtn.setText(seatLabel);
                seatBtn.setTextSize(9f);
                seatBtn.setPadding(0, 0, 0, 0);
                seatBtn.setTextColor(Color.WHITE);
                seatBtn.setAllCaps(false);

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.height = GridLayout.LayoutParams.WRAP_CONTENT;
                params.setMargins(4, 4, 4, 4);
                params.columnSpec = GridLayout.spec(col - 1, 1f);
                params.rowSpec = GridLayout.spec(row);
                seatBtn.setLayoutParams(params);

                if (isBooked) {
                    seatBtn.setBackgroundResource(R.drawable.bg_seat_booked);
                    seatBtn.setEnabled(false);
                } else {
                    seatBtn.setBackgroundResource(R.drawable.bg_seat_available);
                    final String seat = seatLabel;
                    seatBtn.setOnClickListener(v -> selectSeat(seat, seatBtn));
                }

                gridSeats.addView(seatBtn);
            }
        }
    }

    private void selectSeat(String seatLabel, Button clickedBtn) {
        // Reset previously selected seat
        if (selectedSeat != null) {
            resetSeatColors();
        }
        selectedSeat = seatLabel;
        clickedBtn.setBackgroundResource(R.drawable.bg_seat_selected);
        tvSelectedSeat.setText("Ghế đã chọn: " + seatLabel);
        tvSelectedSeat.setVisibility(View.VISIBLE);
    }

    private void resetSeatColors() {
        List<String> bookedSeats = db.ticketDao().getBookedSeatsByShowtime(showtimeId);
        int childCount = gridSeats.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = gridSeats.getChildAt(i);
            if (child instanceof Button) {
                Button btn = (Button) child;
                String label = btn.getText().toString();
                if (!bookedSeats.contains(label)) {
                    btn.setBackgroundResource(R.drawable.bg_seat_available);
                }
            }
        }
    }

    private void confirmBooking() {
        if (selectedSeat == null) {
            Toast.makeText(this, "Vui lòng chọn một ghế", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!sessionManager.isLoggedIn()) {
            Intent intent = new Intent(this, LoginActivity.class);
            intent.putExtra(LoginActivity.EXTRA_SHOWTIME_ID, showtimeId);
            startActivity(intent);
            finish();
            return;
        }

        // Check seat still available
        int alreadyBooked = db.ticketDao().isSeatBooked(showtimeId, selectedSeat);
        if (alreadyBooked > 0) {
            Toast.makeText(this, "Ghế này đã được đặt, vui lòng chọn ghế khác", Toast.LENGTH_SHORT).show();
            buildSeatGrid();
            selectedSeat = null;
            tvSelectedSeat.setVisibility(View.GONE);
            return;
        }

        String today = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(new Date());
        Ticket ticket = new Ticket(
                showtimeId,
                sessionManager.getUserId(),
                selectedSeat,
                today,
                showtime.getPrice(),
                "Đã xác nhận"
        );

        long ticketId = db.ticketDao().insert(ticket);
        db.showtimeDao().decrementAvailableSeats(showtimeId);

        if (ticketId > 0) {
            Intent intent = new Intent(this, TicketConfirmationActivity.class);
            intent.putExtra(TicketConfirmationActivity.EXTRA_TICKET_ID, (int) ticketId);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }
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
