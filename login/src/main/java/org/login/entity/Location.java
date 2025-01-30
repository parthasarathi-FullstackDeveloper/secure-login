package org.login.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "location")
public class Location {
    @Id
    private String id;
    private double Latitude;
    private double Longitude;

    public Location(String id, double latitude, double longitude) {
        this.id = id;
        Latitude = latitude;
        Longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public Location() {
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return Latitude;
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }
}
