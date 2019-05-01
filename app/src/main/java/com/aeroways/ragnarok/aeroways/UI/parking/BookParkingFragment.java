package com.aeroways.ragnarok.aeroways.UI.parking;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.aeroways.ragnarok.aeroways.Entities.ParkingReservationEntry;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.utils.FragmentUtils;
import com.aeroways.ragnarok.aeroways.utils.SharedPreferencesUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookParkingFragment extends Fragment {

    EditText editText1,editText2,editText3;
    Button button5;

    public BookParkingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_parking, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editText1 = getActivity().findViewById(R.id.editText1);
        editText2 = getActivity().findViewById(R.id.editText2);
        editText3 = getActivity().findViewById(R.id.editText3);
        button5 = getActivity().findViewById(R.id.button5);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference ref = database.getReference("users/"+ SharedPreferencesUtils.loadUser(getActivity()).getId()+"/parking");
        final DatabaseReference ref2 = database.getReference("parking");

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((editText1.getText().equals(""))||(editText2.getText().equals(""))||(editText3.getText().equals(""))){
                    Toast.makeText(getActivity(),"Veuillez remplir tous les champs",Toast.LENGTH_SHORT).show();
                } else {
                    ParkingReservationEntry parkingReservationEntry = new ParkingReservationEntry();
                    parkingReservationEntry.setDate(editText1.getText().toString());
                    parkingReservationEntry.setStartTime(editText2.getText().toString());
                    parkingReservationEntry.setEndTime(editText3.getText().toString());
                    parkingReservationEntry.setClient(SharedPreferencesUtils.loadUser(getActivity()).getFirstName()+" "+SharedPreferencesUtils.loadUser(getActivity()).getLastName());

                    Random r = new Random();
                    int i1 = r.nextInt(2500 - 1) + 1;

                    parkingReservationEntry.setPlace(i1);

                    ref.child(parkingReservationEntry.getDate()).setValue(parkingReservationEntry);
                    ref2.child(parkingReservationEntry.getDate()).setValue(parkingReservationEntry);

                    Toast.makeText(getActivity(),"Réservation réussie !",Toast.LENGTH_SHORT).show();

                    FragmentUtils.loadApp(getActivity(),new MyParkingReservationsFragment());
                }
            }
        });

    }
}
