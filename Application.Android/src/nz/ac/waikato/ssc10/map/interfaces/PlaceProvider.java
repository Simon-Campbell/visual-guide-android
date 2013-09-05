package nz.ac.waikato.ssc10.map.interfaces;

import android.location.Address;
import android.location.Location;

import java.util.List;

/**
 * An interface which describes how places are grabbed.
 */
public interface PlaceProvider {
    /**
     * Sets the radius limit for this provider
     *
     * @param radius The radius from a specified location that
     *               a place provider must provide locations.
     */
    void setRadiusLimit(int radius);

    /**
     * Set the maximum number of results that the search
     * provider can return.
     *
     * @param limit The maximum number of results that the provider can
     *              return.
     */
    void setResultsLimit(int limit);

    /**
     * Set the location to search from
     *
     * @param location The origin location to search near
     */
    void setSearchFromLocation(Location location);

    /**
     * Get a list of addresses matching the description
     *
     * @param description The description of the addresses to find
     * @return A list of addresses that match the description
     */
    List<Address> get(String description) throws IllegalStateException;

    /**
     * Get the nearest place that matches the description
     *
     * @param description The description of the address to find
     * @return The closest address to the specified location
     */
    Address getNearest(String description) throws IllegalStateException;
}
