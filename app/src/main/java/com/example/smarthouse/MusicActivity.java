package com.example.smarthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.material.appbar.MaterialToolbar;

public class MusicActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        /* Initializing Toolbar */
        MaterialToolbar topAppBar = findViewById(R.id.a5_topAppBar);

        topAppBar.setNavigationOnClickListener (v -> finish());
    }
}