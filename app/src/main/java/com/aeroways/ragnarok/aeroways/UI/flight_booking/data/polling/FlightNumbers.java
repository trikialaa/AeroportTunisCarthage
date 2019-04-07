package com.aeroways.ragnarok.aeroways.UI.flight_booking.data.polling;

import com.google.gson.annotations.SerializedName;

public class FlightNumbers {
    @SerializedName("FlightNumber")
    private final String flightNumber;
    @SerializedName("CarrierId")
    private final String carrierId;

    public FlightNumbers(String flightNumber, String carrierId) {
        this.flightNumber = flightNumber;
        this.carrierId = carrierId;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getCarrierId() {
        return carrierId;
    }
}
