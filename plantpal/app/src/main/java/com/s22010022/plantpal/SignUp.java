package com.s22010022.plantpal;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private databaseHelper db;
    private EditText userNameText, emailText, nicText, passwordText;
    private Button signUpBtn;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        db = new databaseHelper(this);

        userNameText = findViewById(R.id.UserNameText);
        emailText = findViewById(R.id.emailText);
        nicText = findViewById(R.id.NICText);
        passwordText = findViewById(R.id.passwordText);
        signUpBtn = findViewById(R.id.signUpBtn);

        signUpBtn.setOnClickListener(v -> performSignUp());
    }

    private void performSignUp() {
        String uName = userNameText.getText().toString().trim();
        String email = emailText.getText().toString().trim();
        String nic = nicText.getText().toString().trim();
        String pwd = passwordText.getText().toString().trim();

        if (TextUtils.isEmpty(uName)) {
            userNameText.setError("Username is required");
            userNameText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email) || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailText.setError("Valid email is required");
            emailText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(nic)) {
            nicText.setError("NIC is required");
            nicText.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(pwd) || pwd.length() < 4) {
            passwordText.setError("Password must be at least 4 characters");
            passwordText.requestFocus();
            return;
        }

        boolean isInserted = db.signUpInsert(uName, email, nic, pwd);
        if (isInserted) {
            Toast.makeText(SignUp.this, "Account Created Successfully! Please Login.", Toast.LENGTH_LONG).show();
            finish(); // Return to Login screen
        } else {
            Toast.makeText(SignUp.this, "Username already exists. Please choose another.", Toast.LENGTH_LONG).show();
        }
    }
}
