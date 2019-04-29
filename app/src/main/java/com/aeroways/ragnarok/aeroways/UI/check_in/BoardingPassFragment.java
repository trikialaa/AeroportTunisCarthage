package com.aeroways.ragnarok.aeroways.UI.check_in;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeroways.ragnarok.aeroways.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardingPassFragment extends Fragment {


    public BoardingPassFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_boarding_pass, container, false);
    }

}
