package org.login.dto;

import lombok.Data;

@Data
public class LocationDTO {

    private double Latitude;
    private double Longitude;

    public LocationDTO() {
    }

    public LocationDTO(double latitude, double longitude) {
        Latitude = latitude;
        Longitude = longitude;
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
