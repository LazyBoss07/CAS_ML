package com.example.api2;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity // This tells Hibernate to make a table out of this class
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;

    private String locationLat;
    private String locationLon;

    private String cardNumber;


    public Integer getId() {
        return id;
    }
    private int staticCvv;



    private int  sessionNumber;
    public void setId(Integer id) {
        this.id = id;
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