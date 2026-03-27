package com.example.prj2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prj2.data.database.AppDatabase;
import com.example.prj2.data.entity.Movie;
import com.example.prj2.data.entity.Showtime;
import com.example.prj2.data.entity.Theater;
import com.example.prj2.data.entity.Ticket;

import java.text.NumberFormat;
import java.util.Locale;

public class TicketConfirmationActivity extends AppCompatActivity {

    public static final String EXTRA_TICKET_ID = "extra_ticket_id";

    private TextView tvMovieName;
    private TextView tvTheaterName;
    private TextView tvDate;
    private TextView tvTime;
    private TextView tvSeat;
    private TextView tvPrice;
    private TextView tvBookingDate;
    private TextView tvStatus;
    private TextView tvTicketId;
    private Button btnBackHome;
    private Button btnMyTickets;

    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_confirmation);

        db = AppDatabase.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setTitle("Xác nhận đặt vé");
        }

        tvMovieName = findViewById(R.id.tv_confirm_movie);
        tvTheaterName = findViewById(R.id.tv_confirm_theater);
        tvDate = findViewById(R.id.tv_confirm_date);
        tvTime = findViewById(R.id.tv_confirm_time);
        tvSeat = findViewById(R.id.tv_confirm_seat);
        tvPrice = findViewById(R.id.tv_confirm_price);
        tvBookingDate = findViewById(R.id.tv_confirm_booking_date);
        tvStatus = findViewById(R.id.tv_confirm_status);
        tvTicketId = findViewById(R.id.tv_confirm_ticket_id);
        btnBackHome = findViewById(R.id.btn_back_home);
        btnMyTickets = findViewById(R.id.btn_my_tickets);

        int ticketId = getIntent().getIntExtra(EXTRA_TICKET_ID, -1);
        if (ticketId == -1) {
            Toast.makeText(this, "Không tìm thấy thông tin vé", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        loadTicketInfo(ticketId);

        btnBackHome.setOnClickListener(v -> {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });

        btnMyTickets.setOnClickListener(v -> {
            Intent intent = new Intent(this, MyTicketsActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        });
    }

    private void loadTicketInfo(int ticketId) {
        Ticket ticket = db.ticketDao().getTicketById(ticketId);
        if (ticket == null) {
            Toast.makeText(this, "Không tìm thấy vé", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        Showtime showtime = db.showtimeDao().getShowtimeById(ticket.getShowtimeId());
        Movie movie = null;
        Theater theater = null;

        if (showtime != null) {
            movie = db.movieDao().getMovieById(showtime.getMovieId());
            theater = db.theaterDao().getTheaterById(showtime.getTheaterId());
        }

        tvTicketId.setText("Mã vé: #" + ticket.getId());
        tvMovieName.setText("Phim: " + (movie != null ? movie.getTitle() : "N/A"));
        tvTheaterName.setText("Rạp: " + (theater != null ? theater.getName() : "N/A"));
        tvDate.setText("Ngày chiếu: " + (showtime != null ? showtime.getDate() : "N/A"));
        tvTime.setText("Giờ chiếu: " + (showtime != null ? showtime.getTime() : "N/A"));
        tvSeat.setText("Ghế: " + ticket.getSeatNumber());

        NumberFormat nf = NumberFormat.getInstance(new Locale("vi", "VN"));
        tvPrice.setText("Tổng tiền: " + nf.format(ticket.getTotalPrice()) + " VND");
        tvBookingDate.setText("Ngày đặt: " + ticket.getBookingDate());
        tvStatus.setText("Trạng thái: " + ticket.getStatus());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
