package com.s22010022.plantpal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class fertilizerPage1 extends AppCompatActivity {

    private Button btnProduct1, btnProduct2, btnProduct3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer_page);

        btnProduct1 = findViewById(R.id.btnProduct1);
        btnProduct2 = findViewById(R.id.btnProduct2);
        btnProduct3 = findViewById(R.id.btnProduct3);

        if (btnProduct1 != null) {
            btnProduct1.setOnClickListener(v -> openProductDetail(
                    "Organic Compost",
                    "Organic",
                    125.00,
                    R.drawable.img_18,
                    "High-grade all-natural organic compost rich in essential nutrients, microbial activity, and organic matter to rejuvenate garden soil."
            ));
        }

        if (btnProduct2 != null) {
            btnProduct2.setOnClickListener(v -> openProductDetail(
                    "Vermicompost Premium",
                    "Organic",
                    150.00,
                    R.drawable.img_12,
                    "Pure earthworm castings packed with humic acid, minerals, and micro-nutrients for maximum root growth and vibrant flowering."
            ));
        }

        if (btnProduct3 != null) {
            btnProduct3.setOnClickListener(v -> openProductDetail(
                    "Organic Bone Meal",
                    "Organic",
                    95.00,
                    R.drawable.img_11,
                    "Slow-release natural phosphorus and calcium supplement ideal for bulbs, roses, vegetables, and strong root system development."
            ));
        }
    }

    private void openProductDetail(String name, String category, double price, int imageResId, String description) {
        Intent intent = new Intent(fertilizerPage1.this, productdiscription.class);
        intent.putExtra("product_name", name);
        intent.putExtra("product_category", category);
        intent.putExtra("product_price", price);
        intent.putExtra("product_image", imageResId);
        intent.putExtra("product_description", description);
        startActivity(intent);
    }
}
