package com.aeroways.ragnarok.aeroways.UI.luggage_tracer;

import android.content.Intent;
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

import com.aeroways.ragnarok.aeroways.Entities.Luggage;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.utils.SharedPreferencesUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class LuggageListFragment extends Fragment {

    Button addLuggageButton ;
    List<Luggage> luggageList;
    String flightId ="17180-1909200715--32011-0-10413-1909201045";
    LuggageRecyclerViewAdapter luggageRecyclerViewAdapter;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("TEST","Starting updater");
        new LuggageListUpdater().execute();
        return inflater.inflate(R.layout.fragment_luggage_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        addLuggageButton = getActivity().findViewById(R.id.button6);
        addLuggageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), QRCodeScannerActivity.class);
                i.putExtra("flightId",flightId);
                startActivity(i);
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public class LuggageListUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {

                Log.e("TEST","Background Started");
                luggageList = new ArrayList<Luggage>();

                final FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference ref = database.getReference("users/"+ SharedPreferencesUtils.loadUser(getActivity()).getId()+"/reservations/"+flightId+"/bagages");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        luggageList.clear();
                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                            luggageList.add(dsp.getValue(Luggage.class));
                            luggageRecyclerViewAdapter.notifyDataSetChanged();
                            //recyclerView.setAdapter(luggageRecyclerViewAdapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.e("TEST","Background finished");

            recyclerView = getActivity().findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            luggageRecyclerViewAdapter  = new LuggageRecyclerViewAdapter(getActivity(),luggageList);
            recyclerView.setAdapter(luggageRecyclerViewAdapter);
        }
    }

}


