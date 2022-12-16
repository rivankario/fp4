package com.hacktiv8.finalproject4;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.HashMap;
import java.util.Map;

public class PaymentActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db;

    ImageButton backButton;
    TextView titleUniqueCode,titleTicketPrice,titleTotalHarga,titleCouponDiscount;
    TextView titleNamaBus, titleHargaBus, titleTipeBus, titleFromLocation, titleToLocation, titlePaymentBookingID;
    TextView titleSeatNumber, titlePaymentMethod;
    EditText dateHome;
    EditText txtCoupon,txtPassengerName, txtPassengerEmail, txtPassengerPhone;
    Button btnAddCoupon, btnContinue;

    String dataBookingId= "";
    String dataNamaBus = "";
    String dataHargaBus = "";
    String dataTipeBus= "";
    String dataFromLocation= "";
    String dataToLocation= "";
    String dataSeatBus= "";
    String dataDepartureBus= "";
    String dataArriveBus= "";
    String dataTravelTime = "";
    String dataDate = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ///// TICKET PRICE MASIH 90.000 SAAT COUPON DIGUNAKAN ///
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().hide();

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            dataNamaBus = extras.getString("dataNamaBus").toString();
            dataHargaBus = extras.getString("dataHargaBus").toString();
            dataTipeBus = extras.getString("dataTipeBus").toString();
            dataFromLocation = extras.getString("dataFromLocation").toString();
            dataToLocation = extras.getString("dataToLocation").toString();
            dataSeatBus = extras.getString("dataSeatBus").toString();
            dataBookingId = extras.getString("dataBookingID").toString();
            dataDepartureBus = extras.getString("dataDepartureTime").toString();
            dataArriveBus = extras.getString("dataArriveTime").toString();
            dataTravelTime = getIntent().getStringExtra("dataTravelTime").toString();
            dataTravelTime = getIntent().getStringExtra("dataTravelTime").toString();
            dataDate = getIntent().getStringExtra("dataDate").toString();
        }


        backButton = findViewById(R.id.btnBackPayment);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaymentActivity.super.finish();
            }
        });

        btnAddCoupon = findViewById(R.id.btnAddCoupon);

        txtPassengerName = findViewById(R.id.txtPassengerName);
        txtPassengerEmail = findViewById(R.id.txtPassengerEmail);
        txtPassengerPhone = findViewById(R.id.txtPassengerPhone);

        titleTotalHarga = findViewById(R.id.titleTotalPrice);
        titleTicketPrice = findViewById(R.id.titleTicketPrice);
        titleCouponDiscount = findViewById(R.id.couponDiscount);
        titleUniqueCode = findViewById(R.id.titleUniqueCode);
        titlePaymentBookingID = findViewById(R.id.titlePaymentBookingID);

        titleNamaBus = findViewById(R.id.titleBusName);
        titleHargaBus = findViewById(R.id.titleTicketPrice);
        titleTipeBus = findViewById(R.id.titleBusType);
        titleFromLocation = findViewById(R.id.titleFromLocation);
        titleToLocation = findViewById(R.id.titleToLocation);
        titleSeatNumber = findViewById(R.id.titleSeatNumber);
        titlePaymentMethod = findViewById(R.id.titlePaymentMethod);

        titleNamaBus.setText(dataNamaBus);
        titleHargaBus.setText("Rp. " + dataHargaBus);
        titleTipeBus.setText(dataTipeBus);
        titleFromLocation.setText(dataFromLocation);
        titleToLocation.setText(dataToLocation);
        titleSeatNumber.setText(dataSeatBus);
        titlePaymentBookingID.setText(dataBookingId);

        DecimalFormat kursIndonesia = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols formatRp = new DecimalFormatSymbols();

        formatRp.setCurrencySymbol("");
        formatRp.setMonetaryDecimalSeparator(',');
        formatRp.setGroupingSeparator('.');
        kursIndonesia.setDecimalFormatSymbols(formatRp);

        String strTicketPrice = titleTicketPrice.getText().toString();
        String strUniqueCode = titleUniqueCode.getText().toString();
        double hargaTiket = Double.parseDouble(strTicketPrice.replaceAll("[^0-9]", ""));
        double uniqueCode = Double.parseDouble(strUniqueCode.replaceAll("[^0-9]", ""));
        titleTotalHarga.setText("Rp. " + String.valueOf(kursIndonesia.format((int)hargaTiket + uniqueCode)));



        txtCoupon = findViewById(R.id.txtCoupon);
        btnAddCoupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTicketPrice = titleTicketPrice.getText().toString();
                String strUniqueCode = titleUniqueCode.getText().toString();

                double hargaTiket = Double.parseDouble(strTicketPrice.replaceAll("[^0-9]", ""));
                double uniqueCode = Double.parseDouble(strUniqueCode.replaceAll("[^0-9]", ""));

                String strCoupon = txtCoupon.getText().toString();
                if(strCoupon.equals("TRAVEL10")){
                    DecimalFormat df = new DecimalFormat("0");
                    double couponDiscount = hargaTiket*0.1;
                    double totalPriceNew = hargaTiket - couponDiscount + uniqueCode;
                    titleTotalHarga.setText("Rp. " + String.valueOf(df.format(totalPriceNew)));
                    titleCouponDiscount.setText("Rp. " + String.valueOf(df.format(hargaTiket*0.1)));

                    titleTotalHarga.setText("Rp. " + String.valueOf(kursIndonesia.format((int)totalPriceNew)));
                }
            }
        });




        btnContinue = findViewById(R.id.btnContinueBooking);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addBooking();
            }
        });

    }

    public void sentIntent(){

    }

    public void addBooking(){
        Map<String, Object> dataBooking = new HashMap<>();

        String dataUserID= mAuth.getCurrentUser().getUid().toString();
        String dataNamaUser= txtPassengerName.getText().toString();
        String dataEmailUser= txtPassengerEmail.getText().toString();
        String dataPhoneUser= txtPassengerPhone.getText().toString();
        String paymentMethod = titlePaymentMethod.getText().toString();

        dataBooking.put("idBooking", dataBookingId );
        dataBooking.put("idUser", dataUserID);
        dataBooking.put("nameUser", dataNamaUser);
        dataBooking.put("emailUser", dataEmailUser);
        dataBooking.put("phoneUser", dataPhoneUser);
        dataBooking.put("busName", dataNamaBus);
        dataBooking.put("busType", dataTipeBus);
        dataBooking.put("fromLocation", dataFromLocation);
        dataBooking.put("toLocation", dataToLocation);
        dataBooking.put("departureTime", dataDepartureBus);
        dataBooking.put("arriveTime", dataArriveBus);
        dataBooking.put("travelTime", dataTravelTime);
        dataBooking.put("dateBooking", dataDate);
        dataBooking.put("seatPosition", dataSeatBus);
        dataBooking.put("ticketPrice", dataHargaBus);
        dataBooking.put("paymentMethod", paymentMethod);
        dataBooking.put("paymentVerified", "0");

        db.collection("dataBooking")
                .add(dataBooking).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(getApplicationContext(),"Booking Success",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(PaymentActivity.this, BookingListActivity.class);
                        startActivity(i);
                    }
                }). addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });

    }
}