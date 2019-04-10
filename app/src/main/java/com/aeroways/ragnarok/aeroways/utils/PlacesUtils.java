package com.aeroways.ragnarok.aeroways.utils;

import android.content.Context;

import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.JsonPlaces;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PlacesUtils {

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    public static String loadPlacesJson(Context context){
        String json = null;
        try {
            InputStream is = context.getAssets().open("JP.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return json;
    }


    public static JsonPlaces[] getFromJson(Context context){
       JsonPlaces[] jsonPlacesArray = new JsonPlaces[67];

       jsonPlacesArray = gson.fromJson(loadPlacesJson(context), JsonPlaces[].class);

        return jsonPlacesArray;
    }

    public static ArrayList<String> getAirportNames(JsonPlaces[] jsonPlaces){
        ArrayList<String> result = new ArrayList<>();
        for(int i=0;i<jsonPlaces.length;i++){
            result.add(jsonPlaces[i].getAeroport());
        }
        return result;
    }

    public static JsonPlaces getJsonPlaceByAirportName(String airportName,JsonPlaces[] jsonPlaces){
        for(int i=0;i<jsonPlaces.length;i++){
            if(airportName.equals(jsonPlaces[i].getAeroport())) return jsonPlaces[i];
        }
        return null;
    }

}
