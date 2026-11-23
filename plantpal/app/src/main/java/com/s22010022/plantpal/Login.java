package com.s22010022.plantpal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private Button signUpPageBtn, loginBtn;
    private TextView forgetPassBtn;
    private databaseHelper db;
    private EditText userName, password;
    private SessionManager sessionManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionManager = new SessionManager(this);

        if (sessionManager.isLoggedIn()) {
            startActivity(new Intent(Login.this, Dashbord.class));
            finish();
            return;
        }

        setContentView(R.layout.activity_login);
        db = new databaseHelper(this);
        signUpPageBtn = findViewById(R.id.signUpPageBtn);
        forgetPassBtn = findViewById(R.id.forgotPassBTN);
        loginBtn = findViewById(R.id.loginBtn);
        userName = findViewById(R.id.userNameForLogin);
        password = findViewById(R.id.passwordForLogin);

        signUpPageBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, SignUp.class);
            startActivity(intent);
        });

        forgetPassBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Login.this, Fogottenpassword.class);
            startActivity(intent);
        });

        loginBtn.setOnClickListener(v -> {
            String uName = userName.getText().toString().trim();
            String pwd = password.getText().toString().trim();

            if (TextUtils.isEmpty(uName)) {
                userName.setError("Please enter username");
                userName.requestFocus();
                return;
            }
            if (TextUtils.isEmpty(pwd)) {
                password.setError("Please enter password");
                password.requestFocus();
                return;
            }

            boolean isLoggedId = db.checkUser(uName, pwd);
            if (isLoggedId) {
                // Fetch email for session
                String email = "";
                Cursor cursor = db.getUserDetails(uName);
                if (cursor.moveToFirst()) {
                    int emailIdx = cursor.getColumnIndex(databaseHelper.COL_EMAIL);
                    if (emailIdx != -1) email = cursor.getString(emailIdx);
                }
                cursor.close();

                sessionManager.createLoginSession(uName, email);
                Toast.makeText(Login.this, "Welcome back, " + uName + "!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Login.this, Dashbord.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(Login.this, "Invalid Username or Password!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
