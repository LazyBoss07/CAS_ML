package com.codebeginner.sovary.fingerprinttest.reotrfit;

import androidx.annotation.NonNull;

public class User {


    private String locationLat;
    private String locationLon;

    private String cardNumber;



    private int staticCvv;



    private int  sessionNumber;

    @NonNull
    @Override
    public String toString() {
        return "User{" +
                "locationLat='" + locationLat + '\'' +
                ", locationLon='" + locationLon + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", staticCvv=" + staticCvv +
                ", sessionNumber=" + sessionNumber +
                '}';
    }

    public String getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(String lat) {
        this.locationLat = lat;
    }
    public String getLocationLon() {
        return locationLon;
    }

    public void setLocationLon(String lon) {
        this.locationLon = lon;
    }
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String email) {
        this.cardNumber = email;
    }
    public int getStaticCvv() {
        return staticCvv;
    }

    public void setStaticCvv(int staticCvv) {
        this.staticCvv = staticCvv;
    }

    public int getSessionNumber() {
        return sessionNumber;
    }

    public void setSessionNumber(int sessionNumber) {
        this.sessionNumber = sessionNumber;
    }
}