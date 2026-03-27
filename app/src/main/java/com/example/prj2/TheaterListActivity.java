package com.example.prj2;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj2.adapter.TheaterAdapter;
import com.example.prj2.data.database.AppDatabase;
import com.example.prj2.data.entity.Theater;

import java.util.List;

public class TheaterListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private TheaterAdapter adapter;
    private TextView tvNoTheaters;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theater_list);

        db = AppDatabase.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Rạp chiếu phim");
        }

        recyclerView = findViewById(R.id.recycler_theaters);
        tvNoTheaters = findViewById(R.id.tv_no_theaters);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadTheaters();
    }

    private void loadTheaters() {
        List<Theater> theaters = db.theaterDao().getAllTheaters();

        if (theaters.isEmpty()) {
            tvNoTheaters.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvNoTheaters.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        adapter = new TheaterAdapter(this, theaters, theater -> {
            // Could navigate to showtime filtered by theater in future
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
