package com.aeroways.ragnarok.aeroways.UI.check_in;

import com.aeroways.ragnarok.aeroways.Entities.FlightBookingEntry;

public class MyFlightsFB {

    private FlightBookingEntry flight;
    private int status;

    public MyFlightsFB() {
    }

    public FlightBookingEntry getFlight() {
        return flight;
    }

    public void setFlight(FlightBookingEntry flight) {
        this.flight = flight;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
