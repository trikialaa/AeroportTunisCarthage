package com.aeroways.ragnarok.aeroways.utils;

import com.aeroways.ragnarok.aeroways.Entities.SimpleFlightBookingEntry;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling.Carriers;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling.Itineraries;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling.Places;

import java.util.List;

public class SkyscannerUtils {

    public static String getPlaceCodeById(List<Places> places, String id){
        for(Places p : places){
            if (p.getId().equals(id)) return p.getCode();
        }
        return "Not Found";
    }


    public static String durationToString(String duration) throws Exception{
        long d = Integer.parseInt(duration);
        if (d<60) return (d+"m");
        else return (((long)d)/60)+"h"+(d%60)+"m";
    }

    public static String stopNumbersToString (List<String> stops){
        if(stops.size()==0) return "Direct";
        else if (stops.size()==1) return "1 Stop";
        else return stops.size()+" Stops";
    }

    public static String getCarrierNameById(List<Carriers> carriers, String id){
        for(Carriers c : carriers){
            if (c.getId().equals(id)) return c.getName();
        }
        return "Not Found";
    }

    public static String getCarrierPicById(List<Carriers> carriers, String id){
        for(Carriers c : carriers){
            if (c.getId().equals(id)) return c.getImageUrl();
        }
        return "Not Found";
    }

    public static String getPriceBySimpleLegId(List<Itineraries> itineraries,String id){
        for (Itineraries i : itineraries){
            if (i.getOutboundLegId().equals(id)) return i.getPricingOptions().get(0).getPrice()+" TND";
        }
        return "Not Found";
    }

    public static String getBookingUrlBySimpleLegId(List<Itineraries> itineraries,String id){
        for (Itineraries i : itineraries){
            if (i.getOutboundLegId().equals(id)) return i.getPricingOptions().get(0).getDeeplinkUrl();
        }
        return "Not Found";
    }

    public static SimpleFlightBookingEntry getSimpleFlightBookingEntryById(List<SimpleFlightBookingEntry> list,String id){
        for (SimpleFlightBookingEntry s : list){
            if (s.getId().equals(id)) return s;
        }
        return new SimpleFlightBookingEntry();
    }

}
