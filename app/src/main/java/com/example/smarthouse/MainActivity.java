package com.example.smarthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MaterialToolbar topAppBar = (MaterialToolbar) findViewById(R.id.a2_topAppBar);

        getValues();

        // Defining refresh function
        topAppBar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.refresh) {
                getValues();
                return true;
            } else {
                return false;
            }
        });
    }

    // Function for getting temperature and humidity from firebase
    void getValues() {
        TextView temp = (TextView) findViewById(R.id.a2_text_temp);
        TextView water = (TextView) findViewById(R.id.a2_text_water);

        //Check values on firebase
        double t = 0;
        double w = 0;

        temp.setText(Double.toString(t));
        water.setText(Double.toString(w));
    }

    // Function for going to LightSwitchesActivity
    public void goToLightSwitches(View view) {
        Intent intent = new Intent(this, LightSwitchesActivity.class);
        startActivity(intent);
    }

    // Function for going to MusicActivity
    public void goToMusic(View view) {
        Intent intent = new Intent(this, MusicActivity.class);
        startActivity(intent);
    }

    // Function for going to AutomaticLightsActivity
    public void goToAutomaticLights(View view) {
        Intent intent = new Intent(this, AutomaticLightsActivity.class);
        startActivity(intent);
    }
}