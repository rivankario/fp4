package com.hacktiv8.finalproject4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hacktiv8.finalproject4.BusList.Buses;

public class MainActivity extends AppCompatActivity {
    private FirebaseFirestore db;
    private Button btnMainLogin;
    private Button btnMainRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        btnMainLogin = findViewById(R.id.btnLoginHome);
        btnMainRegister = findViewById(R.id.btnRegisterHome);

        // Debug Seat
        Intent i = new Intent(MainActivity.this, SplashActivity.class);
        startActivity(i);

        db = FirebaseFirestore.getInstance();

        btnMainLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line opening a login activity.
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        });

        btnMainRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line opening a login activity.
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}