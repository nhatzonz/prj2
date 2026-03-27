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

public class RegisterActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etEmail;
    private EditText etPhone;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private Button btnRegister;
    private TextView tvLoginLink;

    private AppDatabase db;
    private SessionManager sessionManager;

    public static final String EXTRA_SHOWTIME_ID = "extra_showtime_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        db = AppDatabase.getInstance(this);
        sessionManager = new SessionManager(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Đăng ký");
        }

        etUsername = findViewById(R.id.et_username);
        etEmail = findViewById(R.id.et_email);
        etPhone = findViewById(R.id.et_phone);
        etPassword = findViewById(R.id.et_password);
        etConfirmPassword = findViewById(R.id.et_confirm_password);
        btnRegister = findViewById(R.id.btn_register);
        tvLoginLink = findViewById(R.id.tv_login_link);

        btnRegister.setOnClickListener(v -> performRegister());

        tvLoginLink.setOnClickListener(v -> finish());
    }

    private void performRegister() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.userDao().countByEmail(email) > 0) {
            Toast.makeText(this, "Email đã được sử dụng", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User(username, email, password, phone);
        long userId = db.userDao().insert(newUser);

        if (userId > 0) {
            sessionManager.createLoginSession((int) userId, username, email);
            Toast.makeText(this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();

            int showtimeId = getIntent().getIntExtra(EXTRA_SHOWTIME_ID, -1);
            if (showtimeId != -1) {
                Intent intent = new Intent(this, SeatSelectionActivity.class);
                intent.putExtra(SeatSelectionActivity.EXTRA_SHOWTIME_ID, showtimeId);
                startActivity(intent);
                finish();
            } else {
                finish();
            }
        } else {
            Toast.makeText(this, "Có lỗi xảy ra, vui lòng thử lại", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
