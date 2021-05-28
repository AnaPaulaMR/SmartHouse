package com.example.smarthouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AutomaticLightsActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    private SeekBar seekBar;

    private boolean swcAL;
    private boolean swcT;
    private boolean swcL;

    private int hour = 0;
    private int min = 0;
    private int light = 0;

    private SwitchMaterial swcAutoLights;
    private SwitchMaterial swcTime;
    private SwitchMaterial swcLight;

    private LinearLayout llAutoLights;
    private TextView tvTime;
    private LinearLayout llLight;
    private TextView tvLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_lights);

        /* Initializing Database */
        mDatabase = FirebaseDatabase.getInstance().getReference("AutomaticLights");

        // Change if needed
        mDatabase.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                swcAL = dataSnapshot.child("SwitchAutomaticLights").getValue(Boolean.class);
                swcT = dataSnapshot.child("SwitchTime").getValue(Boolean.class);
                swcL = dataSnapshot.child("SwitchLight").getValue(Boolean.class);

                hour = dataSnapshot.child("Hour").getValue(Integer.class);
                min = dataSnapshot.child("Min").getValue(Integer.class);
                light = dataSnapshot.child("Light").getValue(Integer.class);

                swcAutoLights.setChecked(swcAL);
                swcTime.setChecked(swcT);
                swcLight.setChecked(swcL);

                setTime(hour, min);
                tvLight.setText(light + "%");
                seekBar.setProgress(light);

                if(swcAL) {
                    llAutoLights.setVisibility(View.VISIBLE);
                    if(swcT && swcL) {
                        tvTime.setVisibility(View.VISIBLE);
                        llLight.setVisibility(View.VISIBLE);
                    } else if(swcT) {
                        tvTime.setVisibility(View.VISIBLE);
                        llLight.setVisibility(View.GONE);
                    } else{
                        tvTime.setVisibility(View.GONE);
                        llLight.setVisibility(View.VISIBLE);
                    }
                } else {
                    llAutoLights.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Failed to read value
                Log.w("AutomaticLights", "Failed to read value.", error.toException());
            }
        });

        /* Initializing Variables */
        swcAutoLights = findViewById(R.id.a4_swc_autolights);
        swcTime = findViewById(R.id.a4_swc_time);
        swcLight = findViewById(R.id.a4_swc_light);

        llAutoLights = findViewById(R.id.a4_ll_autolights);
        tvTime = findViewById(R.id.a4_text_time);
        llLight = findViewById(R.id.a4_ll_light);
        tvLight = findViewById(R.id.a4_text_light);

        /* Initializing Toolbar */
        MaterialToolbar topAppBar = findViewById(R.id.a4_topAppBar);

        topAppBar.setNavigationOnClickListener (v -> finish());

        /* Initializing SeekBar */
        seekBar = findViewById(R.id.a4_seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvLight.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mDatabase.child("Light").setValue(seekBar.getProgress());
            }
        });
    }

    /* Function for formatting time to text */
    private void setTime(int h, int m) {
        if(h < 10) {
            if(m < 10) {
                tvTime.setText(0 + h + ":" + 0 + m);
            } else {
                tvTime.setText(0 + h + ":" + m);
            }
        } else if(m < 10) {
            tvTime.setText(h + ":" + 0 + m);
        } else {
            tvTime.setText(h + ":" + m);
        }
    }

    /* Function for turning on and off each switch */
    public void setVisibility(View view) {
        SwitchMaterial swc = findViewById(view.getId());

        if (swcAutoLights.equals(swc)) {
            if (swc.isChecked()) {
                if (!swcTime.isChecked() && !swcLight.isChecked()) {
                    mDatabase.child("SwitchTime").setValue(true);
                    mDatabase.child("SwitchLight").setValue(true);
                }
                mDatabase.child("SwitchAutomaticLights").setValue(true);
            } else {
                mDatabase.child("SwitchAutomaticLights").setValue(false);
            }
        } else if (swcTime.equals(swc)) {
            if (swc.isChecked()) {
                mDatabase.child("SwitchTime").setValue(true);
            } else {
                mDatabase.child("SwitchTime").setValue(false);
            }
        } else if (swcLight.equals(swc)) {
            if (swc.isChecked()) {
                mDatabase.child("SwitchLight").setValue(true);
            } else {
                mDatabase.child("SwitchLight").setValue(false);
            }
        }

        if (!swcTime.isChecked() && !swcLight.isChecked() && swcAL) {
            mDatabase.child("SwitchAutomaticLights").setValue(false);
        }
    }

    /* Function for setting up the TimePicker */
    public void showMaterialTimePicker(View view) {
        MaterialTimePicker picker = new MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setHour(hour)
                .setMinute(min)
                .setTitleText("Select time:")
                .build();

        picker.show(getSupportFragmentManager(), "timePicker");

        picker.addOnPositiveButtonClickListener(v -> {
            hour = picker.getHour();
            min = picker.getMinute();

            setTime(hour, min);

            mDatabase.child("Hour").setValue(hour);
            mDatabase.child("Min").setValue(min);
        });
    }
}