package com.hacktiv8.finalproject4;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.hacktiv8.finalproject4.BookingList.BookingLists;
import com.hacktiv8.finalproject4.BookingList.ListBookingAdapter;
import com.hacktiv8.finalproject4.BusList.Buses;
import com.hacktiv8.finalproject4.BusList.BusesAdapter;

import java.util.ArrayList;
import java.util.Objects;

public class BookingListActivity extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    private ArrayList<BookingLists> bookingList;
    RecyclerView recyclerView;
    ListBookingAdapter bookingListAdapter;

    ImageButton btnBack;

    ArrayList<String> BookingID = new ArrayList<>();
    ArrayList<String> BusName = new ArrayList<>();
    ArrayList<String> TravelTime = new ArrayList<>();
    ArrayList<String> DepartureTime = new ArrayList<>();
    ArrayList<String> ArriveTime = new ArrayList<>();
    ArrayList<String> Seat = new ArrayList<>();
    ArrayList<String> TotalPrice = new ArrayList<>();

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_booking_list);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Booking List..");
        progressDialog.show();

        // initializing our variables.
        recyclerView = findViewById(R.id.rvBookingList);
        btnBack = findViewById(R.id.btnBackBookingList);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line opening a login activity.
                Intent i = new Intent(BookingListActivity.this, UserActivity.class);
                startActivity(i);
            }
        });

        // firestore and firebase auth getting its instance.
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        userID = auth.getCurrentUser().getUid().toString();

        // creating our new array list
        bookingList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // adding our array list to our recycler view adapter class.
        bookingListAdapter = new ListBookingAdapter(bookingList, this);

        // setting adapter to our recycler view.
        recyclerView.setAdapter(bookingListAdapter);

        getBookingList();
    }

    private void getBookingList(){
        // db.collection("dataBus").whereEqualTo("kategori", dataValue)
        db.collection("dataBooking").whereEqualTo("idUser", userID)
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
                                String BookingList, BusName, TravelTime, DepartureTime, ArriveTime, Seat, TotalPrice;


                                BookingList = dc.getDocument().get("idBooking").toString();
                                BusName = dc.getDocument().get("busName").toString();
                                TravelTime = dc.getDocument().get("travelTime").toString();
                                DepartureTime = dc.getDocument().get("departureTime").toString();
                                ArriveTime = dc.getDocument().get("arriveTime").toString();
                                Seat = dc.getDocument().get("seatPosition").toString();
                                TotalPrice = dc.getDocument().get("ticketPrice").toString();

                                bookingList.add(new BookingLists(BookingList,BusName,TravelTime,DepartureTime,ArriveTime,Seat,TotalPrice));
                            }
                            System.out.println(dc.getType());
                            bookingListAdapter.notifyDataSetChanged();

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }});
    }


}