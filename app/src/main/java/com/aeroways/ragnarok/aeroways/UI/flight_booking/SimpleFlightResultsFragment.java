package com.aeroways.ragnarok.aeroways.UI.flight_booking;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeroways.ragnarok.aeroways.Entities.FlightBookingEntry;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.UI.luggage_tracer.LuggageRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

public class SimpleFlightResultsFragment extends Fragment {

    String originPlace, destinationPlace,outboundDate,s_adults,s_children, s_infants, cabinClass;
    private List<FlightBookingEntry> flightBookingEntries = new ArrayList<FlightBookingEntry>();
    private RecyclerView recyclerView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        originPlace = b.getString("originPlace");
        destinationPlace = b.getString("destinationPlace");
        outboundDate = b.getString("outboundDate");
        s_adults = b.getString("s_adults");
        s_children = b.getString("s_children");
        s_infants = b.getString("s_infants");
        cabinClass = b.getString("cabinClass");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recyclerView = getActivity().findViewById(R.id.recycler_view);
        new FlightSearchUpdater().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        return inflater.inflate(R.layout.fragment_simple_flight_results, container, false);
    }

    public class FlightSearchUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                flightBookingEntries = SimpleFlightBookingService.getFlightBookingEntries(originPlace, destinationPlace, outboundDate, s_adults, s_children, s_infants, cabinClass);
            } catch (Exception e) {
                Log.e("MyApp","Background failed");
                e.printStackTrace();

            }
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
            recyclerView.setAdapter(new SimpleFlightRecyclerViewAdapter(getActivity(),flightBookingEntries,s_adults,s_children,s_infants,cabinClass));
        }
    }

}

