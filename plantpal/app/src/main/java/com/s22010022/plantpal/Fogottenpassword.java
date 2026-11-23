package com.s22010022.plantpal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Fogottenpassword extends AppCompatActivity {
    private EditText etUsername, etEmail;
    private Button btnConfirm;
    private databaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fogottenpassword);
        db = new databaseHelper(this);

        etUsername = findViewById(R.id.editTextText7);
        etEmail = findViewById(R.id.editTextText9);
        btnConfirm = findViewById(R.id.button6);

        btnConfirm.setOnClickListener(v -> {
            String uName = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();

            if (TextUtils.isEmpty(uName) || TextUtils.isEmpty(email)) {
                Toast.makeText(Fogottenpassword.this, "Please enter both Username and Email", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor cursor = db.getUserDetails(uName);
            if (cursor.moveToFirst()) {
                int emailColIdx = cursor.getColumnIndex(databaseHelper.COL_EMAIL);
                String dbEmail = emailColIdx != -1 ? cursor.getString(emailColIdx) : "";
                cursor.close();

                if (dbEmail.equalsIgnoreCase(email)) {
                    showNewPasswordDialog(uName);
                } else {
                    Toast.makeText(Fogottenpassword.this, "Email does not match this username!", Toast.LENGTH_LONG).show();
                }
            } else {
                cursor.close();
                Toast.makeText(Fogottenpassword.this, "Username not found!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showNewPasswordDialog(String uName) {
        final EditText input = new EditText(this);
        input.setHint("Enter new password");
        input.setInputType(android.text.InputType.TYPE_CLASS_TEXT | android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);

        new AlertDialog.Builder(this)
                .setTitle("Reset Password")
                .setMessage("Enter new password for " + uName)
                .setView(input)
                .setPositiveButton("Reset", (dialog, which) -> {
                    String newPass = input.getText().toString().trim();
                    if (newPass.length() >= 4) {
                        Cursor c = db.getUserDetails(uName);
                        String nic = "";
                        if (c.moveToFirst()) {
                            int nicIdx = c.getColumnIndex(databaseHelper.COL_NIC);
                            if (nicIdx != -1) nic = c.getString(nicIdx);
                        }
                        c.close();
                        boolean updated = db.updatePassword(uName, nic, newPass);
                        if (updated) {
                            Toast.makeText(Fogottenpassword.this, "Password updated successfully! Please login.", Toast.LENGTH_LONG).show();
                            finish();
                        } else {
                            Toast.makeText(Fogottenpassword.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(Fogottenpassword.this, "Password must be at least 4 characters", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
}
