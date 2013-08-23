package nz.ac.waikato.ssc10.navigation;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import nz.ac.waikato.ssc10.map.LatLng;
import nz.ac.waikato.ssc10.map.WalkingDirections;

import java.io.IOException;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * This class listens to a location provider and checks it against a defined
 * route. Other classes can listen in for the events of the navigator and request
 * information about the navigation.
 */
public class IncrementalNavigator {
    public static final double PATH_BEARING_THRESH_DEG = 90.0;

    private static final int MILLISECONDS_PER_SECOND = 1000;
    private static final int SECONDS_PER_DAY = 86400;

    private static final int HOURS_PER_DAY = 24;
    private static final int HOURS_USER_IS_AWAKE = 12;

    private static final int PREFERRED_GEOCODE_UPDATES_PER_DAY = 2000;
    private static final int MAX_GEOCODE_UPDATES_PER_DAY = 2500;

    private static final int GEOCODE_PREFERRED_UPDATE_INTERVAL_SECONDS =
            ((SECONDS_PER_DAY / (HOURS_PER_DAY / HOURS_USER_IS_AWAKE))
                    / PREFERRED_GEOCODE_UPDATES_PER_DAY);

    private static final int GEOCODE_PREFERRED_UPDATE_INTERVAL_MILLISECONDS =
            GEOCODE_PREFERRED_UPDATE_INTERVAL_SECONDS * MILLISECONDS_PER_SECOND;

    private static String TAG = "IncrementalNavigator";

    private int currentIdx = 0;

    /**
     * The bearing that the phone is heading
     */
    private double headingBearing = 0.0;

    /**
     * The last calculated distance from the persons location to
     * the next destination.
     */
    private double nextDestinationLength = 0.0;

    /**
     * The last calculated 'initial' bearing from the persons location
     * to the next destination.
     */
    private double nextDestinationBearing = 0.0;

    private Queue<Location> movementHistory;
    private NavigatorUpdateListener navigatorUpdateListener;

    private LocationClient locationClient;
    private Geocoder geocoder;
    private LocationRequest locationRequest;
    private CompassProvider compassProvider;

    private WalkingDirections walkingDirections;

    private LocationListener locationListener = new LocationListener() {
        private Location anchorLocation = null;

        private Address lastGeocodeAddress = null;
        private long lastGeocodeUpdate = 0;

        private static final double MOVE_DISTANCE_THRESH = 2.5;
        private static final double UPDATE_DISTANCE_THRESH = 10.0;

        /**
         * Detect a street change based on the passed location
         * @param l The new location to compare to the previous
         *          location and thus detect a street change.
         */
        private void detectStreetChange(Location l) {
            long now = System.currentTimeMillis();

            // If the time difference is greater than the preferred time
            // then we'll do a check
            if ((now - lastGeocodeUpdate) > GEOCODE_PREFERRED_UPDATE_INTERVAL_MILLISECONDS) {

                try {
                    List<Address> results = geocoder.getFromLocation(l.getLatitude(), l.getLongitude(), 1);

                    if (results != null && results.size() != 0) {
                        Address currentAddress = results.get(0);

                        if (lastGeocodeAddress == null) {
                            lastGeocodeAddress = currentAddress;
                        } else {
                            String current = currentAddress.getThoroughfare();
                            String last = lastGeocodeAddress.getThoroughfare();

                            if (!current.equals(last)) {
                                Log.d(TAG, "The street has changed from " + last + " to " + current);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    lastGeocodeUpdate = System.currentTimeMillis();
                }
            }
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "The location has changed to " + location.toString());

            if (anchorLocation == null) {
                anchorLocation = location;
            }

            if (location.hasBearing()) {
                headingBearing = location.getBearing();
            }

            if (navigatorUpdateListener != null && walkingDirections != null) {
                List<NavigationStep> steps = walkingDirections.getSteps();

                NavigationStep current = steps.get(currentIdx);
                NavigationStep next = currentIdx + 1 < steps.size() ? steps.get(currentIdx + 1) : null;

                if (current != null) {
                    LatLng endLatLng = current.getEndLocation();
                    final float distanceTo = getDistanceTo(location, endLatLng);

                    if (distanceTo < UPDATE_DISTANCE_THRESH) {
                        currentIdx++;

                        navigatorUpdateListener.onNavigationStepChange(IncrementalNavigator.this, next);

                        // If we have no more steps to get to then,
                        // we'll kill the updates.

                        // TODO: Make sure that no more updates happen after
                        // this.
                        if (next == null) {
                            locationClient.removeLocationUpdates(locationListener);

                            anchorLocation = null;
                        }
                    }

                    float diff = getDistanceTo(anchorLocation, endLatLng) - distanceTo;

                    if (diff > MOVE_DISTANCE_THRESH) {
                        anchorLocation = location;
                    } else if (diff < -MOVE_DISTANCE_THRESH) {
                        anchorLocation = location;

                        navigatorUpdateListener.onMoveFromPath(IncrementalNavigator.this, 0.0);
                    }
                }

            }

            // Offer the location to the history queue, if
            // it fails then try removing the head and then
            // adding it.
            if (!movementHistory.offer(location)) {
                movementHistory.remove();
                movementHistory.add(location);
            }
        }

        private float getDistanceTo(Location from, LatLng to) {
            float[] res = new float[1];
            Location.distanceBetween(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude(), res);
            return res[0];
        }
    };

    public IncrementalNavigator(LocationClient locationClient, CompassProvider compassProvider, Geocoder geocoder) {
        this.compassProvider = compassProvider;

        this.locationRequest = LocationRequest.create();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationRequest.setInterval(5000);
        this.locationRequest.setFastestInterval(1000);

        this.locationClient = locationClient;

        this.headingBearing = locationClient.getLastLocation().getBearing();

        this.movementHistory = new ArrayBlockingQueue<Location>(128);
    }

    public double getLastFacingBearing() {
        return compassProvider.getBearing();
    }

    public void setNavigatorUpdateListener(NavigatorUpdateListener listener) {
        this.navigatorUpdateListener = listener;
    }

    public void shutdown() {
        this.locationClient.removeLocationUpdates(locationListener);
    }

    public void setWalkingDirections(WalkingDirections walkingDirections) {
        // The method assumes that by setting the walking directions object you are
        // starting from the beginning.
        this.currentIdx = 0;
        this.walkingDirections = walkingDirections;

        if (walkingDirections != null) {
            if (navigatorUpdateListener != null) {
                navigatorUpdateListener.onNavigationStepChange(this, walkingDirections.getSteps().get(0));
            }

            this.locationClient.requestLocationUpdates(locationRequest, locationListener);
        } else {
            this.locationClient.removeLocationUpdates(locationListener);
        }
    }

    public Location getLastLocation() {
        return this.locationClient.getLastLocation();
    }

    public String getCurrentInstruction() {
        return walkingDirections
                .getSteps()
                .get(currentIdx)
                .getInstruction();
    }

    private LatLng getLastCheckpoint() {
        return walkingDirections
                .getSteps()
                .get(currentIdx)
                .getStartLocation();
    }

    private LatLng getNextCheckpoint() {
        LatLng next = null;
        List<NavigationStep> steps = walkingDirections.getSteps();

        if (currentIdx < steps.size()) {
            next = steps.get(currentIdx).getEndLocation();
        }

        return next;
    }

    private int getNextRouteIndex() {
        return currentIdx + 1;
    }
}
