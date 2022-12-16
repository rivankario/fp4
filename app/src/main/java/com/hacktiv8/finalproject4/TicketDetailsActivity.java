package com.hacktiv8.finalproject4;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;
import com.hacktiv8.finalproject4.BookingList.BookingLists;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class TicketDetailsActivity extends AppCompatActivity {
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    TextView titlePaymentBookingDetailsID, ticketDetailsSeat, ticketDetailsName, ticketDetailsPhone,ticketDetailsEmail, ticketDetailsPaymentMethod;
    TextView ticketDetailsTotalPrice, ticketDetailsTicketPrice, ticketDetailsBusName, ticketDetailsUniqueCode;
    Button btnConfirmPayment;
    ImageButton btnBack;
    String DataBookingID;
    String DocumentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_details);

        getSupportActionBar().hide();

        db = FirebaseFirestore.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Booking List..");
        progressDialog.show();

        titlePaymentBookingDetailsID = findViewById(R.id.titlePaymentBookingDetailsID);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            DataBookingID = extras.getString("dataBookingID").toString();
        }
        titlePaymentBookingDetailsID.setText(DataBookingID);

        ticketDetailsSeat = findViewById(R.id.ticketDetailsSeat);
        ticketDetailsName = findViewById(R.id.ticketDetailsName);
        ticketDetailsPhone = findViewById(R.id.ticketDetailsPhone);
        ticketDetailsEmail = findViewById(R.id.ticketDetailsEmail);
        ticketDetailsPaymentMethod = findViewById(R.id.ticketDetailsPaymentMethod);
        ticketDetailsTotalPrice = findViewById(R.id.ticketDetailsTotalPrice);
        ticketDetailsTicketPrice = findViewById(R.id.ticketDetailsTicketPrice);
        ticketDetailsBusName = findViewById(R.id.ticketDetailsBusName);
        ticketDetailsUniqueCode = findViewById(R.id.ticketDetailsUniqueCode);

        btnBack = findViewById(R.id.btnBackTicketDetails);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line opening a login activity.
                Intent i = new Intent(TicketDetailsActivity.this, BookingListActivity.class);
                startActivity(i);
            }
        });

        btnConfirmPayment = findViewById(R.id.btnConfirmPayment);
        btnConfirmPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line opening a login activity.
                updatePayment();
                btnConfirmPayment.setVisibility(View.GONE);
            }
        });

        getTicketDetails();

    }

    private void updatePayment(){
        // Get a new write batch
        WriteBatch batch = db.batch();

        DocumentReference sfRef = db.collection("dataBooking").document(DocumentID);
        batch.update(sfRef, "paymentVerified", "1");

        // Commit the batch
        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(TicketDetailsActivity.this, "Payment Verified Success!", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getTicketDetails(){
        // db.collection("dataBus").whereEqualTo("kategori", dataValue)
        db.collection("dataBooking").whereEqualTo("idBooking", DataBookingID)
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
                                String Seat, Name, Phone, Email, PaymentMethod, TotalPrice, TicketPrice, BusName, PaymentVerified;
                                Seat = dc.getDocument().get("seatPosition").toString();
                                Name = dc.getDocument().get("nameUser").toString();
                                Phone = dc.getDocument().get("phoneUser").toString();
                                Email = dc.getDocument().get("emailUser").toString();
                                PaymentMethod = dc.getDocument().get("paymentMethod").toString();
                                TotalPrice = dc.getDocument().get("ticketPrice").toString();
                                TicketPrice = dc.getDocument().get("ticketPrice").toString();
                                BusName = dc.getDocument().get("busName").toString();
                                PaymentVerified = dc.getDocument().get("paymentVerified").toString();
                                System.out.printf("Document ID  : " + dc.getDocument().getId().toString());
                                DocumentID = dc.getDocument().getId().toString();


                                ticketDetailsSeat.setText(Seat);
                                ticketDetailsName.setText(Name);
                                ticketDetailsPhone.setText(Phone);
                                ticketDetailsEmail.setText(Email);
                                ticketDetailsPaymentMethod.setText(PaymentMethod);
                                ticketDetailsTotalPrice.setText(TotalPrice);
                                ticketDetailsTicketPrice.setText(TicketPrice);
                                ticketDetailsBusName.setText(BusName);

                                String strTotalPrice = TotalPrice;
                                double totalPrice = Double.parseDouble(strTotalPrice.replaceAll("[^0-9]", ""));
                                String strUniqueCode = ticketDetailsUniqueCode.getText().toString();
                                double uniqueCode = Double.parseDouble(strUniqueCode.replaceAll("[^0-9]", ""));

                                DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
                                DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

                                formatRp.setCurrencySymbol("");
                                formatRp.setMonetaryDecimalSeparator(',');
                                formatRp.setGroupingSeparator('.');
                                kursIndonesia.setDecimalFormatSymbols(formatRp);
                                ticketDetailsTotalPrice.setText("Rp. " + String.valueOf(kursIndonesia.format((int)totalPrice + uniqueCode)));




                                if(PaymentVerified.equals("1")){
                                    btnConfirmPayment.setVisibility(View.GONE);
                                }

                            }
                            System.out.println(dc.getType());

                            if(progressDialog.isShowing()){
                                progressDialog.dismiss();
                            }
                        }
                    }});
    }
}