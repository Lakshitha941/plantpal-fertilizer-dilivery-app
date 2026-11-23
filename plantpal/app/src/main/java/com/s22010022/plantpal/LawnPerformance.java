package com.s22010022.plantpal;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class LawnPerformance extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Delegate to product description
        Intent intent = new Intent(this, productdiscription.class);
        intent.putExtra("product_name", "Lawn Performance Blend");
        intent.putExtra("product_category", "Blends");
        intent.putExtra("product_price", 160.00);
        intent.putExtra("product_image", R.drawable.img_30);
        intent.putExtra("product_description", "Specially engineered slow-release turf booster designed to maintain rich, lush green color and dense grass turf.");
        startActivity(intent);
        finish();
    }
}
