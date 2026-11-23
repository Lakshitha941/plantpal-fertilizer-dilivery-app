package com.s22010022.plantpal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.VideoView;


public class Forfarmers extends AppCompatActivity {
    Button nextBtn;
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forfarmers);
        nextBtn = findViewById(R.id.nextBtn);
        videoView = findViewById(R.id.videoView4);
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.mmm;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();
        nextBtn.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Forfarmers.this, Login.class);
                startActivity(intent);

            }
        });
    }
}