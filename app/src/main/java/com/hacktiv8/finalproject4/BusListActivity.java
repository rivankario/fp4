package com.hacktiv8.finalproject4;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import com.hacktiv8.finalproject4.BusList.Buses;
import com.hacktiv8.finalproject4.BusList.BusesAdapter;

import java.util.ArrayList;

public class BusListActivity extends AppCompatActivity {

    private FirebaseFirestore db;
    ProgressDialog progressDialog;

    private TextView filterButton;
    private ArrayList<Buses> busesList;
    private BusesAdapter busAdapter;
    private RecyclerView recyclerView;

    String fromLocation = "";
    String toLocation = "";
    String date = "";
    TextView titleFromLocation, titleToLocation, titleDateBusList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Hiding the supportBar*/
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_bus_list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data..");
        progressDialog.show();

        // initializing our variables.
        recyclerView = findViewById(R.id.busRecyclerView);

        titleFromLocation = findViewById(R.id.fromBusLocationTitle);
        titleToLocation = findViewById(R.id.toBusLocationTitle);
        titleDateBusList = findViewById(R.id.titleBusListDate);

        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // creating our new array list
        busesList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        busAdapter = new BusesAdapter(busesList, this);

        // setting adapter to our recycler view.
        recyclerView.setAdapter(busAdapter);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            fromLocation = extras.getString("fromLocation").toString();
            toLocation = extras.getString("toLocation").toString();
            date = extras.getString("date").toString();
        }

        titleFromLocation.setText(fromLocation);
        titleToLocation.setText(toLocation);
        titleDateBusList.setText(date);

        //setBusesInfo();
        LoadBusFromFirebase();

//        busesList.add(new Buses("Bus1", "Non-AC Seater", "10 hrs", "8:00 PM"
//                ,"6:00 AM", "3.0", "829","14","0","4","Rp 200K", "Jakarta", "Bandung"));


    }

    private void LoadBusFromFirebase(){
        // db.collection("dataBus").whereEqualTo("kategori", dataValue)
        db.collection("dataBus").whereEqualTo("fromLocation", fromLocation).whereEqualTo("toLocation", toLocation)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if(progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }

                        if (error != null){
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }

                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                String busName, busType, travelTime, departureTime, arriveTime;
                                String rating, jumlahRating, totalSeat, totalBed, windowSeats, ticketPrice;
                                String fromLocation, toLocation;

                                busName = dc.getDocument().get("busName").toString();
                                busType = dc.getDocument().get("busType").toString();
                                departureTime = dc.getDocument().get("departureTime").toString();
                                arriveTime = dc.getDocument().get("arriveTime").toString();
                                travelTime = dc.getDocument().get("travelTime").toString();
                                totalSeat = dc.getDocument().get("totalSeat").toString();
                                totalBed = dc.getDocument().get("totalBed").toString();
                                ticketPrice = dc.getDocument().get("ticketPrice").toString();
                                rating = dc.getDocument().get("rating").toString();
                                jumlahRating = "-";
                                windowSeats = "4";

                                fromLocation = dc.getDocument().get("fromLocation").toString();
                                toLocation = dc.getDocument().get("toLocation").toString();
                                System.out.println("fromLocation : " + dc.getDocument().get("toLocation").toString());

                                busesList.add(new Buses(busName,busType,travelTime,departureTime,arriveTime,rating, jumlahRating, totalSeat, totalBed,
                                        windowSeats, ticketPrice, fromLocation, toLocation,date));
                            }
                            System.out.println(dc.getType());
                            busAdapter.notifyDataSetChanged();

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }});
    }
}