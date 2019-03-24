package com.aeroways.ragnarok.aeroways.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.aeroways.ragnarok.aeroways.Entities.User;
import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesUtils {

    private static final String SHARED_PREFERENCES_FILE_USER_INFO = "SHARED_PREFERENCES_FILE_USER_INFO" ;
    private static final String SHARED_PREFERENCES_KEY_USER_INFO = "SHARED_PREFERENCES_KEY_USER_INFO" ;

    public static void saveUser(Activity activity, User user){

        // Create Gson object.
        Gson gson = new Gson();

        // Get java object list json format string.
        String userJsonString = gson.toJson(user);

        // Create SharedPreferences object.
        Context ctx = activity.getApplicationContext();
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO, MODE_PRIVATE);

        // Put the json format string to SharedPreferences object.
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(SHARED_PREFERENCES_KEY_USER_INFO, userJsonString);
        editor.commit();
    }

    public static User loadUser(Activity activity) {
        // Create SharedPreferences object.
        Context ctx = activity.getApplicationContext();
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO, MODE_PRIVATE);

        // Get saved string data in it.
        String userJsonString = sharedPreferences.getString(SHARED_PREFERENCES_KEY_USER_INFO, "");

        // Create Gson object and translate the json string to related java object array.
        Gson gson = new Gson();
        User user = gson.fromJson(userJsonString, User.class);
        return user;
    }
}
