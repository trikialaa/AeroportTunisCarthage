package com.aeroways.ragnarok.aeroways;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aeroways.ragnarok.aeroways.UI.bottom_nav.BottomNavFragment;
import com.aeroways.ragnarok.aeroways.UI.flight_board.FlightBoardOutFragment;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.FlightSearchFragment;

public class AppActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);

        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.navFragment)!=null){
            if (savedInstanceState!=null){
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.navFragment, new BottomNavFragment(),null);
            fragmentTransaction.commit();
        }

        if (findViewById(R.id.mainFragment)!=null){
            if (savedInstanceState!=null){
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.mainFragment, new FlightSearchFragment(),null);
            fragmentTransaction.commit();
        }
    }
}
