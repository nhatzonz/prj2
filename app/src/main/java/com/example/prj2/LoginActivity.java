package com.example.prj2;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.prj2.data.database.AppDatabase;
import com.example.prj2.data.entity.User;
import com.example.prj2.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    private EditText etEmail;
    private EditText etPassword;
    private Button btnLogin;
    private TextView tvRegisterLink;

    private AppDatabase db;
    private SessionManager sessionManager;

    public static final String EXTRA_SHOWTIME_ID = "extra_showtime_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = AppDatabase.getInstance(this);
        sessionManager = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Đăng nhập");
        }

        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        btnLogin = findViewById(R.id.btn_login);
        tvRegisterLink = findViewById(R.id.tv_register_link);

        btnLogin.setOnClickListener(v -> performLogin());

        tvRegisterLink.setOnClickListener(v -> {
            int showtimeId = getIntent().getIntExtra(EXTRA_SHOWTIME_ID, -1);
            Intent intent = new Intent(this, RegisterActivity.class);
            if (showtimeId != -1) {
                intent.putExtra(RegisterActivity.EXTRA_SHOWTIME_ID, showtimeId);
            }
            startActivity(intent);
        });
    }

    private void performLogin() {
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        User user = db.userDao().login(email, password);
        if (user != null) {
            sessionManager.createLoginSession(user.getId(), user.getUsername(), user.getEmail());
            Toast.makeText(this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();

            int showtimeId = getIntent().getIntExtra(EXTRA_SHOWTIME_ID, -1);
            if (showtimeId != -1) {
                Intent intent = new Intent(this, SeatSelectionActivity.class);
                intent.putExtra(SeatSelectionActivity.EXTRA_SHOWTIME_ID, showtimeId);
                startActivity(intent);
            }
            finish();
        } else {
            Toast.makeText(this, "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
