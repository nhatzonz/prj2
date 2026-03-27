package com.example.prj2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.prj2.data.database.DatabaseSeeder;
import com.example.prj2.utils.SessionManager;

public class MainActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private TextView tvWelcome;
    private Button btnLogin;
    private CardView cardMovies;
    private CardView cardTheaters;
    private CardView cardShowtimes;
    private CardView cardMyTickets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Seed database on first launch
        DatabaseSeeder.seed(this);

        sessionManager = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvWelcome = findViewById(R.id.tv_welcome);
        btnLogin = findViewById(R.id.btn_login);
        cardMovies = findViewById(R.id.card_movies);
        cardTheaters = findViewById(R.id.card_theaters);
        cardShowtimes = findViewById(R.id.card_showtimes);
        cardMyTickets = findViewById(R.id.card_my_tickets);

        cardMovies.setOnClickListener(v -> startActivity(new Intent(this, MovieListActivity.class)));
        cardTheaters.setOnClickListener(v -> startActivity(new Intent(this, TheaterListActivity.class)));
        cardShowtimes.setOnClickListener(v -> startActivity(new Intent(this, ShowtimeListActivity.class)));

        btnLogin.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                new AlertDialog.Builder(this)
                        .setTitle("Đăng xuất")
                        .setMessage("Bạn có chắc muốn đăng xuất?")
                        .setPositiveButton("Đăng xuất", (dialog, which) -> {
                            sessionManager.logout();
                            updateUI();
                            invalidateOptionsMenu();
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        cardMyTickets.setOnClickListener(v -> {
            if (sessionManager.isLoggedIn()) {
                startActivity(new Intent(this, MyTicketsActivity.class));
            } else {
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        updateUI();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
        invalidateOptionsMenu();
    }

    private void updateUI() {
        if (sessionManager.isLoggedIn()) {
            tvWelcome.setText("Xin chào, " + sessionManager.getUsername() + "!");
            btnLogin.setText("Đăng xuất");
            btnLogin.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(R.color.text_secondary, getTheme())));
        } else {
            tvWelcome.setText("Xin chào! Đăng nhập để đặt vé.");
            btnLogin.setText("Đăng nhập");
            btnLogin.setBackgroundTintList(android.content.res.ColorStateList.valueOf(
                    getResources().getColor(R.color.primary, getTheme())));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (sessionManager.isLoggedIn()) {
            menu.add(Menu.NONE, R.id.menu_my_tickets, Menu.NONE, "Vé của tôi")
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
            menu.add(Menu.NONE, R.id.menu_logout, Menu.NONE, "Đăng xuất")
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        } else {
            menu.add(Menu.NONE, R.id.menu_login, Menu.NONE, "Đăng nhập")
                    .setShowAsAction(MenuItem.SHOW_AS_ACTION_NEVER);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_login) {
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        } else if (id == R.id.menu_my_tickets) {
            startActivity(new Intent(this, MyTicketsActivity.class));
            return true;
        } else if (id == R.id.menu_logout) {
            new AlertDialog.Builder(this)
                    .setTitle("Đăng xuất")
                    .setMessage("Bạn có chắc muốn đăng xuất?")
                    .setPositiveButton("Đăng xuất", (dialog, which) -> {
                        sessionManager.logout();
                        updateUI();
                        invalidateOptionsMenu();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
