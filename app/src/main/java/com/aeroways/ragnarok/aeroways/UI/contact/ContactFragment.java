package com.aeroways.ragnarok.aeroways.UI.contact;


import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.utils.KeyboardHider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {

    EditText editText1,editText2,editText3;
    Button button;

    public ContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contact, container, false);



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        editText1 = getActivity().findViewById(R.id.editText1);
        editText2 = getActivity().findViewById(R.id.editText2);
        editText3 = getActivity().findViewById(R.id.editText3);
        button = getActivity().findViewById(R.id.button5);
        LinearLayout l = getActivity().findViewById(R.id.l);

        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                KeyboardHider.closeKeyboard(getActivity());
            }
        });


        final FirebaseDatabase database = FirebaseDatabase.getInstance();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref = database.getReference("/contact");
                String key = ref.push().getKey();

                HashMap<String,String> mail = new HashMap<>();
                mail.put("nom",editText1.getText().toString());
                mail.put("adresse",editText2.getText().toString());
                mail.put("message",editText3.getText().toString());
                ref.child(key).setValue(mail);

                Toast.makeText(getActivity(),"Message envoyé avec succès !",Toast.LENGTH_SHORT).show();
            }
        });



    }
}
