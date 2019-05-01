package com.aeroways.ragnarok.aeroways.UI.check_in;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.SimpleFlightRecyclerViewAdapter;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.SimpleFlightResultsFragment;
import com.aeroways.ragnarok.aeroways.utils.SharedPreferencesUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class MyFlightsFragment extends Fragment {

    List<MyFlightsFB> myFlightsFBList = new ArrayList<>();
    private RecyclerView recyclerView;
    MyFlightsRecyclerViewAdapter myFlightsRecyclerViewAdapter;

    public MyFlightsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recyclerView = getActivity().findViewById(R.id.recycler_view);

        myFlightsRecyclerViewAdapter = new MyFlightsRecyclerViewAdapter(getActivity(),myFlightsFBList);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("users/"+ SharedPreferencesUtils.loadUser(getActivity()).getId()+"/reservations");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                myFlightsFBList.clear();
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    myFlightsFBList.add(dsp.getValue(MyFlightsFB.class));
                }
                myFlightsRecyclerViewAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        new MyFlightsUpdater().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_flights, container, false);
    }

    public class MyFlightsUpdater extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {


            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Log.e("MyApp","Starting post execute");
            super.onPostExecute(aVoid);

            RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setNestedScrollingEnabled(false);
            recyclerView.setAdapter(myFlightsRecyclerViewAdapter);
        }
    }

}
