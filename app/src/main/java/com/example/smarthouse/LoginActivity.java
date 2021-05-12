package com.example.smarthouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    // Login function
    public void doLogin(View view) {
        ArrayList<String> Username = new ArrayList<String>();
        Username.add("Ana");
        String Password = "1234";

        EditText txtUsername = (EditText) findViewById(R.id.a1_edit_username);
        EditText txtPassword = (EditText) findViewById(R.id.a1_edit_password);

        String strUsername = txtUsername.getText().toString();
        String strPassword = txtPassword.getText().toString();

        if(Username.contains(strUsername) && strPassword.equals(Password)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(getApplicationContext(),"Invalid username or password", Toast.LENGTH_SHORT).show();
        }
    }
}