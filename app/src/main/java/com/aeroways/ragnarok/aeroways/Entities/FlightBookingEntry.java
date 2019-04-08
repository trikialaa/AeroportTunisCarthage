package com.aeroways.ragnarok.aeroways.Entities;

import android.support.annotation.NonNull;

public class FlightBookingEntry {

    private int id;

    @NonNull
    private SimpleFlightBookingEntry outboundFlight;

    private SimpleFlightBookingEntry intboundFlight;

    @NonNull
    private String price;

    @NonNull
    private String bookingUrl;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public SimpleFlightBookingEntry getOutboundFlight() {
        return outboundFlight;
    }

    public void setOutboundFlight(@NonNull SimpleFlightBookingEntry outboundFlight) {
        this.outboundFlight = outboundFlight;
    }

    public SimpleFlightBookingEntry getIntboundFlight() {
        return intboundFlight;
    }

    public void setIntboundFlight(SimpleFlightBookingEntry intboundFlight) {
        this.intboundFlight = intboundFlight;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @NonNull
    public String getBookingUrl() {
        return bookingUrl;
    }

    public void setBookingUrl(@NonNull String bookingUrl) {
        this.bookingUrl = bookingUrl;
    }
}
