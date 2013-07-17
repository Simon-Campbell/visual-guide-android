package nz.ac.waikato.ssc10.BlindAssistant;

import nz.ac.waikato.ssc10.map.LatLng;

/**
 * A class describing a GPS coordinate in terms of latitude,
 * longitude and altitude
 */
public class GpsCoordinate {
    private LatLng location;
    private double altitude;

    public GpsCoordinate(double latitude, double longitude, double altitude) {
        this.location = new LatLng(latitude, longitude);
        this.altitude = altitude;
    }

    public double getAltitude() {
        return this.altitude;
    }

    public double getLatitude() {
        return this.location.getLatitude();
    }

    public double getLongitude() {
        return this.location.getLongitude();
    }
}
