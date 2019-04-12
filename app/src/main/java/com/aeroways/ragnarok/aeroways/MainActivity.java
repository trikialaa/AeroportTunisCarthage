package com.aeroways.ragnarok.aeroways;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.aeroways.ragnarok.aeroways.Entities.User;
import com.aeroways.ragnarok.aeroways.UI.login.LoginFragment;
import com.aeroways.ragnarok.aeroways.UI.login.SignOnFragment;
import com.aeroways.ragnarok.aeroways.utils.FragmentUtils;
import com.aeroways.ragnarok.aeroways.utils.SharedPreferencesUtils;

public class MainActivity extends AppCompatActivity {

    private static FragmentManager fragmentManager;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Change Status Bar Color

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        // Import user profile

        user = SharedPreferencesUtils.loadUser(this);

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

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Fragment f = getSupportFragmentManager().findFragmentById(R.id.fragment);
        if(f instanceof SignOnFragment) FragmentUtils.loadMain(this,new LoginFragment());
    }

    public void gotoSignOn(View v){
        FragmentUtils.loadMain(this,new SignOnFragment());
    }

}
