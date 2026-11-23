package com.s22010022.plantpal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class setting extends AppCompatActivity {

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switchNotifications, switchReminder, switchDarkMode;
    private Button btnSave, btnReset;
    private SessionManager sessionManager;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);

        sessionManager = new SessionManager(this);

        switchNotifications = findViewById(R.id.notifications_switch);
        switchReminder = findViewById(R.id.daily_reminder_switch);
        switchDarkMode = findViewById(R.id.dark_mode_switch);
        btnSave = findViewById(R.id.save_button);
        btnReset = findViewById(R.id.reset_button);

        // Load existing preferences
        if (switchDarkMode != null) {
            switchDarkMode.setChecked(sessionManager.isDarkMode());
            switchDarkMode.setOnCheckedChangeListener((buttonView, isChecked) -> {
                sessionManager.setDarkMode(isChecked);
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            });
        }

        if (switchNotifications != null) {
            switchNotifications.setChecked(sessionManager.isNotificationsEnabled());
        }

        if (btnSave != null) {
            btnSave.setOnClickListener(v -> {
                if (switchNotifications != null) {
                    sessionManager.setNotificationsEnabled(switchNotifications.isChecked());
                }
                Toast.makeText(setting.this, "Settings Saved Successfully", Toast.LENGTH_SHORT).show();
            });
        }

        if (btnReset != null) {
            btnReset.setText("Log Out");
            btnReset.setOnClickListener(v -> {
                Toast.makeText(setting.this, "Logging out...", Toast.LENGTH_SHORT).show();
                sessionManager.logoutUser();
                finish();
            });
        }

        setupBottomNavigation();
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_setting);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(setting.this, Dashbord.class));
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_cart) {
                    startActivity(new Intent(setting.this, cart.class));
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_setting) {
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    startActivity(new Intent(setting.this, account.class));
                    finish();
                    return true;
                }
                return false;
            });
        }
    }
}
