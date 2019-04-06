package com.aeroways.ragnarok.aeroways.Entities;

public class FlightBoardEntry {

    public String time;
    public String place;
    public String company;
    public String number;
    public String status;

    public FlightBoardEntry() {
        this.time = "";
        this.place = "";
        this.company = "";
        this.number = "";
        this.status = "";
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


    @Override
    public String toString() {
        return time + " | " + place + " | " + company + " | " + number + " | " + status;
    }
}
