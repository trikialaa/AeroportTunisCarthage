package com.aeroways.ragnarok.aeroways.Entities;


import android.support.annotation.NonNull;

public class User {

    private String id;

    @NonNull
    private String  firstName;

    @NonNull
    private String lastName;

    @NonNull
    private boolean gender;

    @NonNull
    private String dateOfBirth;

    @NonNull
    private String  email;

    @NonNull
    private String  mobile;

    @NonNull
    private String  country;

    private String address;

    @NonNull
    private String login;

    @NonNull
    private String  password;

    private String  profilePicLink;

    private long flightKm = 0;

    private int  rewardPoints = 0;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NonNull String firstName) {
        this.firstName = firstName;
    }

    @NonNull
    public String getLastName() {
        return lastName;
    }

    public void setLastName(@NonNull String lastName) {
        this.lastName = lastName;
    }

    @NonNull
    public boolean isGender() {
        return gender;
    }

    public void setGender(@NonNull boolean gender) {
        this.gender = gender;
    }

    @NonNull
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(@NonNull String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @NonNull
    public String getEmail() {
        return email;
    }

    public void setEmail(@NonNull String email) {
        this.email = email;
    }

    @NonNull
    public String getMobile() {
        return mobile;
    }

    public void setMobile(@NonNull String mobile) {
        this.mobile = mobile;
    }

    @NonNull
    public String getCountry() { return country; }

    public void setCountry(@NonNull String country) { this.country = country; }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @NonNull
    public String getLogin() {
        return login;
    }

    public void setLogin(@NonNull String login) {
        this.login = login;
    }

    @NonNull
    public String getPassword() {
        return password;
    }

    public void setPassword(@NonNull String password) {
        this.password = password;
    }

    public String getProfilePicLink() {
        return profilePicLink;
    }

    public void setProfilePicLink(String profilePicLink) {
        this.profilePicLink = profilePicLink;
    }

    public long getFlightKm() {
        return flightKm;
    }

    public void setFlightKm(long flightKm) {
        this.flightKm = flightKm;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", email='" + email + '\'' +
                ", mobile='" + mobile + '\'' +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", profilePicLink='" + profilePicLink + '\'' +
                ", flightKm=" + flightKm +
                ", rewardPoints=" + rewardPoints +
                '}';
    }
}
