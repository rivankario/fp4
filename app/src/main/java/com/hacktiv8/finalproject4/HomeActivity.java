package com.hacktiv8.finalproject4;

import androidx.appcompat.app.AppCompatActivity;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.firestore.auth.User;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    EditText date;
    Button search_buses;
    DatePickerDialog datePickerDialog;
    SimpleDateFormat dateFormatter;
    String[] From =  {"Jakarta","Bandung","Surabaya","Semarang","Yogyakarta"};
    String[] Destination =  {"Jakarta","Bandung","Surabaya","Semarang","Yogyakarta"};
    AutoCompleteTextView autoCompleteTxt;
    AutoCompleteTextView autoCompleteTxt2;
    ArrayAdapter<String> adapterItems;

    ImageView btnUser;
    String fromLocation = "";
    String toLocation = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        autoCompleteTxt2 = findViewById(R.id.auto_complete_txt2);
        date = (EditText)findViewById(R.id.date);

        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,From);
        autoCompleteTxt.setAdapter(adapterItems);
        adapterItems = new ArrayAdapter<String>(this,R.layout.list_item,Destination);
        autoCompleteTxt2.setAdapter(adapterItems);

        autoCompleteTxt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                fromLocation = item;
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });
        autoCompleteTxt2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                toLocation = item;
                Toast.makeText(getApplicationContext(),"Item: "+item,Toast.LENGTH_SHORT).show();
            }
        });
        search_buses = findViewById(R.id.search);
        search_buses.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), BusListActivity.class);
            intent.putExtra("fromLocation", fromLocation);
            intent.putExtra("toLocation", toLocation);
            intent.putExtra("date", date.getText().toString());

            startActivity(intent);
        });



        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog();
            }
        });

        btnUser = findViewById(R.id.imgUserHome);
        btnUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, UserActivity.class);
                startActivity(i);
            }
        });

    }

    private void showDateDialog() {
        Calendar calendar = Calendar.getInstance();

        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                date.setText(dateFormatter.format(newDate.getTime()));
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();

    }
}