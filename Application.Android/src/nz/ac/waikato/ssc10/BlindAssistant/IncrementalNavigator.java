package nz.ac.waikato.ssc10.BlindAssistant;

import android.location.Location;
import android.util.Log;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import nz.ac.waikato.ssc10.map.LatLng;
import nz.ac.waikato.ssc10.map.WalkingDirections;
import nz.ac.waikato.ssc10.navigation.CompassProvider;
import nz.ac.waikato.ssc10.navigation.NavigationStep;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 10/07/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class IncrementalNavigator {
    public static final double UPDATE_DISTANCE_THRESHOLD = 10.0;
    private static String TAG = "IncrementalNavigator";

    private int currentIdx = 0;

    private double headingBearing = 0.0;
    private double facingBearing = 0.0;

    private Queue<Location> movementHistory;
    private NavigatorUpdateListener navigatorUpdateListener;

    private LocationClient locationClient;
    private LocationRequest locationRequest;
    private CompassProvider compassProvider;

    private WalkingDirections walkingDirections;
    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "The location has changed to " + location.toString());

            if (location.hasBearing()) {
                headingBearing = location.getBearing();
            }

            if (navigatorUpdateListener != null && walkingDirections != null) {
                List<NavigationStep> steps = walkingDirections.getSteps();

                NavigationStep current = steps.get(currentIdx);
                NavigationStep next = currentIdx + 1 < steps.size() ? steps.get(currentIdx + 1) : null;

                if (current != null) {
                    float[] results = new float[2];

                    LatLng latLng = current.getEndLocation();
                    Location.distanceBetween(location.getLatitude(), location.getLongitude(), latLng.getLatitude(), latLng.getLongitude(), results);

                    if (results[0] < UPDATE_DISTANCE_THRESHOLD) {
                        currentIdx++;

                        navigatorUpdateListener.onNavigationStepChange(IncrementalNavigator.this, next);

                        // If we have no more steps to get to then,
                        // we'll kill the updates.

                        // TODO: Make sure that no more updates happen after
                        // this.
                        if (next == null) {
                            locationClient.removeLocationUpdates(locationListener);
                        }
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
    };

    private CompassProvider.CompassChangedListener compassChangedListener = new CompassProvider.CompassChangedListener() {
        @Override
        public void onCompassBearingChanged(double oldBearing, double newBearing) {
            facingBearing = newBearing;
        }
    };


    public IncrementalNavigator(LocationClient locationClient, CompassProvider compassProvider) {
        this.compassProvider = compassProvider;
        this.compassProvider.setCompassChangedListener(compassChangedListener);

        this.facingBearing = compassProvider.getBearing();

        this.locationRequest = LocationRequest.create();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationRequest.setInterval(5000);
        this.locationRequest.setFastestInterval(1000);

        this.locationClient = locationClient;

        this.headingBearing = locationClient.getLastLocation().getBearing();

        this.movementHistory = new ArrayBlockingQueue<Location>(128);
    }

    public double getLastFacingBearing() {
        return this.facingBearing;
    }

    public void setNavigatorUpdateListener(NavigatorUpdateListener listener) {
        this.navigatorUpdateListener = listener;
    }

    public void shutdown() {
        this.locationClient.removeLocationUpdates(locationListener);
        this.locationClient.disconnect();
    }

    public void setWalkingDirections(WalkingDirections walkingDirections) {
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
