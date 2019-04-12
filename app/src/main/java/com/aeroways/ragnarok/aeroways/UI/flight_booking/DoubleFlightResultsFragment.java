package com.aeroways.ragnarok.aeroways.UI.flight_booking;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeroways.ragnarok.aeroways.R;

public class DoubleFlightResultsFragment extends Fragment {

    String originPlace, destinationPlace,outboundDate,inboundDate,s_adults,s_children, s_infants, cabinClass;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle b = getArguments();
        originPlace = b.getString("originPlace");
        destinationPlace = b.getString("destinationPlace");
        outboundDate = b.getString("outboundDate");
        inboundDate = b.getString("inboundDate");
        s_adults = b.getString("s_adults");
        s_children = b.getString("s_children");
        s_infants = b.getString("s_infants");
        cabinClass = b.getString("cabinClass");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_double_flight_results, container, false);
    }
}
