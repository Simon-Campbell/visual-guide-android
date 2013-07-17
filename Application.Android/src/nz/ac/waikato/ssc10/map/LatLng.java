package nz.ac.waikato.ssc10.map;

public class LatLng {
    private double lat;
    private double lng;

    public double getLongitude() {
        return lng;
    }

    public double getLatitude() {
        return lat;
    }

    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }
}
