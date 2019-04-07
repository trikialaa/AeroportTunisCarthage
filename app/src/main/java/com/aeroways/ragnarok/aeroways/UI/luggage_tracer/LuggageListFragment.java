package com.aeroways.ragnarok.aeroways.UI.luggage_tracer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.aeroways.ragnarok.aeroways.Entities.Luggage;
import com.aeroways.ragnarok.aeroways.R;

import java.util.ArrayList;
import java.util.List;


public class LuggageListFragment extends Fragment {

    Button addLuggageButton ;
    List<Luggage> luggageList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        new LuggageListUpdater().execute();
        return inflater.inflate(R.layout.fragment_luggage_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        addLuggageButton = getActivity().findViewById(R.id.button6);
        addLuggageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), QRCodeScannerActivity.class));
            }
        });

        super.onViewCreated(view, savedInstanceState);
    }

    public class LuggageListUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Luggage l1 = new Luggage();
                l1.setId("12345");
                l1.setType("Sac");
                l1.setColor("Bleu");
                l1.setWeight(15.7);
                l1.setPicUrl("https://i2.cdscdn.com/pdt2/0/8/9/1/300x300/auc2009895486089/rw/sac-a-main-en-cuir-femme-bleu.jpg");
                l1.setStatus("Securité II");
                l1.setLastSeen("06/04/2019 18:23 GMT");
                luggageList = new ArrayList<Luggage>();
                luggageList.add(l1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new LuggageRecyclerViewAdapter(getActivity(),luggageList));
        }
    }

}


