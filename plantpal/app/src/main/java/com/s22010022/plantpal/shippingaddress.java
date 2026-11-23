package com.s22010022.plantpal;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class shippingaddress extends AppCompatActivity {

    private EditText etFullName, etStreetAddress, etCity, etState, etPostalCode, etPhoneNumber;
    private Button btnSubmit;
    private databaseHelper db;
    private SessionManager sessionManager;
    private double totalAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shippingaddress);

        db = new databaseHelper(this);
        sessionManager = new SessionManager(this);
        totalAmount = getIntent().getDoubleExtra("total_amount", 0.0);

        etFullName = findViewById(R.id.et_full_name);
        etStreetAddress = findViewById(R.id.et_street_address);
        etCity = findViewById(R.id.et_city);
        etState = findViewById(R.id.et_state);
        etPostalCode = findViewById(R.id.et_postal_code);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        btnSubmit = findViewById(R.id.btn_submit);

        // Auto-populate existing profile details if available
        loadUserProfile();

        btnSubmit.setOnClickListener(v -> {
            if (isValid()) {
                String fullName = etFullName.getText().toString().trim();
                String address = etStreetAddress.getText().toString().trim();
                String city = etCity.getText().toString().trim();
                String phone = etPhoneNumber.getText().toString().trim();

                // Save order in database & clear cart
                db.insertOrder(sessionManager.getUsername(), fullName, address, city, phone, totalAmount);

                Toast.makeText(shippingaddress.this, "Order placed successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(shippingaddress.this, EndPageActivity.class);
                intent.putExtra("order_user", fullName);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loadUserProfile() {
        Cursor cursor = db.getUserDetails(sessionManager.getUsername());
        if (cursor != null && cursor.moveToFirst()) {
            int phoneCol = cursor.getColumnIndex(databaseHelper.COL_PHONE);
            int addrCol = cursor.getColumnIndex(databaseHelper.COL_ADDRESS);

            if (phoneCol != -1 && !cursor.isNull(phoneCol)) {
                etPhoneNumber.setText(cursor.getString(phoneCol));
            }
            if (addrCol != -1 && !cursor.isNull(addrCol)) {
                etStreetAddress.setText(cursor.getString(addrCol));
            }
            etFullName.setText(sessionManager.getUsername());
            cursor.close();
        } else if (cursor != null) {
            cursor.close();
        }
    }

    private boolean isValid() {
        if (TextUtils.isEmpty(etFullName.getText().toString().trim())) {
            etFullName.setError("Full Name is required");
            etFullName.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etStreetAddress.getText().toString().trim())) {
            etStreetAddress.setError("Street Address is required");
            etStreetAddress.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etCity.getText().toString().trim())) {
            etCity.setError("City is required");
            etCity.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etState.getText().toString().trim())) {
            etState.setError("State is required");
            etState.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etPostalCode.getText().toString().trim())) {
            etPostalCode.setError("Postal Code is required");
            etPostalCode.requestFocus();
            return false;
        }

        if (TextUtils.isEmpty(etPhoneNumber.getText().toString().trim())) {
            etPhoneNumber.setError("Phone Number is required");
            etPhoneNumber.requestFocus();
            return false;
        }

        return true;
    }
}
