package com.example.Varsani.Clients;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.Varsani.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MyArtWork extends AppCompatActivity {
    private FloatingActionButton fabUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_art_work);

        fabUpload = findViewById(R.id.fab_upload_artwork);

        fabUpload.setOnClickListener(v -> {
            Intent intent = new Intent(MyArtWork.this, UploadArt.class);
            startActivity(intent);
        });
    }
}