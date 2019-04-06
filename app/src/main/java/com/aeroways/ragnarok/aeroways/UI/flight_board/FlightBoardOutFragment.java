package com.aeroways.ragnarok.aeroways.UI.flight_board;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.TextView;

import com.aeroways.ragnarok.aeroways.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FlightBoardOutFragment extends Fragment {

    private List<FlightBoardEntry> fbe;
    private TextView test;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        new FlightBoardUpdater().execute();

        return inflater.inflate(R.layout.fragment_flight_board_out, container, false);
    }

    public class FlightBoardUpdater extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... params) {
            Log.e("MyApp","StartBackground");
            try {
                fbe = FlightBoardBuilder.buildFlightBoard("http://www.aeroport-de-tunis-carthage.com/tunisie-aeroport-de-tunis-carthage-vols-depart.php");
            } catch (Exception e) {
                e.printStackTrace();

            }
            Log.e("MyApp","Finished Background");
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            Log.e("MyApp","StartPostExecute");

            RecyclerView recyclerView = getActivity().findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(new FlightBoardRecyclerViewAdapter(getActivity(),fbe));

        }
    }

}
