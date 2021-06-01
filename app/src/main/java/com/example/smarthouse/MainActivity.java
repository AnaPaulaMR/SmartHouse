package com.example.smarthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String uid;

    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Initializing SharedPreferences */
        sharedPreferences = getSharedPreferences(SigninActivity.PREFERENCES, MODE_PRIVATE);
        uid = sharedPreferences.getString(SigninActivity.USER_UID, null);

        /* Initializing Database */
        mDatabase = FirebaseDatabase.getInstance().getReference(uid + "/Values");

        // Change if needed
        mDatabase.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double t = dataSnapshot.child("Temperature").getValue(Double.class);
                int w = dataSnapshot.child("Humidity").getValue(Integer.class);

                TextView temp = findViewById(R.id.a2_text_temp);
                TextView water = findViewById(R.id.a2_text_water);

                temp.setText(String.valueOf(t));
                water.setText(String.valueOf(w));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("Values", "Failed to read value.", error.toException());
            }
        });

        /* Initializing Toolbar */
        MaterialToolbar topAppBar = findViewById(R.id.a2_topAppBar);

        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.signout) {
                SigninActivity.signOut();
                finishAffinity();
            } else {
                return false;
            }
            return true;
        });
    }

    /* Function for going to LightSwitchesActivity */
    public void goToLightSwitches(View view) {
        Intent intent = new Intent(this, LightSwitchesActivity.class);
        startActivity(intent);
    }

    /* Function for going to MusicActivity */
    public void goToMusic(View view) {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    /* Function for going to AutomaticLightsActivity */
    public void goToAutomaticLights(View view) {
        Intent intent = new Intent(this, AutomaticLightsActivity.class);
        startActivity(intent);
    }
}