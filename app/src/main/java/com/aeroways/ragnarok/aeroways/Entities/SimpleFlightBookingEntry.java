package com.aeroways.ragnarok.aeroways.Entities;

import android.support.annotation.NonNull;

public class SimpleFlightBookingEntry {

    private String id;

    @NonNull
    private String originStationCode;

    @NonNull
    private String destinationStationCode;

    @NonNull
    private String departure;

    @NonNull
    private String arrival;

    @NonNull
    private String duration;

    @NonNull
    private String stopsNumber;

    @NonNull
    private String carrier;

    @NonNull
    private String carrierPictureLink;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getOriginStationCode() {
        return originStationCode;
    }

    public void setOriginStationCode(@NonNull String originStationCode) {
        this.originStationCode = originStationCode;
    }

    @NonNull
    public String getDestinationStationCode() {
        return destinationStationCode;
    }

    public void setDestinationStationCode(@NonNull String destinationStationCode) {
        this.destinationStationCode = destinationStationCode;
    }

    @NonNull
    public String getDeparture() {
        return departure;
    }

    public void setDeparture(@NonNull String departure) {
        this.departure = departure;
    }

    @NonNull
    public String getArrival() {
        return arrival;
    }

    public void setArrival(@NonNull String arrival) {
        this.arrival = arrival;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStopsNumber() {
        return stopsNumber;
    }

    public void setStopsNumber(String stopsNumber) {
        this.stopsNumber = stopsNumber;
    }

    @NonNull
    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(@NonNull String carrier) {
        this.carrier = carrier;
    }

    @NonNull
    public String getCarrierPictureLink() {
        return carrierPictureLink;
    }

    public void setCarrierPictureLink(@NonNull String carrierPictureLink) {
        this.carrierPictureLink = carrierPictureLink;
    }
}
