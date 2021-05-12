package com.example.smarthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.appbar.MaterialToolbar;

public class MainActivity extends AppCompatActivity {

    private MaterialToolbar topAppBar = null;

    private TextView temp = null;
    private TextView water = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing variables
        topAppBar = (MaterialToolbar) findViewById(R.id.a2_topAppBar);

        temp = (TextView) findViewById(R.id.a2_text_temp);
        water = (TextView) findViewById(R.id.a2_text_water);

        // Initializing activity values
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