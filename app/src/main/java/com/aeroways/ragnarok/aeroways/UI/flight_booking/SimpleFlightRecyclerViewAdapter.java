package com.aeroways.ragnarok.aeroways.UI.flight_booking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroways.ragnarok.aeroways.Entities.FlightBookingEntry;
import com.aeroways.ragnarok.aeroways.Entities.Luggage;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.UI.luggage_tracer.LuggageRecyclerViewAdapter;
import com.aeroways.ragnarok.aeroways.utils.BitmapUtils;

import java.util.ArrayList;
import java.util.List;

public class SimpleFlightRecyclerViewAdapter extends RecyclerView.Adapter<SimpleFlightRecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<FlightBookingEntry> flightBookingEntries;


    public SimpleFlightRecyclerViewAdapter(Context context,List<FlightBookingEntry> flightBookingEntries){
        this.context=context;
        this.flightBookingEntries=flightBookingEntries;
    }

    @NonNull
    @Override
    public SimpleFlightRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_simple_flight,parent,false);
        SimpleFlightRecyclerViewAdapter.ViewHolder viewHolder = new SimpleFlightRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SimpleFlightRecyclerViewAdapter.ViewHolder holder, int position) {
        final FlightBookingEntry flightBookingEntry = flightBookingEntries.get(position);
        holder.carrier.setText(flightBookingEntry.getOutboundFlight().getCarrier());
        holder.a_depart.setText(flightBookingEntry.getOutboundFlight().getOriginStationCode());
        holder.a_arrivee.setText(flightBookingEntry.getOutboundFlight().getDestinationStationCode());
        holder.t_depart.setText(flightBookingEntry.getOutboundFlight().getDeparture().substring(11,16));
        holder.t_arrivee.setText(flightBookingEntry.getOutboundFlight().getArrival().substring(11,16));
        holder.prix.setText("Prix : "+flightBookingEntry.getPrice());
        BitmapUtils.loadImageFromURL(flightBookingEntry.getOutboundFlight().getCarrierPictureLink(),holder.pic);
        holder.reserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, flightBookingEntry.getBookingUrl(),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.e("TESTING",String.valueOf(flightBookingEntries.size()));
        return flightBookingEntries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView carrier,a_depart,a_arrivee,t_depart,t_arrivee,prix;
        Button reserver;
        ImageView pic;

        public ViewHolder(View itemView) {
            super(itemView);
            carrier = itemView.findViewById(R.id.textView20);
            a_depart = itemView.findViewById(R.id.textView21);
            a_arrivee = itemView.findViewById(R.id.textView22);
            t_depart = itemView.findViewById(R.id.textView23);
            t_arrivee = itemView.findViewById(R.id.textView25);
            prix = itemView.findViewById(R.id.textView26);
            pic = itemView.findViewById(R.id.imageView5);
            reserver = itemView.findViewById(R.id.button4);


        }

    }
}