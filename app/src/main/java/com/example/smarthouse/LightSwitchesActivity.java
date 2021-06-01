package com.example.smarthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LightSwitchesActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private String uid;

    private DatabaseReference mDatabase;

    private SwitchMaterial swc_room1;
    private SwitchMaterial swc_room2;
    private SwitchMaterial swc_room3;
    private SwitchMaterial swc_room4;
    private SwitchMaterial swc_room5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_switches);

        /* Initializing SharedPreferences */
        sharedPreferences = getSharedPreferences(SigninActivity.PREFERENCES, MODE_PRIVATE);
        uid = sharedPreferences.getString(SigninActivity.USER_UID, null);

        /* Initializing Database */
        mDatabase = FirebaseDatabase.getInstance().getReference(uid + "/LightSwitches");

        // Change if needed
        mDatabase.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                boolean r1 = dataSnapshot.child("Room1").getValue(Boolean.class);
                boolean r2 = dataSnapshot.child("Room2").getValue(Boolean.class);
                boolean r3 = dataSnapshot.child("Room3").getValue(Boolean.class);
                boolean r4 = dataSnapshot.child("Room4").getValue(Boolean.class);
                boolean r5 = dataSnapshot.child("Room5").getValue(Boolean.class);

                swc_room1 = findViewById(R.id.a3_swc_room1);
                swc_room2 = findViewById(R.id.a3_swc_room2);
                swc_room3 = findViewById(R.id.a3_swc_room3);
                swc_room4 = findViewById(R.id.a3_swc_room4);
                swc_room5 = findViewById(R.id.a3_swc_room5);

                swc_room1.setChecked(r1);
                swc_room2.setChecked(r2);
                swc_room3.setChecked(r3);
                swc_room4.setChecked(r4);
                swc_room5.setChecked(r5);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("LightSwitches", "Failed to read value.", error.toException());
            }
        });

        /* Initializing Toolbar */
        MaterialToolbar topAppBar = findViewById(R.id.a3_topAppBar);

        topAppBar.setNavigationOnClickListener (v -> finish());
    }

    /* Function for setting light switches values on firebase */
    public void setValues(View view) {
        SwitchMaterial swc = findViewById(view.getId());

        if (swc_room1.equals(swc)) {
            if (swc.isChecked()) {
                mDatabase.child("Room1").setValue(true);
            } else {
                mDatabase.child("Room1").setValue(false);
            }
        } else if (swc_room2.equals(swc)) {
            if (swc.isChecked()) {
                mDatabase.child("Room2").setValue(true);
            } else {
                mDatabase.child("Room2").setValue(false);
            }
        } else if (swc_room3.equals(swc)) {
            if (swc.isChecked()) {
                mDatabase.child("Room3").setValue(true);
            } else {
                mDatabase.child("Room3").setValue(false);
            }
        } else if (swc_room4.equals(swc)) {
            if (swc.isChecked()) {
                mDatabase.child("Room4").setValue(true);
            } else {
                mDatabase.child("Room4").setValue(false);
            }
        } else if (swc_room5.equals(swc)) {
            if (swc.isChecked()) {
                mDatabase.child("Room5").setValue(true);
            } else {
                mDatabase.child("Room5").setValue(false);
            }
        }
    }
}