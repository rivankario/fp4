package com.hacktiv8.finalproject4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.Random;

public class SeatActivity extends AppCompatActivity implements View.OnClickListener{
    private Button btnDone;
    private Button[] btn = new Button[36];
    String selectedbtn = "";
    Boolean pressed = false;
    private String[] selectedSeat = {"0"};
    private int[] btn_id = {R.id.btn1A, R.id.btn1B, R.id.btn1C, R.id.btn1D,
            R.id.btn2A, R.id.btn2B, R.id.btn2C, R.id.btn2D,
            R.id.btn3A, R.id.btn3B, R.id.btn3C, R.id.btn3D,
            R.id.btn4A, R.id.btn4B, R.id.btn4C, R.id.btn4D,
            R.id.btn5A, R.id.btn5B, R.id.btn5C, R.id.btn5D,
            R.id.btn6A, R.id.btn6B, R.id.btn6C, R.id.btn6D,
            R.id.btn7A, R.id.btn7B, R.id.btn7C, R.id.btn7D,
            R.id.btn8A, R.id.btn8B, R.id.btn8C, R.id.btn8D,
            R.id.btn9A, R.id.btn9B, R.id.btn9C, R.id.btn9D};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seat);
        // Action Bar Back
        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setTitle("Select Seat");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);




        for(int i = 0; i < btn.length; i++){
            Boolean selected = false;
            btn[i] = findViewById(btn_id[i]);
            for (int x = 0; x<selectedSeat.length; x++){
                if (btn[i].getText().equals(selectedSeat[x])){
                    btn[i].setBackgroundColor(Color.rgb(185, 138, 255));
                    btn[i].setEnabled(false);
                    selected = true;
                }
            }
            if(!selected){
                btn[i].setBackgroundColor(Color.rgb(207, 207, 207));
                btn[i].setOnClickListener(this);
            }


        }

        btnDone = findViewById(R.id.btnDone);
        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line opening a login activity.
                Toast.makeText(SeatActivity.this, "Selected Seat : "+selectedbtn, Toast.LENGTH_SHORT).show();

                Intent sendData1 = new Intent(SeatActivity.this, PaymentActivity.class);

                String dataNama = getIntent().getStringExtra("dataNamaBus").toString();
                String dataHarga = getIntent().getStringExtra("dataHargaBus").toString();
                String dataTipe = getIntent().getStringExtra("dataTipeBus").toString();
                String dataFromLocation = getIntent().getStringExtra("dataFromLocation").toString();
                String dataToLocation = getIntent().getStringExtra("dataToLocation").toString();
                String dataDepartureTime = getIntent().getStringExtra("dataDepartureTime").toString();
                String dataArriveTime = getIntent().getStringExtra("dataArriveTime").toString();
                String dataTravelTime = getIntent().getStringExtra("dataTravelTime").toString();
                String dataDate = getIntent().getStringExtra("dataDate").toString();

                Random rnd = new Random();
                int n = 1000000 + rnd.nextInt(9000000);
                String strBookingID = String.valueOf(n);
                sendData1.putExtra("dataNamaBus", dataNama);
                sendData1.putExtra("dataHargaBus",dataHarga);
                sendData1.putExtra("dataTipeBus", dataTipe);
                sendData1.putExtra("dataFromLocation",dataFromLocation);
                sendData1.putExtra("dataToLocation", dataToLocation);
                sendData1.putExtra("dataFromLocation",dataDepartureTime);
                sendData1.putExtra("dataToLocation", dataArriveTime);
                sendData1.putExtra("dataSeatBus", selectedbtn.toString());
                sendData1.putExtra("dataBookingID", strBookingID);
                sendData1.putExtra("dataDepartureTime", dataDepartureTime);
                sendData1.putExtra("dataArriveTime", dataArriveTime);
                sendData1.putExtra("dataTravelTime", dataTravelTime);
                sendData1.putExtra("dataDate", dataDate);
                SeatActivity.this.startActivity(sendData1);

            }
        });


    }

    public void disableAllButton(Button index){
        for(int i = 0; i < btn.length; i++){
            if (btn[i] == index){

            } else {
                btn[i].setEnabled(false);
                btn[i].setOnClickListener(this);
            }

        }
    }

    public void enableAllButton(){
        for(int i = 0; i < btn.length; i++){
            Boolean selected = false;
            for (int x = 0; x<selectedSeat.length; x++){
                if (btn[i].getText().equals(selectedSeat[x])){
                    btn[i].setEnabled(false);
                    selected = true;
                }
            }
            if (!selected){
                btn[i].setEnabled(true);
                btn[i].setOnClickListener(this);
            }

        }
    }

    private void setFocus(Button btn_focus){
        btn_focus.setBackgroundColor(Color.rgb(255, 168, 44));
    }

    private void setUnfocus(Button btn_focus){
        btn_focus.setBackgroundColor(Color.rgb(207, 207, 207));
    }


    @Override
    public void onClick(View v) {
        if(!pressed){
            setFocus((Button) findViewById(v.getId()));
            Button btn = (Button) findViewById(v.getId());
            selectedbtn = btn.getText().toString();

            pressed = true;
            disableAllButton((Button) findViewById(v.getId()));
        } else{
            setUnfocus((Button) findViewById(v.getId()));
            pressed = false;
            enableAllButton();

        }
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}