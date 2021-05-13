package com.example.smarthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.switchmaterial.SwitchMaterial;

public class LightSwitchesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_switches);

        MaterialToolbar topAppBar = (MaterialToolbar) findViewById(R.id.a3_topAppBar);

        getValues();

        // Defining back function
        topAppBar.setNavigationOnClickListener (v -> finish());

        // Defining refresh function
        topAppBar.setOnMenuItemClickListener(item -> {
            if(item.getItemId() == R.id.refresh) {
                getValues();
                return true;
            } else {
                return false;
            }
        });
    }

    // Function for getting light switches values from firebase
    void getValues() {
        SwitchMaterial swc_room1 = (SwitchMaterial) findViewById(R.id.a3_swc_room1);
        SwitchMaterial swc_room2 = (SwitchMaterial) findViewById(R.id.a3_swc_room2);
        SwitchMaterial swc_room3 = (SwitchMaterial) findViewById(R.id.a3_swc_room3);
        SwitchMaterial swc_room4 = (SwitchMaterial) findViewById(R.id.a3_swc_room4);
        SwitchMaterial swc_room5 = (SwitchMaterial) findViewById(R.id.a3_swc_room5);

        // Check values on firebase
        boolean r1 = true;
        boolean r2 = true;
        boolean r3 = true;
        boolean r4 = true;
        boolean r5 = true;

        swc_room1.setChecked(r1);
        swc_room2.setChecked(r2);
        swc_room3.setChecked(r3);
        swc_room4.setChecked(r4);
        swc_room5.setChecked(r5);
    }

    // Function for getting light switch value from firebase given the light switch
    boolean getValue(SwitchMaterial room) {
        // Get value from firebase
        boolean r = true;
        return r;
    }

    // Function for setting light switches values on firebase
    public void setValues(View view) {
        SwitchMaterial room = (SwitchMaterial) findViewById(view.getId());
        boolean r = getValue(room);

        if(room.isChecked() != r) {
            //change it on firebase
        }

        // Refresh values (uncomment when connected to firebase)
        //getValues();
    }
}