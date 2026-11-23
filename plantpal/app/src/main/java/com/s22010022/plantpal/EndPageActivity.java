package com.s22010022.plantpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class EndPageActivity extends AppCompatActivity {
    private Button homeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.end);

        homeBtn = findViewById(R.id.btnHome);
        if (homeBtn != null) {
            homeBtn.setOnClickListener(v -> {
                Intent intent = new Intent(EndPageActivity.this, Dashbord.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }
    }
}
