package com.s22010022.plantpal;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class cart1 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Cleanly redirect to main dynamic cart
        startActivity(new Intent(this, cart.class));
        finish();
    }
}
