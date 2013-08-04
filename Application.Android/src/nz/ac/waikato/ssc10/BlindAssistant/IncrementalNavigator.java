package nz.ac.waikato.ssc10.BlindAssistant;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import nz.ac.waikato.ssc10.map.LatLng;
import nz.ac.waikato.ssc10.map.WalkingDirections;
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
    private static String TAG = "IncrementalNavigator";

    private int currentIdx = 0;
    private float bearing;

    private Queue<Location> movementHistory;
    private NavigatorUpdateListener navigatorUpdateListener;

    private LocationManager locationManager;
    private WalkingDirections walkingDirections;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "The location has changed to " + location.toString());

            movementHistory.add(location);
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.d(TAG, "The status has changed to '" + s + "'");
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.d(TAG, "The provider '" + s + "' has been enabled");
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.d(TAG, "The provider '" + s + "' has been disabled");
        }
    };

    public IncrementalNavigator(LocationManager locationManager) {
        this.locationManager = locationManager;
        this.movementHistory = new ArrayBlockingQueue<Location>(128);
    }

    public void setNavigatorUpdateListener(NavigatorUpdateListener listener) {
        this.navigatorUpdateListener = listener;
    }

    public void shutdown() {
        this.locationManager.removeUpdates(locationListener);
    }

    public void setWalkingDirections(WalkingDirections walkingDirections) {
        this.walkingDirections = walkingDirections;

        if (walkingDirections != null) {
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        } else {
            this.locationManager.removeUpdates(locationListener);
        }
    }

    public Location getLastLocation() {
        return this.locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
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

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    private int getNextRouteIndex() {
        return currentIdx + 1;
    }
}
