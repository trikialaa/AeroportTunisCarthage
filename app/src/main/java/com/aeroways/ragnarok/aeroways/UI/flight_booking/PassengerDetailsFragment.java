package com.aeroways.ragnarok.aeroways.UI.flight_booking;


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
import android.widget.Button;

import com.aeroways.ragnarok.aeroways.Entities.FlightBookingEntry;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.utils.FragmentUtils;
import com.google.gson.Gson;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassengerDetailsFragment extends Fragment {

    RecyclerView recyclerView;
    private FlightBookingEntry flightBookingEntry;
    private int adults;
    private int children;
    private int infants;
    Button button;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        this.flightBookingEntry= new Gson().fromJson(bundle.getString("flightBookingEntry"), FlightBookingEntry.class);
        this.adults=bundle.getInt("adults");
        this.children=bundle.getInt("children");
        this.infants=bundle.getInt("infants");
        return inflater.inflate(R.layout.fragment_passenger_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = getActivity().findViewById(R.id.recycler_view);
        new PassengerDetailsUpdater().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        button = getActivity().findViewById(R.id.button9);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b2 = new Bundle();
                b2.putInt("nombreTickets",adults+children+infants);
                b2.putString("flightBookingEntry",new Gson().toJson(flightBookingEntry));
                PaymentFragment paymentFragment = new PaymentFragment();
                paymentFragment.setArguments(b2);
                FragmentUtils.loadApp(getActivity(),paymentFragment);
            }
        });

    }

    public class PassengerDetailsUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
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
            recyclerView.setAdapter(new PassengerDetailsRecyclerViewAdapter(getActivity(),adults,children,infants));
        }
    }
}
