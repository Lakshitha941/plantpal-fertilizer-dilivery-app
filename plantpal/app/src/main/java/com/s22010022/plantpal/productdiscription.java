package com.s22010022.plantpal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class productdiscription extends AppCompatActivity {
    private TextView tvProductName, tvProductType, tvProductDesc;
    private ImageView ivProductImage;
    private Button btnBuyNow, btnAddToCart;
    private databaseHelper db;
    private SessionManager sessionManager;

    private String productName;
    private String productCategory;
    private double productPrice;
    private int productImageRes;
    private String productDesc;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdiscription);

        db = new databaseHelper(this);
        sessionManager = new SessionManager(this);

        tvProductName = findViewById(R.id.productName);
        tvProductType = findViewById(R.id.productType);
        tvProductDesc = findViewById(R.id.productDesc);
        ivProductImage = findViewById(R.id.productImage);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        // Retrieve data passed from previous activity or use defaults
        Intent intent = getIntent();
        productName = intent.getStringExtra("product_name");
        if (productName == null) productName = "Organic Compost";

        productCategory = intent.getStringExtra("product_category");
        if (productCategory == null) productCategory = "Organic";

        productPrice = intent.getDoubleExtra("product_price", 125.00);
        productImageRes = intent.getIntExtra("product_image", R.drawable.img_18);

        productDesc = intent.getStringExtra("product_description");
        if (productDesc == null) {
            productDesc = "Made from locally recycled garden prunings, food products and vegetable trimmings. Contains no biosolids or steer manure.";
        }

        // Bind data to UI
        if (tvProductName != null) tvProductName.setText(productName);
        if (tvProductType != null) tvProductType.setText(productCategory + " - $" + productPrice);
        if (tvProductDesc != null) tvProductDesc.setText(productDesc);
        if (ivProductImage != null) ivProductImage.setImageResource(productImageRes);

        if (btnAddToCart != null) {
            btnAddToCart.setOnClickListener(v -> {
                boolean added = db.addToCart(sessionManager.getUsername(), productName, productCategory, productPrice, 1, productImageRes);
                if (added) {
                    Toast.makeText(productdiscription.this, productName + " added to cart! 🛒", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(productdiscription.this, "Failed to add to cart", Toast.LENGTH_SHORT).show();
                }
            });
        }

        if (btnBuyNow != null) {
            btnBuyNow.setOnClickListener(v -> {
                db.addToCart(sessionManager.getUsername(), productName, productCategory, productPrice, 1, productImageRes);
                Intent cartIntent = new Intent(productdiscription.this, cart.class);
                startActivity(cartIntent);
            });
        }
    }
}
