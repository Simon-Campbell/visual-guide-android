package nz.ac.waikato.ssc10.map;

import android.location.Location;

/**
 * A class which represents a location as a latitude/longitude
 * pair
 */
public class LatLng {
    private double lat;
    private double lng;

    /**
     * Converts this LatLng object to a Location object
     * @return A Location object
     */
    public Location toLocation() {
        Location location = new Location("lat-lng");
        location.setLatitude(lat);
        location.setLongitude(lng);
        return location;
    }

    /**
     * Converts a location to a LatLng object
     * @param location The location to convert to a LatLng object
     * @return A LatLng object
     */
    public static LatLng fromLocation(Location location) {
        return new LatLng(location.getLatitude(), location.getLongitude());
    }

    /**
     * Get the longitude
     * @return The longitude as a double
     */
    public double getLongitude() {
        return lng;
    }

    /**
     * Get the latitude
     * @return The latitude as a double
     */
    public double getLatitude() {
        return lat;
    }

    /**
     * Create a simple latitude/longitude object
     * @param lat The latitude of the location
     * @param lng The longitude of the location
     */
    public LatLng(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LatLng latLng = (LatLng) o;

        if (Double.compare(latLng.lat, lat) != 0) return false;
        if (Double.compare(latLng.lng, lng) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(lat);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(lng);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
