package com.s22010022.plantpal;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Locale;

public class cart extends AppCompatActivity {
    private Button btnCheckout;
    private LinearLayout cartContainer;
    private databaseHelper db;
    private SessionManager sessionManager;
    private double currentTotal = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        db = new databaseHelper(this);
        sessionManager = new SessionManager(this);

        cartContainer = findViewById(R.id.cartItemsContainer);
        btnCheckout = findViewById(R.id.buttonCheckout);

        btnCheckout.setOnClickListener(v -> {
            if (currentTotal <= 0.0) {
                Toast.makeText(cart.this, "Your cart is empty! Please add products.", Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(cart.this, PaymentPage.class);
                intent.putExtra("total_amount", currentTotal);
                startActivity(intent);
            }
        });

        setupBottomNav();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCartItems();
    }

    @SuppressLint("SetTextI18n")
    private void loadCartItems() {
        if (cartContainer == null) return;
        cartContainer.removeAllViews();
        currentTotal = 0.0;

        String userName = sessionManager.getUsername();
        Cursor cursor = db.getCartItems(userName);

        if (cursor == null || cursor.getCount() == 0) {
            if (cursor != null) cursor.close();

            // Empty cart view
            TextView emptyTv = new TextView(this);
            emptyTv.setText("Your cart is currently empty.\nPlease add items from the store.");
            emptyTv.setTextSize(16);
            emptyTv.setGravity(Gravity.CENTER);
            emptyTv.setPadding(32, 64, 32, 64);
            emptyTv.setTextColor(Color.DKGRAY);
            cartContainer.addView(emptyTv);

            btnCheckout.setText("Cart is Empty");
            return;
        }

        while (cursor.moveToNext()) {
            int cartId = cursor.getInt(cursor.getColumnIndexOrThrow(databaseHelper.COL_CART_ID));
            String pName = cursor.getString(cursor.getColumnIndexOrThrow(databaseHelper.COL_CART_PRODUCT));
            String pCat = cursor.getString(cursor.getColumnIndexOrThrow(databaseHelper.COL_CART_CATEGORY));
            double price = cursor.getDouble(cursor.getColumnIndexOrThrow(databaseHelper.COL_CART_PRICE));
            int qty = cursor.getInt(cursor.getColumnIndexOrThrow(databaseHelper.COL_CART_QTY));
            int imgRes = cursor.getInt(cursor.getColumnIndexOrThrow(databaseHelper.COL_CART_IMAGE));

            currentTotal += (price * qty);

            // Create card for cart item
            CardView card = new CardView(this);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            cardParams.setMargins(0, 0, 0, 24);
            card.setLayoutParams(cardParams);
            card.setRadius(24f);
            card.setCardElevation(8f);
            card.setContentPadding(24, 24, 24, 24);

            LinearLayout row = new LinearLayout(this);
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setGravity(Gravity.CENTER_VERTICAL);

            // Product Image
            ImageView iv = new ImageView(this);
            LinearLayout.LayoutParams ivParams = new LinearLayout.LayoutParams(160, 160);
            iv.setLayoutParams(ivParams);
            iv.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (imgRes != 0) iv.setImageResource(imgRes);
            else iv.setImageResource(R.drawable.img_18);
            row.addView(iv);

            // Info column
            LinearLayout infoCol = new LinearLayout(this);
            infoCol.setOrientation(LinearLayout.VERTICAL);
            LinearLayout.LayoutParams infoParams = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
            infoParams.setMargins(24, 0, 16, 0);
            infoCol.setLayoutParams(infoParams);

            TextView tvName = new TextView(this);
            tvName.setText(pName);
            tvName.setTextSize(16);
            tvName.setTypeface(null, Typeface.BOLD);
            tvName.setTextColor(Color.BLACK);
            infoCol.addView(tvName);

            TextView tvCategory = new TextView(this);
            tvCategory.setText(pCat + " | $" + String.format(Locale.US, "%.2f", price) + " each");
            tvCategory.setTextSize(13);
            tvCategory.setTextColor(Color.GRAY);
            infoCol.addView(tvCategory);

            // Quantity controls row (+ / -)
            LinearLayout qtyRow = new LinearLayout(this);
            qtyRow.setOrientation(LinearLayout.HORIZONTAL);
            qtyRow.setGravity(Gravity.CENTER_VERTICAL);
            qtyRow.setPadding(0, 12, 0, 0);

            Button btnMinus = new Button(this);
            btnMinus.setText("-");
            btnMinus.setTextSize(16);
            btnMinus.setLayoutParams(new LinearLayout.LayoutParams(90, 80));
            btnMinus.setOnClickListener(v -> {
                db.updateCartQuantity(cartId, qty - 1);
                loadCartItems();
            });

            TextView tvQty = new TextView(this);
            tvQty.setText("  " + qty + "  ");
            tvQty.setTextSize(15);
            tvQty.setTypeface(null, Typeface.BOLD);

            Button btnPlus = new Button(this);
            btnPlus.setText("+");
            btnPlus.setTextSize(16);
            btnPlus.setLayoutParams(new LinearLayout.LayoutParams(90, 80));
            btnPlus.setOnClickListener(v -> {
                db.updateCartQuantity(cartId, qty + 1);
                loadCartItems();
            });

            qtyRow.addView(btnMinus);
            qtyRow.addView(tvQty);
            qtyRow.addView(btnPlus);
            infoCol.addView(qtyRow);

            row.addView(infoCol);

            // Remove button
            Button btnRemove = new Button(this);
            btnRemove.setText("✕");
            btnRemove.setTextSize(14);
            btnRemove.setTextColor(Color.WHITE);
            btnRemove.setBackgroundColor(Color.parseColor("#E53935"));
            btnRemove.setLayoutParams(new LinearLayout.LayoutParams(90, 80));
            btnRemove.setOnClickListener(v -> {
                db.deleteCartItem(cartId);
                Toast.makeText(cart.this, "Item removed", Toast.LENGTH_SHORT).show();
                loadCartItems();
            });
            row.addView(btnRemove);

            card.addView(row);
            cartContainer.addView(card);
        }
        cursor.close();

        btnCheckout.setText("Checkout ($" + String.format(Locale.US, "%.2f", currentTotal) + ")");
    }

    private void setupBottomNav() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setSelectedItemId(R.id.navigation_cart);
            bottomNav.setOnItemSelectedListener(item -> {
                int itemId = item.getItemId();
                if (itemId == R.id.navigation_dashboard) {
                    startActivity(new Intent(cart.this, Dashbord.class));
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_cart) {
                    return true;
                } else if (itemId == R.id.navigation_setting) {
                    startActivity(new Intent(cart.this, setting.class));
                    finish();
                    return true;
                } else if (itemId == R.id.navigation_profile) {
                    startActivity(new Intent(cart.this, account.class));
                    finish();
                    return true;
                }
                return false;
            });
        }
    }
}
