package com.aeroways.ragnarok.aeroways;

import android.os.Build;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.aeroways.ragnarok.aeroways.UI.check_in.MyFlightsFragment;
import com.aeroways.ragnarok.aeroways.UI.contact.ContactFragment;
import com.aeroways.ragnarok.aeroways.UI.flight_board.FlightBoardOutFragment;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.FlightSearchFragment;
import com.aeroways.ragnarok.aeroways.UI.luggage_tracer.LuggageListFragment;
import com.aeroways.ragnarok.aeroways.UI.parking.MyParkingReservationsFragment;
import com.aeroways.ragnarok.aeroways.UI.stores.StoresFragment;

public class AppActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        // Change Status Bar Color

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.mainFragment)!=null){
            if (savedInstanceState!=null){
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.mainFragment, new StoresFragment(),null);
            fragmentTransaction.commit();
        }
    }
}
