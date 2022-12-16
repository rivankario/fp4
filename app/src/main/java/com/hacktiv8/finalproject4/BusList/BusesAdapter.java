package com.hacktiv8.finalproject4.BusList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hacktiv8.finalproject4.PaymentActivity;
import com.hacktiv8.finalproject4.R;
import com.hacktiv8.finalproject4.SeatActivity;

import java.util.ArrayList;
import java.util.List;

public class BusesAdapter extends RecyclerView.Adapter<BusesAdapter.MyViewHolder> {
    private ArrayList<Buses> busesList;
    private Context context;

    public BusesAdapter(ArrayList<Buses> busesList, Context context){
        this.busesList = busesList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView nameTxt, typeTxt, travelTxt, startTxt,
                endTxt, ratingPointTxt, ratingTxt, seatTxt, bedTxt,
                windowTxt, priceTxt;



        public MyViewHolder(final View view){
            super(view);
            nameTxt = view.findViewById(R.id.busName);
            typeTxt = view.findViewById(R.id.busType);
            travelTxt = view.findViewById(R.id.travelTime);
            startTxt = view.findViewById(R.id.startPoint);
            endTxt = view.findViewById(R.id.endPoint);
            ratingPointTxt = view.findViewById(R.id.ratingPoint);
            ratingTxt = view.findViewById(R.id.rating);
            seatTxt = view.findViewById(R.id.seatQuantity);
            bedTxt = view.findViewById(R.id.bedQuantity);
            windowTxt = view.findViewById(R.id.windowSeats);
            priceTxt = view.findViewById(R.id.price);
        }
    }


    @NonNull
    @Override
    public BusesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bus_cardview, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BusesAdapter.MyViewHolder holder, int position) {
        Buses list = busesList.get(position);

        holder.nameTxt.setText(list.getBusName());
        holder.typeTxt.setText(list.getBusType());
        holder.travelTxt.setText(list.getTravelTime());
        holder.startTxt.setText(list.getStartPoint());
        holder.endTxt.setText(list.getEndPoint());
        holder.ratingPointTxt.setText(list.getRatingPoint());
        holder.ratingTxt.setText(list.getRating());
        holder.seatTxt.setText(list.getSeatAvailable());
        holder.bedTxt.setText(list.getBedAvailable());
        holder.windowTxt.setText(list.getWindowSeats());
        holder.priceTxt.setText("Rp. " + list.getPrice());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendData1 = new Intent(context, SeatActivity.class);
                sendData1.putExtra("dataNamaBus", list.getBusName());
                sendData1.putExtra("dataTipeBus",list.getBusType());
                sendData1.putExtra("dataHargaBus", list.getPrice());
                sendData1.putExtra("dataFromLocation",list.getFromLocation());
                sendData1.putExtra("dataToLocation", list.getToLocation());
                sendData1.putExtra("dataDepartureTime", list.getStartPoint());
                sendData1.putExtra("dataArriveTime", list.getEndPoint());
                sendData1.putExtra("dataTravelTime", list.getTravelTime());
                sendData1.putExtra("dataDate", list.getDate());
                context.startActivity(sendData1);


            }
        });
    }

    @Override
    public int getItemCount() {
        return busesList.size();
    }
}
