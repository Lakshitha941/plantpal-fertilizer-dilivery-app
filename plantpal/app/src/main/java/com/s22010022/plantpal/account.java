package com.s22010022.plantpal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class account extends AppCompatActivity {

    private EditText etUsername, etEmail, etPhone, etAddress;
    private Button btnSave, btnEdit;
    private databaseHelper db;
    private SessionManager sessionManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        db = new databaseHelper(this);
        sessionManager = new SessionManager(this);

        etUsername = findViewById(R.id.username);
        etEmail = findViewById(R.id.email);
        etPhone = findViewById(R.id.phone);
        etAddress = findViewById(R.id.address);
        btnSave = findViewById(R.id.save_button);
        btnEdit = findViewById(R.id.edit_button);

        loadProfileData();

        // Initially make fields non-editable until Edit is clicked
        setFieldsEditable(false);

        btnEdit.setOnClickListener(v -> {
            setFieldsEditable(true);
            etEmail.requestFocus();
            Toast.makeText(account.this, "Editing enabled. Update your details and click Save.", Toast.LENGTH_SHORT).show();
        });

        btnSave.setOnClickListener(v -> {
            String uName = sessionManager.getUsername();
            String email = etEmail.getText().toString().trim();
            String phone = etPhone.getText().toString().trim();
            String address = etAddress.getText().toString().trim();

            if (TextUtils.isEmpty(email)) {
                etEmail.setError("Email cannot be empty");
                etEmail.requestFocus();
                return;
            }

            boolean updated = db.updateUserProfile(uName, email, phone, address);
            if (updated) {
                setFieldsEditable(false);
                Toast.makeText(account.this, "Profile updated successfully! ✅", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(account.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
            }
        });

        setupBottomNavigation();
    }

    private void setFieldsEditable(boolean enabled) {
        // Username is always fixed/unique identifier
        etUsername.setEnabled(false);
        etEmail.setEnabled(enabled);
        etPhone.setEnabled(enabled);
        etAddress.setEnabled(enabled);
    }

    private void loadProfileData() {
        String uName = sessionManager.getUsername();
        etUsername.setText(uName);

        Cursor cursor = db.getUserDetails(uName);
        if (cursor != null && cursor.moveToFirst()) {
            int emailCol = cursor.getColumnIndex(databaseHelper.COL_EMAIL);
            int phoneCol = cursor.getColumnIndex(databaseHelper.COL_PHONE);
            int addrCol = cursor.getColumnIndex(databaseHelper.COL_ADDRESS);

            if (emailCol != -1 && !cursor.isNull(emailCol)) etEmail.setText(cursor.getString(emailCol));
            if (phoneCol != -1 && !cursor.isNull(phoneCol)) etPhone.setText(cursor.getString(phoneCol));
            if (addrCol != -1 && !cursor.isNull(addrCol)) etAddress.setText(cursor.getString(addrCol));
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_profile);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(account.this, Dashbord.class));
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_cart) {
                    startActivity(new Intent(account.this, cart.class));
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_setting) {
                    startActivity(new Intent(account.this, setting.class));
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    return true;
                }
                return false;
            });
        }
    }
}
