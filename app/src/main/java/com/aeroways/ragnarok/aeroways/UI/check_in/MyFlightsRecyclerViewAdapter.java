package com.aeroways.ragnarok.aeroways.UI.check_in;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Space;
import android.widget.TextView;

import com.aeroways.ragnarok.aeroways.Entities.FlightBookingEntry;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.SimpleFlightRecyclerViewAdapter;
import com.aeroways.ragnarok.aeroways.UI.luggage_tracer.LuggageListFragment;
import com.aeroways.ragnarok.aeroways.utils.BitmapUtils;
import com.aeroways.ragnarok.aeroways.utils.FragmentUtils;
import com.aeroways.ragnarok.aeroways.utils.SharedPreferencesUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.List;

public class MyFlightsRecyclerViewAdapter extends RecyclerView.Adapter<MyFlightsRecyclerViewAdapter.ViewHolder> {


    private Context context;
    private List<MyFlightsFB> myFlightsFBS;

    public MyFlightsRecyclerViewAdapter(Context context, List<MyFlightsFB> myFlightsFBS) {
        this.context = context;
        this.myFlightsFBS = myFlightsFBS;
    }

    @NonNull
    @Override
    public MyFlightsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_flights,parent,false);
        MyFlightsRecyclerViewAdapter.ViewHolder viewHolder = new MyFlightsRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyFlightsRecyclerViewAdapter.ViewHolder holder, int position) {
        final MyFlightsFB myFlightFB = myFlightsFBS.get(position);
        holder.carrier.setText(myFlightFB.getFlight().getOutboundFlight().getCarrier());
        holder.originPlace.setText(myFlightFB.getFlight().getOutboundFlight().getOriginStationCode());
        holder.destinationPlace.setText(myFlightFB.getFlight().getOutboundFlight().getDestinationStationCode());
        holder.departureTime.setText(myFlightFB.getFlight().getOutboundFlight().getDeparture().substring(11,16)+" GMT");
        holder.arrivalTime.setText(myFlightFB.getFlight().getOutboundFlight().getArrival().substring(11,16)+" GMT");
        BitmapUtils.loadImageFromURL(myFlightFB.getFlight().getOutboundFlight().getCarrierPictureLink(),holder.carrierPic);

        int status = myFlightFB.getStatus();
        if (status==0){
            holder.mainButton.setText("Check-in");
            holder.mainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference ref = database.getReference("users/"+ SharedPreferencesUtils.loadUser((FragmentActivity)context).getId()+"/reservations/"+myFlightFB.getFlight().getOutboundFlight().getId());
                    ref.child("status").setValue(1);

                }
            });
            holder.space.setVisibility(View.GONE);
            holder.boardingPassButton.setVisibility(View.GONE);
        } else if (status==1){
            holder.mainButton.setText("Bagages");
            holder.mainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Bundle bundle = new Bundle();
                    bundle.putString("flight",new Gson().toJson(myFlightFB.getFlight()));
                    Fragment luggageListFragment = new LuggageListFragment();
                    luggageListFragment.setArguments(bundle);
                    FragmentUtils.loadApp((FragmentActivity)context,luggageListFragment);
                }
            });
            holder.boardingPassButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentUtils.loadApp((FragmentActivity)context,new BoardingPassFragment());
                    Bundle bundle = new Bundle();
                    bundle.putString("flight",new Gson().toJson(myFlightFB.getFlight()));
                    Fragment boardingPassFragment = new BoardingPassFragment();
                    boardingPassFragment.setArguments(bundle);
                    FragmentUtils.loadApp((FragmentActivity)context,boardingPassFragment);
                }
            });
            holder.space.setVisibility(View.VISIBLE);
            holder.boardingPassButton.setVisibility(View.VISIBLE);
        } else if (status==2){
            holder.mainButton.setText("Bagages");
            holder.space.setVisibility(View.GONE);
            holder.boardingPassButton.setVisibility(View.GONE);
            holder.mainButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentUtils.loadApp((FragmentActivity)context,new LuggageListFragment());
                    Bundle bundle = new Bundle();
                    bundle.putString("flight",new Gson().toJson(myFlightFB.getFlight()));
                    Fragment luggageListFragment = new LuggageListFragment();
                    luggageListFragment.setArguments(bundle);
                    FragmentUtils.loadApp((FragmentActivity)context,luggageListFragment);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return myFlightsFBS.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView carrier, originPlace, destinationPlace, departureTime, arrivalTime;
        Button mainButton, boardingPassButton;
        Space space;
        ImageView carrierPic;


        public ViewHolder(View itemView) {
            super(itemView);
            carrier = itemView.findViewById(R.id.textView20);
            originPlace = itemView.findViewById(R.id.textView21);
            destinationPlace = itemView.findViewById(R.id.textView22);
            departureTime = itemView.findViewById(R.id.textView23);
            arrivalTime = itemView.findViewById(R.id.textView25);
            mainButton = itemView.findViewById(R.id.button4);
            boardingPassButton = itemView.findViewById(R.id.button10);
            space = itemView.findViewById(R.id.space);
            carrierPic = itemView.findViewById(R.id.imageView5);

        }

    }

}
