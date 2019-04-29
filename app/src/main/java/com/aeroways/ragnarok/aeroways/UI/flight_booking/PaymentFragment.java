package com.aeroways.ragnarok.aeroways.UI.flight_booking;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aeroways.ragnarok.aeroways.Entities.FlightBookingEntry;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.utils.FragmentUtils;
import com.aeroways.ragnarok.aeroways.utils.SharedPreferencesUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class PaymentFragment extends Fragment {


    TextView textView30,textView38;
    Button button8;
    int points;

    public PaymentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_payment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView30 = getActivity().findViewById(R.id.textView30);
        textView38 = getActivity().findViewById(R.id.textView38);
        button8 = getActivity().findViewById(R.id.button8);

        Bundle bundle = this.getArguments();

        final FlightBookingEntry flightBookingEntry = new Gson().fromJson(bundle.getString("flightBookingEntry"),FlightBookingEntry.class);

        textView30.setText(bundle.getInt("nombreTickets")+"x Tickets Aller Simple\n\n"+flightBookingEntry.getOutboundFlight().getOriginStationCode()+" - "+flightBookingEntry.getOutboundFlight().getDestinationStationCode()+" ["+flightBookingEntry.getOutboundFlight().getDeparture()+"]");
        textView38.setText("Total: "+flightBookingEntry.getPrice()+" TTC");
        button8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("users/"+ SharedPreferencesUtils.loadUser(getActivity()).getId()+"/reservations/"+flightBookingEntry.getOutboundFlight().getId());
                ref.child("status").setValue(0);
                ref.child("flight").setValue(flightBookingEntry);

                final DatabaseReference ref2 = database.getReference("users/"+SharedPreferencesUtils.loadUser(getActivity()).getId());
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        points = dataSnapshot.child("rewardPoints").getValue(Integer.class);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                };
                ref2.addValueEventListener(valueEventListener);
                ref2.removeEventListener(valueEventListener);

                ref2.child("rewardPoints").setValue(points+500);

                Toast.makeText(getActivity(), "Paiement effectué avec succès !",
                        Toast.LENGTH_SHORT).show();

                FragmentUtils.loadApp(getActivity(),new FlightSearchFragment());
            }
        });

    }


}
