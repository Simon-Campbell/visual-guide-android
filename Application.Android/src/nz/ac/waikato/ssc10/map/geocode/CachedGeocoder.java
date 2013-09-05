package nz.ac.waikato.ssc10.map.geocode;

import android.location.Address;
import android.util.Log;
import android.util.LongSparseArray;

import java.io.IOException;
import java.util.List;

/**
 *
 */
public class CachedGeocoder {
    private static final String TAG = "CachedGeocoder";

    public static final double MAX_LATITUDE = 90.0;
    public static final double MAX_LONGITUDE = 180.0;

    private android.location.Geocoder geocoder;
    private LongSparseArray<GeocodeResult> cache = new LongSparseArray<GeocodeResult>();

    // The higher this is means that the caching will be more
    // accurate. Making it too high will mean a lot more entries
    // are cached and thus more processing is needed and memory
    private final int ACCURACY_LEVEL = 4;
    private final int INDEX_MULTIPLIER = (int) (10 * Math.pow(10.0, ACCURACY_LEVEL));

    private final int LATITUDE_HEIGHT = (int) (MAX_LATITUDE * 2) * INDEX_MULTIPLIER;
    private final int LONGITUDE_WIDTH = (int) (MAX_LONGITUDE * 2) * INDEX_MULTIPLIER;

    private long getSparseIndex(double latitude, double longitude) {
        // shift into positive, then multiply it by idx_multiplier before
        // rounding out the decimal places
        long latIdx = Math.round((latitude + MAX_LATITUDE) * INDEX_MULTIPLIER);
        long lngIdx = Math.round((longitude + MAX_LONGITUDE) * INDEX_MULTIPLIER);

        return (latIdx * LONGITUDE_WIDTH) + lngIdx;
    }

    public CachedGeocoder(android.location.Geocoder geocoder) {
        this.geocoder = geocoder;
    }

    public List<Address> getFromLocation(double latitude, double longitude, int maxResults) throws IOException {
        Log.i(TAG, String.format("getFromLocation(%f,%f,%d)", latitude, longitude, maxResults));

        long idx = getSparseIndex(latitude, longitude);

        List<Address> result;
        GeocodeResult geoResult = cache.get(idx);

        // If there is a result already at the lat/lng then we'll
        // check the number of results. If we do not have enough then
        // we'll re-fetch with the higher count.
        if (geoResult != null) {
            if (geoResult.addresses.size() < maxResults) {
                Log.v(TAG, "Not enough results in cached entry. Redoing a lookup with more results");

                result = geocoder.getFromLocation(latitude, longitude, maxResults);

                // Update the GeocodeResult with a new, improved version and
                // put it in the cache
                geoResult = new GeocodeResult(result, latitude, longitude);
                cache.put(idx, geoResult);
            } else {
                Log.v(TAG, "Found a cached geocoder result.");

                result = geoResult.first(maxResults);
            }
        } else {
            Log.v(TAG, "No cached geocoding entry. Doing a lookup.");

            result = geocoder.getFromLocation(latitude, longitude, maxResults);

            // Update the GeocodeResult with a new, improved version and
            // put it in the cache
            geoResult = new GeocodeResult(result, latitude, longitude);
            cache.put(idx, geoResult);
        }

        return result;
    }

    /**
     * A class that describes the result of geocoding a specified
     * latitude/longitude
     */
    private class GeocodeResult {
        private List<Address> addresses;

        private double latitude;
        private double longitude;

        /**
         * Return a view of the first n elements in the result
         * list held by this geocode result.
         *
         * @param n The number of elements to take from the list and
         *          put in the view
         * @return A list of n elements from the start of the list
         */
        public List<Address> first(int n) {
            if (n == addresses.size()) {
                return addresses;
            } else {
                return addresses.subList(0, n);
            }
        }

        public GeocodeResult(List<Address> addresses, double latitude, double longitude) {
            this.addresses = addresses;

            this.latitude = latitude;
            this.longitude = longitude;
        }
    }
}
