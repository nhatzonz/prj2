package com.example.prj2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prj2.adapter.MovieAdapter;
import com.example.prj2.data.database.AppDatabase;
import com.example.prj2.data.entity.Movie;

import java.util.List;

public class MovieListActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private EditText etSearch;
    private TextView tvNoMovies;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

        db = AppDatabase.getInstance(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Phim đang chiếu");
        }

        recyclerView = findViewById(R.id.recycler_movies);
        etSearch = findViewById(R.id.et_search);
        tvNoMovies = findViewById(R.id.tv_no_movies);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadMovies("");

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                loadMovies(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {}
        });
    }

    private void loadMovies(String query) {
        List<Movie> movies;
        if (query.isEmpty()) {
            movies = db.movieDao().getAllMovies();
        } else {
            movies = db.movieDao().searchMovies(query);
        }

        if (movies.isEmpty()) {
            tvNoMovies.setVisibility(android.view.View.VISIBLE);
            recyclerView.setVisibility(android.view.View.GONE);
        } else {
            tvNoMovies.setVisibility(android.view.View.GONE);
            recyclerView.setVisibility(android.view.View.VISIBLE);
        }

        if (adapter == null) {
            adapter = new MovieAdapter(this, movies, movie -> {
                Intent intent = new Intent(this, ShowtimeListActivity.class);
                intent.putExtra(ShowtimeListActivity.EXTRA_MOVIE_ID, movie.getId());
                intent.putExtra(ShowtimeListActivity.EXTRA_MOVIE_TITLE, movie.getTitle());
                startActivity(intent);
            });
            recyclerView.setAdapter(adapter);
        } else {
            adapter.updateList(movies);
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
