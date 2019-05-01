package com.aeroways.ragnarok.aeroways.UI.parking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aeroways.ragnarok.aeroways.Entities.ParkingReservationEntry;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.UI.luggage_tracer.LuggageRecyclerViewAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyParkingReservationsRecyclerViewAdapter extends RecyclerView.Adapter<MyParkingReservationsRecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<ParkingReservationEntry> parkingReservationEntries;

    public MyParkingReservationsRecyclerViewAdapter(Context context, ArrayList<ParkingReservationEntry> parkingReservationEntries) {
        this.context = context;
        this.parkingReservationEntries = parkingReservationEntries;
    }

    @NonNull
    @Override
    public MyParkingReservationsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_parking_reservation,parent,false);
        MyParkingReservationsRecyclerViewAdapter.ViewHolder viewHolder = new MyParkingReservationsRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyParkingReservationsRecyclerViewAdapter.ViewHolder holder, int position) {
        ParkingReservationEntry parkingReservationEntry = parkingReservationEntries.get(position);
        holder.date.setText("Date: "+parkingReservationEntry.getDate());
        holder.debut.setText(parkingReservationEntry.getStartTime());
        holder.fin.setText(parkingReservationEntry.getEndTime());
        holder.place.setText("N° Place: "+parkingReservationEntry.getPlace());
    }

    @Override
    public int getItemCount() {
        return parkingReservationEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView date,debut,fin,place;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.textView20);
            debut = itemView.findViewById(R.id.textView23);
            fin = itemView.findViewById(R.id.textView25);
            place = itemView.findViewById(R.id.textView29);
        }
    }
}