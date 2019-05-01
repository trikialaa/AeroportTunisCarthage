package com.aeroways.ragnarok.aeroways.UI.parking;


import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.aeroways.ragnarok.aeroways.Entities.ParkingReservationEntry;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.UI.luggage_tracer.LuggageRecyclerViewAdapter;
import com.aeroways.ragnarok.aeroways.utils.FragmentUtils;
import com.aeroways.ragnarok.aeroways.utils.SharedPreferencesUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyParkingReservationsFragment extends Fragment {

    Activity activity;
    ArrayList<ParkingReservationEntry> parkingReservationEntries = new ArrayList<>();
    RecyclerView recyclerView;
    MyParkingReservationsRecyclerViewAdapter myParkingReservationsRecyclerViewAdapter;


    public MyParkingReservationsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity=getActivity();
        new MyParkingReservationsUpdater().execute();
        return inflater.inflate(R.layout.fragment_my_parking_reservations, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.recycler_view);
        myParkingReservationsRecyclerViewAdapter = new MyParkingReservationsRecyclerViewAdapter(activity,parkingReservationEntries);


        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users/"+ SharedPreferencesUtils.loadUser(getActivity()).getId()+"/parking");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                parkingReservationEntries.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    parkingReservationEntries.add(dsp.getValue(ParkingReservationEntry.class));
                }
                myParkingReservationsRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Button button11 = getActivity().findViewById(R.id.button11);
        button11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentUtils.loadApp((FragmentActivity) activity,new BookParkingFragment());
            }
        });

    }

    public class MyParkingReservationsUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView = getActivity().findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(myParkingReservationsRecyclerViewAdapter);
        }
    }

}
