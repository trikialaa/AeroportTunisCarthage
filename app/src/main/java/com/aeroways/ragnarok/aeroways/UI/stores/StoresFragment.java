package com.aeroways.ragnarok.aeroways.UI.stores;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aeroways.ragnarok.aeroways.Entities.Boutique;
import com.aeroways.ragnarok.aeroways.Entities.Luggage;
import com.aeroways.ragnarok.aeroways.R;
import com.aeroways.ragnarok.aeroways.UI.luggage_tracer.LuggageRecyclerViewAdapter;
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
public class StoresFragment extends Fragment {

    ArrayList<Boutique> boutiques;
    RecyclerView recyclerView;

    public StoresFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new StoresUpdater().execute();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stores, container, false);
    }

    public class StoresUpdater extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            boutiques = new ArrayList<>();

            final FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference ref = database.getReference("boutiques");
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    boutiques.clear();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        boutiques.add(dsp.getValue(Boutique.class));
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            recyclerView = getActivity().findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new StoresRecyclerViewAdapter(getActivity(),boutiques));

        }
    }

}
