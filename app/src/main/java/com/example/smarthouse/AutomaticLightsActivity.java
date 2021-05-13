package com.example.smarthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

public class AutomaticLightsActivity extends AppCompatActivity {

    SwitchMaterial swcAutoLights = null;
    SwitchMaterial swcTime = null;
    SwitchMaterial swcLight = null;

    LinearLayout llAutoLights = null;
    TextView tvTime = null;
    LinearLayout llLight = null;
    TextView tvLight = null;

    int hour = 0;
    int min = 0;
    int light = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatic_lights);

        // Initializing variables
        swcAutoLights = (SwitchMaterial) findViewById(R.id.a4_swc_autolights);
        swcTime = (SwitchMaterial) findViewById(R.id.a4_swc_time);
        swcLight = (SwitchMaterial) findViewById(R.id.a4_swc_light);

        llAutoLights = (LinearLayout) findViewById(R.id.a4_ll_autolights);
        tvTime = (TextView) findViewById(R.id.a4_text_time);
        llLight = (LinearLayout) findViewById(R.id.a4_ll_light);
        tvLight = (TextView) findViewById(R.id.a4_text_light);


        MaterialToolbar topAppBar = findViewById(R.id.a4_topAppBar);

        topAppBar.setNavigationOnClickListener (v -> finish());

        SeekBar seekBar = (SeekBar) findViewById(R.id.a4_seekBar);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvLight.setText(progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // Send to firebase
                seekBar.getProgress();
            }
        });

        // Check if automatic lights is ON on firebase
        boolean swcAL = true;
        boolean swcT = true;
        boolean swcL = true;

        // Check parameters on firebase
        hour = 18;
        min = 0;
        light = 30;

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

    void setTime(int h, int m) {
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

    public void setVisibility(View view) {
        SwitchMaterial swc = (SwitchMaterial) findViewById(view.getId());

        if (swcAutoLights.equals(swc)) {
            if (swc.isChecked()) {
                llAutoLights.setVisibility(View.VISIBLE);
                // Turn ON on firebase
            } else {
                llAutoLights.setVisibility(View.GONE);
                // Turn OFF on firebase
            }
        } else if (swcTime.equals(swc)) {
            if (swc.isChecked()) {
                tvTime.setVisibility(View.VISIBLE);
                // Turn ON on firebase
            } else {
                tvTime.setVisibility(View.GONE);
                // Turn OFF on firebase
            }
        } else if (swcLight.equals(swc)) {
            if (swc.isChecked()) {
                llLight.setVisibility(View.VISIBLE);
                // Turn ON on firebase
            } else {
                llLight.setVisibility(View.GONE);
                // Turn OFF on firebase
            }

            if (!swcTime.isChecked() && !swcLight.isChecked()) {
                swcAutoLights.setChecked(false);
                llAutoLights.setVisibility(View.GONE);
                // Turn OFF on firebase
            }
        }
    }

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

            // // Send to firebase hour and min
        });
    }

    public void showNumberPicker(View view) {

    }
}