package com.hacktiv8.finalproject4.BookingList;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hacktiv8.finalproject4.BookingList.BookingLists;
import com.hacktiv8.finalproject4.R;
import com.hacktiv8.finalproject4.SeatActivity;
import com.hacktiv8.finalproject4.TicketDetailsActivity;

import java.util.ArrayList;

public class ListBookingAdapter extends RecyclerView.Adapter<ListBookingAdapter.MyViewHolder>{

    private ArrayList<BookingLists> BookingLists;
    private Context context;

    public ListBookingAdapter(ArrayList<BookingLists> BookingListsList, Context context){
        this.BookingLists = BookingListsList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView titleBookingID, titleBusName, titleTravelTime, titleDepartureTime,
                titleArriveTime, titleSeat, titleTotal;

        public MyViewHolder(final View view){
            super(view);
            titleBookingID = view.findViewById(R.id.RVtitleBookingID);
            titleBusName = view.findViewById(R.id.RVtitleBusName);
            titleTravelTime = view.findViewById(R.id.RVtravelTime);
            titleDepartureTime = view.findViewById(R.id.RVstartPoint);
            titleArriveTime = view.findViewById(R.id.RVendPoint);
            titleSeat = view.findViewById(R.id.RVseatBookingList);
            titleTotal = view.findViewById(R.id.RVpriceBookingList);

        }
    }


    @NonNull
    @Override
    public ListBookingAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bookinglist_cardview, parent, false);
        return new ListBookingAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListBookingAdapter.MyViewHolder holder, int position) {
        BookingLists list = BookingLists.get(position);

        holder.titleBookingID.setText(list.getBookingID());
        holder.titleBusName.setText(list.getBusName());
        holder.titleTravelTime.setText(list.getTravelTime());
        holder.titleDepartureTime.setText(list.getDepartureTime());
        holder.titleArriveTime.setText(list.getArriveTime());
        holder.titleSeat.setText(list.getSeat());
        holder.titleTotal.setText("Rp. " + list.getTotal());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendData1 = new Intent(context, TicketDetailsActivity.class);
                sendData1.putExtra("dataBookingID", list.getBookingID());
                context.startActivity(sendData1);

            }
        });
    }

    @Override
    public int getItemCount() {
        return BookingLists.size();
    }

}
