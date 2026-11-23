package com.s22010022.plantpal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class fertilizerPage2 extends AppCompatActivity {

    private Button btnProduct1, btnProduct2, btnProduct3;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fertilizer_page2);

        btnProduct1 = findViewById(R.id.btnProduct1);
        btnProduct2 = findViewById(R.id.btnProduct2);
        btnProduct3 = findViewById(R.id.btnProduct3);

        if (btnProduct1 != null) {
            btnProduct1.setOnClickListener(v -> openProductDetail(
                    "NPK 15-15-15 Balanced",
                    "Synthetic",
                    140.00,
                    R.drawable.img_10,
                    "Balanced all-purpose synthetic mineral fertilizer delivering fast-acting nitrogen, phosphorus, and potassium for all crops."
            ));
        }

        if (btnProduct2 != null) {
            btnProduct2.setOnClickListener(v -> openProductDetail(
                    "High Nitrogen Urea 46%",
                    "Synthetic",
                    110.00,
                    R.drawable.img_3,
                    "High-potency nitrogen fertilizer formulated to stimulate rapid vegetative growth, deep green foliage, and healthy pasture expansion."
            ));
        }

        if (btnProduct3 != null) {
            btnProduct3.setOnClickListener(v -> openProductDetail(
                    "Triple Superphosphate",
                    "Synthetic",
                    130.00,
                    R.drawable.img_5,
                    "Concentrated phosphorus fertilizer that accelerates root establishment and enhances vigorous flower bud and fruit formation."
            ));
        }
    }

    private void openProductDetail(String name, String category, double price, int imageResId, String description) {
        Intent intent = new Intent(fertilizerPage2.this, productdiscription.class);
        intent.putExtra("product_name", name);
        intent.putExtra("product_category", category);
        intent.putExtra("product_price", price);
        intent.putExtra("product_image", imageResId);
        intent.putExtra("product_description", description);
        startActivity(intent);
    }
}
