package com.aeroways.ragnarok.aeroways.UI.flight_booking;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeroways.ragnarok.aeroways.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class PassengerDetailsFragment extends Fragment {

    private int adults;
    private int children;
    private int infants;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        this.adults=bundle.getInt("adults");
        this.children=bundle.getInt("children");
        this.infants=bundle.getInt("infants");
        return inflater.inflate(R.layout.fragment_passenger_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
