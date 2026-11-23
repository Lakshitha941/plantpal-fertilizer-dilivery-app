package com.s22010022.plantpal;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class productdiscription3 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Delegate cleanly to main productdiscription
        Intent forward = new Intent(this, productdiscription.class);
        if (getIntent().getExtras() != null) {
            forward.putExtras(getIntent().getExtras());
        } else {
            forward.putExtra("product_name", "Organic Bone Meal");
            forward.putExtra("product_category", "Organic");
            forward.putExtra("product_price", 95.00);
            forward.putExtra("product_image", R.drawable.img_11);
        }
        startActivity(forward);
        finish();
    }
}
