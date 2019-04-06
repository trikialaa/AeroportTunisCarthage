package com.aeroways.ragnarok.aeroways.UI.flight_board;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aeroways.ragnarok.aeroways.Entities.FlightBoardEntry;
import com.aeroways.ragnarok.aeroways.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlightBoardInFragment extends Fragment {


    private List<FlightBoardEntry> fbe;
    private TextView test;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // table scraping test
        test = getView().findViewById(R.id.textView11);
        new FlightBoardInFragment.FlightBoardUpdater().execute();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight_board_in, container, false);
    }

    public class FlightBoardUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            try {
                fbe = FlightBoardBuilder.buildFlightBoard("http://www.aeroport-de-tunis-carthage.com/tunisie-aeroport-de-tunis-carthage-vols-arrivee.php");
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
            recyclerView.setAdapter(new FlightBoardRecyclerViewAdapter(getActivity(),fbe));
        }
    }

}
