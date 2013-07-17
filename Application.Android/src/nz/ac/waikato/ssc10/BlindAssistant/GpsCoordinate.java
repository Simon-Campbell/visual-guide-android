package nz.ac.waikato.ssc10.BlindAssistant;

/**
 * A class describing a GPS coordinate in terms of latitude,
 * longitude and altitude
 */
public class GpsCoordinate {
    double mLatitude;
    double mLongitude;
    double mAltitude;

    public GpsCoordinate(double latitude, double longitude, double altitude) {
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mAltitude = altitude;
    }

    public double getAltitude() {
        return mAltitude;
    }

    public void setAltitude(double altitude) {
        this.mAltitude = altitude;
    }

    public double getLatitude() {
        return mLatitude;
    }

    public void setLatitude(double latitude) {
        this.mLatitude = latitude;
    }

    public double getLongitude() {
        return mLongitude;
    }

    public void setLongitude(double longitude) {
        this.mLongitude = longitude;
    }
}
