package com.aeroways.ragnarok.aeroways.UI.flight_booking;

import android.util.Log;

import com.aeroways.ragnarok.aeroways.Entities.FlightBookingEntry;
import com.aeroways.ragnarok.aeroways.Entities.SimpleFlightBookingEntry;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling.Carriers;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling.Itineraries;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling.Legs;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling.Places;
import com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling.PollingResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.aeroways.ragnarok.aeroways.utils.SkyscannerUtils.durationToString;
import static com.aeroways.ragnarok.aeroways.utils.SkyscannerUtils.getCarrierNameById;
import static com.aeroways.ragnarok.aeroways.utils.SkyscannerUtils.getCarrierPicById;
import static com.aeroways.ragnarok.aeroways.utils.SkyscannerUtils.getPlaceCodeById;
import static com.aeroways.ragnarok.aeroways.utils.SkyscannerUtils.getSimpleFlightBookingEntryById;
import static com.aeroways.ragnarok.aeroways.utils.SkyscannerUtils.stopNumbersToString;

public class DoubleFlightBookingService {

    private static String sessionKey = "";

    private static HttpParams httpParams = new BasicHttpParams();

    private static HttpClient client;

    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create();

    public static List<FlightBookingEntry> getFlightBookingEntries(String originPlace, String destinationPlace, String outboundDate, String inboundDate, String s_adults, String s_children, String s_infants, String cabinClass) {

        HttpConnectionParams.setConnectionTimeout(httpParams, 20000);
        HttpConnectionParams.setSoTimeout(httpParams, 20000);

        client = new DefaultHttpClient(httpParams);

        List<FlightBookingEntry> result = new ArrayList<>();
        Log.e("MyApp", "Starting the background thing");

        try {

            HttpPost rep = new HttpPost("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/v1.0");
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("cabinClass", cabinClass));
            nameValuePairs.add(new BasicNameValuePair("children", s_children));
            nameValuePairs.add(new BasicNameValuePair("infants", s_infants));
            nameValuePairs.add(new BasicNameValuePair("groupPricing", "false"));
            nameValuePairs.add(new BasicNameValuePair("country", "TN"));
            nameValuePairs.add(new BasicNameValuePair("currency", "TND"));
            nameValuePairs.add(new BasicNameValuePair("locale", "fr-FR"));
            nameValuePairs.add(new BasicNameValuePair("originPlace", originPlace));
            nameValuePairs.add(new BasicNameValuePair("destinationPlace", destinationPlace));
            nameValuePairs.add(new BasicNameValuePair("outboundDate", outboundDate));
            nameValuePairs.add(new BasicNameValuePair("inboundDate", inboundDate));
            nameValuePairs.add(new BasicNameValuePair("adults", s_adults));
            rep.setHeader("X-RapidAPI-Key", "62f0838904mshc39538c32d98ec0p142683jsn8bac895a9bf8");
            rep.setHeader("Content-Type", "application/x-www-form-urlencoded");
            rep.setEntity(new UrlEncodedFormEntity(nameValuePairs, HTTP.UTF_8));

            org.apache.http.HttpResponse r = client.execute(rep);

            int status = r.getStatusLine().getStatusCode();

            if (status == 201) {
                Log.e("MyApp", "Success");
            } else {
                Log.e("MyApp", String.valueOf(status));
            }
            HttpEntity entity = r.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Log.e("MyApp", responseString);
            for (Header header : r.getAllHeaders()) {
                if (header.getName().equalsIgnoreCase("location")) {
                    sessionKey = header.getValue().replace("http://partners.api.skyscanner.net/apiservices/pricing/uk1/v1.0/", "");
                    Log.e("MyApp", sessionKey);
                }
            }


        } catch (Exception e) {
            Log.e("MyApp", "Background failed");
            Log.e("MyApp", e.toString());
        }


        String repStringJson = "";

        try {
            HttpGet rep2 = new HttpGet("https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/pricing/uk2/v1.0/" + sessionKey + "?sortType=price&sortOrder=asc&pageIndex=0&pageSize=20");
            rep2.setHeader("X-RapidAPI-Key", "62f0838904mshc39538c32d98ec0p142683jsn8bac895a9bf8");
            org.apache.http.HttpResponse r2 = client.execute(rep2);
            repStringJson = r2.getEntity().toString();
            int status = r2.getStatusLine().getStatusCode();
            Log.e("MyApp", String.valueOf(status));
            HttpEntity entity = r2.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            Log.e("MyApp", String.valueOf(responseString));
            PollingResponse pollingResponse = gson.fromJson(String.valueOf(responseString), PollingResponse.class);
            List<SimpleFlightBookingEntry> legsAfterProcessing = new ArrayList<>();

            List<Carriers> carriers = pollingResponse.getCarriers();
            List<Legs> legs = pollingResponse.getLegs();
            List<Itineraries> itineraries = pollingResponse.getItineraries();
            List<Places> places = pollingResponse.getPlaces();

            for (int i = 0; i < pollingResponse.getLegs().size(); i++) {
                SimpleFlightBookingEntry simpleFlightBookingEntry = new SimpleFlightBookingEntry();
                simpleFlightBookingEntry.setId(legs.get(i).getId());
                simpleFlightBookingEntry.setOriginStationCode(getPlaceCodeById(places, legs.get(i).getOriginStation()));
                simpleFlightBookingEntry.setDestinationStationCode(getPlaceCodeById(places, legs.get(i).getDestinationStation()));
                simpleFlightBookingEntry.setDeparture(legs.get(i).getDeparture());
                simpleFlightBookingEntry.setArrival(legs.get(i).getArrival());
                simpleFlightBookingEntry.setDuration(durationToString(legs.get(i).getDuration()));
                simpleFlightBookingEntry.setStopsNumber(stopNumbersToString(legs.get(i).getStops()));
                simpleFlightBookingEntry.setCarrier(getCarrierNameById(carriers, legs.get(i).getCarriers().get(0)));
                simpleFlightBookingEntry.setCarrierPictureLink(getCarrierPicById(carriers, legs.get(i).getCarriers().get(0)));
                legsAfterProcessing.add(simpleFlightBookingEntry);
            }

            for (int i = 0; i < pollingResponse.getItineraries().size(); i++) {

                FlightBookingEntry flightBookingEntry = new FlightBookingEntry();
                flightBookingEntry.setOutboundFlight(getSimpleFlightBookingEntryById(legsAfterProcessing, itineraries.get(i).getOutboundLegId()));
                flightBookingEntry.setIntboundFlight(getSimpleFlightBookingEntryById(legsAfterProcessing, itineraries.get(i).getInboundLegId()));
                flightBookingEntry.setPrice(itineraries.get(i).getPricingOptions().get(0).getPrice() + " TND");
                flightBookingEntry.setBookingUrl(itineraries.get(i).getPricingOptions().get(0).getDeeplinkUrl());

                result.add(flightBookingEntry);
            }
            Collections.sort(result);

        } catch (Exception ex) {
        }
        return result;
    }
}