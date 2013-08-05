package nz.ac.waikato.ssc10.BlindAssistant;

import android.location.Location;
import android.util.Log;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
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
    private float headingBearing = 0.0f;

    private Queue<Location> movementHistory;
    private NavigatorUpdateListener navigatorUpdateListener;

    private LocationClient locationClient;
    private LocationRequest locationRequest;

    private WalkingDirections walkingDirections;

    private LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "The location has changed to " + location.toString());

            if (navigatorUpdateListener != null && walkingDirections != null) {
                navigatorUpdateListener.onNavigationStepChange(IncrementalNavigator.this, walkingDirections.getSteps().get(currentIdx));
            }

            movementHistory.add(location);
        }
    };

    public IncrementalNavigator(LocationClient locationClient) {
        this.locationRequest = LocationRequest.create();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationRequest.setInterval(5000);
        this.locationRequest.setFastestInterval(1000);
        this.locationClient = locationClient;
        this.headingBearing = locationClient
                .getLastLocation()
                .getBearing();

        this.movementHistory = new ArrayBlockingQueue<Location>(128);
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
