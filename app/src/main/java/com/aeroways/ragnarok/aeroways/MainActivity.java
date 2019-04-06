package com.aeroways.ragnarok.aeroways;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.aeroways.ragnarok.aeroways.UI.login.LoginFragment;
import com.aeroways.ragnarok.aeroways.UI.login.SignOnFragment;

public class MainActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Fragment Test

        fragmentManager = getSupportFragmentManager();

        if (findViewById(R.id.fragment)!=null){
            if (savedInstanceState!=null){
                return;
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.fragment, new LoginFragment(),null);
            fragmentTransaction.commit();
        }

        /*
        Uri gmmIntentUri = Uri.parse("google.navigation:q=Aeroport-Tunis-Carthage");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        startActivity(mapIntent);
        */

    }

}
