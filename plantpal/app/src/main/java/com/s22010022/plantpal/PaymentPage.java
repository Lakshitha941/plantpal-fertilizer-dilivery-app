package com.s22010022.plantpal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class PaymentPage extends AppCompatActivity {
    private Button proceed;
    private double totalAmount = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment);

        totalAmount = getIntent().getDoubleExtra("total_amount", 150.00);

        proceed = findViewById(R.id.proceedToPayButton);
        if (proceed != null) {
            proceed.setOnClickListener(v -> {
                Intent intent = new Intent(PaymentPage.this, shippingaddress.class);
                intent.putExtra("total_amount", totalAmount);
                startActivity(intent);
            });
        }
    }
}
