package com.s22010022.plantpal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class fertilizerPage3 extends AppCompatActivity {

    private Button btnProduct1, btnProduct2, btnProduct3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizerpage3);

        btnProduct1 = findViewById(R.id.btnProduct1);
        btnProduct2 = findViewById(R.id.btnProduct2);
        btnProduct3 = findViewById(R.id.btnProduct3);

        if (btnProduct1 != null) {
            btnProduct1.setOnClickListener(v -> openProductDetail(
                    "Chelated Foliar Blend",
                    "Blends",
                    125.00,
                    R.drawable.img_39,
                    "Premium chelated micro-nutrient foliar spray formulated for vegetables, fruits, pulses, and cereal crops."
            ));
        }

        if (btnProduct2 != null) {
            btnProduct2.setOnClickListener(v -> openProductDetail(
                    "Lawn Performance Blend",
                    "Blends",
                    160.00,
                    R.drawable.img_30,
                    "Specially engineered slow-release turf booster designed to maintain rich, lush green color and dense grass turf."
            ));
        }

        if (btnProduct3 != null) {
            btnProduct3.setOnClickListener(v -> openProductDetail(
                    "Topsoil & Compost Mix",
                    "Blends",
                    90.00,
                    R.drawable.img_28,
                    "Balanced combination of screened rich topsoil and well-aged organic compost ready for raised beds and garden patching."
            ));
        }
    }

    private void openProductDetail(String name, String category, double price, int imageResId, String description) {
        Intent intent = new Intent(fertilizerPage3.this, productdiscription.class);
        intent.putExtra("product_name", name);
        intent.putExtra("product_category", category);
        intent.putExtra("product_price", price);
        intent.putExtra("product_image", imageResId);
        intent.putExtra("product_description", description);
        startActivity(intent);
    }
}
