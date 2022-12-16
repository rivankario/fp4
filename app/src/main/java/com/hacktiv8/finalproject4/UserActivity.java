package com.hacktiv8.finalproject4;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.hacktiv8.finalproject4.BookingList.BookingLists;

public class UserActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView titleUserID;
    Button btnBookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getSupportActionBar().hide();
        mAuth = FirebaseAuth.getInstance();

        titleUserID = findViewById(R.id.titleUserID);
        String userID = mAuth.getCurrentUser().getUid().toString();
        titleUserID.setText(userID);

        btnBookingList = findViewById(R.id.btnBookingList);
        btnBookingList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line opening a login activity.
                Intent i = new Intent(UserActivity.this, BookingListActivity.class);
                startActivity(i);
            }
        });
    }
}