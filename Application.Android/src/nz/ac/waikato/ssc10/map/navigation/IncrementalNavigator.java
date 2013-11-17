package nz.ac.waikato.ssc10.map.navigation;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Address;
import android.location.Location;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import nz.ac.waikato.ssc10.BlindAssistant.services.WalkingDirectionsUpdateService;
import nz.ac.waikato.ssc10.map.CompassProvider;
import nz.ac.waikato.ssc10.map.NoSuchRouteException;
import nz.ac.waikato.ssc10.map.geocode.CachedGeocoder;
import nz.ac.waikato.ssc10.map.interfaces.ContextualWalkingDirections;
import nz.ac.waikato.ssc10.map.interfaces.WalkingDirections;

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

    private float headingBearing;
    private float facingBearing;

    // The time in ms when the beeper last beeped!
    private long lastBeepTime = 0;

    private static final String TAG = "IncrementalNavigator";
    private final Handler directionsUpdateHandler = new Handler() {
        @Override
        public void handleMessage(Message message) {
            switch (message.what) {
                case WalkingDirectionsUpdateService.RESULT_OK:
                    setWalkingDirections((WalkingDirections) message.obj);

                    if (navigatorUpdateListener != null) {
                        navigatorUpdateListener.onPathUpdated(IncrementalNavigator.this, walkingDirections.getSteps());
                    }

                    break;
                case WalkingDirectionsUpdateService.RESULT_NO_ROUTE:
                    Log.w(TAG, "A route was unable to be automatically generated");
                    break;
            }
        }
    };

    private final Messenger directionsUpdateMessenger = new Messenger(directionsUpdateHandler);

    private int currentIdx = 0;

    private Queue<Location> movementHistory;
    private NavigatorUpdateListener navigatorUpdateListener;

    private Context context;
    private LocationClient locationClient;
    private CachedGeocoder geocoder;
    private LocationRequest locationRequest;
    private CompassProvider compassProvider;

    private ContextualWalkingDirections walkingDirections;

    private LocationListener locationListener = new IncrementalNavigatorLocationListener();
    private ToneGenerator toneGenerator;

    public void beep() {
        toneGenerator.startTone(ToneGenerator.TONE_PROP_BEEP);
    }

    public IncrementalNavigator(Context context, LocationClient locationClient, CompassProvider compassProvider, CachedGeocoder geocoder) {
        this.context = context;
        this.bindUpdateService(context);

        this.compassProvider = compassProvider;
        this.geocoder = geocoder;
        this.toneGenerator = new ToneGenerator(AudioManager.STREAM_VOICE_CALL, ToneGenerator.MAX_VOLUME);

        this.locationRequest = LocationRequest.create();
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        this.locationRequest.setInterval(5000);
        this.locationRequest.setFastestInterval(1000);

        this.locationClient = locationClient;

        this.headingBearing = locationClient.getLastLocation().getBearing();

        this.movementHistory = new ArrayBlockingQueue<Location>(128);
    }

    private WalkingDirectionsUpdateService walkingDirectionsUpdateService;

    private ServiceConnection updateServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            WalkingDirectionsUpdateService.WalkingDirectionsUpdateServiceBinder
                    binder = (WalkingDirectionsUpdateService.WalkingDirectionsUpdateServiceBinder) iBinder;

            walkingDirectionsUpdateService = binder.getService();
            walkingDirectionsUpdateService.setNavigator(IncrementalNavigator.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            walkingDirectionsUpdateService = null;
        }
    };

    private boolean bindUpdateService(Context context) {
        Intent intent = new Intent(context, WalkingDirectionsUpdateService.class);
        return context.bindService(intent, updateServiceConnection, Context.BIND_AUTO_CREATE);
    }

    private void unbindUpdateService() {
        context.unbindService(updateServiceConnection);
    }

    public double getLastFacingBearing() {
        return compassProvider.getBearing();
    }

    public void setNavigatorUpdateListener(NavigatorUpdateListener listener) {
        this.navigatorUpdateListener = listener;
    }

    public void shutdown() {
        this.locationClient.removeLocationUpdates(locationListener);
        this.toneGenerator.release();
        this.unbindUpdateService();
    }


    /**
     * Re-route from the specified location to the already specified
     * location.
     *
     * @param location
     */
    public WalkingDirections routeFrom(Location location) throws NoSuchRouteException {
        return walkingDirections.routeFrom(location);
    }

    public void setWalkingDirections(WalkingDirections walkingDirections) {
        // The method assumes that by setting the walking directions object you are
        // starting from the beginning.
        this.currentIdx = 0;
        this.lastBeepTime = System.currentTimeMillis();
        this.walkingDirections = new DisabilityWalkingDirections(walkingDirections, geocoder);

        if (walkingDirections != null) {
            if (navigatorUpdateListener != null) {
                navigatorUpdateListener.onNavigationStepChange(this, walkingDirections.getSteps().get(0));
            }

            this.locationClient.requestLocationUpdates(locationRequest, locationListener);
        } else {
            this.locationClient.removeLocationUpdates(locationListener);
        }
    }

    public Location getLocation() {
        return this.locationClient.getLastLocation();
    }

    public String getCurrentInstruction() {
        NavigationStep current = getCurrentStep();

        if (current != null) {
            return current.getInstruction();
        } else {
            return "";
        }
    }

    /**
     * Gets the current step that is being navigated, if the navigator is finished or
     * not navigating then null will be returned.
     *
     * @return The current navigation step, null if navigator is not actively navigating
     */
    public NavigationStep getCurrentStep() {
        NavigationStep current = null;

        if (walkingDirections != null) {
            List<NavigationStep> steps = walkingDirections.getSteps();

            if (currentIdx >= 0 && currentIdx < steps.size()) {
                current = steps.get(currentIdx);
            }
        }

        return current;
    }

    public NavigationStep getNextStep() {
        NavigationStep next = null;

        if (walkingDirections != null) {
            int nextIdx = (currentIdx + 1);
            List<NavigationStep> steps = walkingDirections.getSteps();

            if (nextIdx >= 0 && nextIdx < steps.size()) {
                next = steps.get(nextIdx);
            }
        }

        return next;
    }

    private Location getNextCheckpoint() {
        Location next = null;
        List<NavigationStep> steps = walkingDirections.getSteps();

        if (currentIdx < steps.size()) {
            next = steps.get(currentIdx).getEndLocation();
        }

        return next;
    }

    private class IncrementalNavigatorLocationListener implements LocationListener {
        private Location anchorLocation = null;

        private Address lastGeocodeAddress = null;

        private long lastGeocodeUpdate = 0;

        private static final double MOVE_DISTANCE_THRESH = 2.5;
        private static final double UPDATE_DISTANCE_THRESH = 10.0;

        /**
         * Detect a thoroughfare change based on the passed location
         *
         * @param location The new location to compare to the previous
         *                 location and thus detect a street change.
         */
        private void detectThoroughfareChange(Location location) {
            assert location != null;

            Log.d(TAG, "Detecting street change for location " + location);

            if (hasThoroughfareUpdateThresholdPassed()) {
                Address currentAddress = getAddress(location);

                if (currentAddress != null) {
                    Log.i(TAG, "Current address is " + currentAddress);

                    if (lastGeocodeAddress != null && isThoroughfareDifferent(currentAddress)) {
                        onThoroughfareChange(location, currentAddress.getThoroughfare(), lastGeocodeAddress.getThoroughfare());
                    }

                    lastGeocodeAddress = currentAddress;
                }

                lastGeocodeUpdate = System.currentTimeMillis();
            }
        }

        private Address getAddress(Location location) {
            assert location != null;
            Address currentAddress = null;

            try {
                List<Address> results = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                if (results != null && results.size() != 0) {
                    currentAddress = results.get(0);
                } else {
                    Log.w(TAG, "No address was found for the specified location " + location);
                }
            } catch (IOException ex) {
                ex.printStackTrace();

                currentAddress = null;
            }

            return currentAddress;
        }

        /**
         * Checks if the update threshold for thoroughfare updates has passed. This is essential so that
         * the geolocation limit is not exceeded.
         *
         * @return True if the threshold period has passed (do an update), false otherwise
         */
        private boolean hasThoroughfareUpdateThresholdPassed() {
            long now = System.currentTimeMillis();
            return (now - lastGeocodeUpdate) > GEOCODE_PREFERRED_UPDATE_INTERVAL_MILLISECONDS;
        }

        private boolean isThoroughfareDifferent(Address currentAddress) {
            assert currentAddress != null;

            if (lastGeocodeAddress != null) {
                String current = currentAddress.getThoroughfare();
                String last = lastGeocodeAddress.getThoroughfare();

                return (!current.equals(last));
            }

            return true;
        }

        private void onThoroughfareChange(Location l, String current, String previous) {
            Log.d(TAG, "The street has changed from " + previous + " to " + current);

            startWalkingDirectionsUpdateService(l);
        }

        private void startWalkingDirectionsUpdateService(Location rerouteFrom) {
            assert rerouteFrom != null;

            Intent intent = new Intent(context, WalkingDirectionsUpdateService.class);
            intent.setAction(WalkingDirectionsUpdateService.ACTION_UPDATE_ROUTE);

            intent.putExtra(WalkingDirectionsUpdateService.EXTRA_LOCATION, rerouteFrom);
            intent.putExtra(WalkingDirectionsUpdateService.EXTRA_MESSENGER, directionsUpdateMessenger);

            if (walkingDirectionsUpdateService != null) {
                walkingDirectionsUpdateService.startService(intent);
            } else {
                Log.e(TAG, "No instance of the service was available to start.");
            }
        }

        private boolean hasBeepThresholdPassed(float distance) {
            long now = System.currentTimeMillis();

            final double Ca = 90.0;
            final int Ddiff = 0;
            final double R = Math.PI;

            double periodMilliseconds = (1000 * ((distance * (distance / R)) / Ca)) - Ddiff;

            return (now - lastBeepTime) > periodMilliseconds;
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

            detectThoroughfareChange(location);

            if (navigatorUpdateListener != null && walkingDirections != null) {
                // Tell the walking directions object about the current
                // context
                walkingDirections.tell(location);

                // After the context has changed we'll need to
                // get the current step
                NavigationStep current = getCurrentStep();

                if (current != null) {
                    final float distanceTo = getDistanceTo(location, current.getEndLocation());

                    if (hasBeepThresholdPassed(distanceTo)) {
                        beep();

                        lastBeepTime = System.currentTimeMillis();
                    }

                    detectStepChange(distanceTo);
                    detectMovementFromNextStep(location, distanceTo);
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

        private void detectMovementFromNextStep(Location location, float distanceTo) {
            // If we are still navigating then we will check if the
            // user is walking away from the next point by doing
            // a distance check
            NavigationStep current = getCurrentStep();

            if (current != null) {
                float diff = getDistanceTo(anchorLocation, current.getEndLocation()) - distanceTo;

                if (diff > MOVE_DISTANCE_THRESH) {
                    anchorLocation = location;
                } else if (diff < -MOVE_DISTANCE_THRESH) {
                    anchorLocation = location;

                    navigatorUpdateListener.onMoveFromPath(IncrementalNavigator.this, 0.0);
                }
            }
        }

        private void detectStepChange(float distanceTo) {
            NavigationStep next = getNextStep();

            if (isNextStepNearby(distanceTo)) {
                currentIdx++;

                navigatorUpdateListener.onNavigationStepChange(IncrementalNavigator.this, next);

                // If we have no more steps to get to then,
                // we'll kill the updates.
                if (next == null) {
                    stopNavigating();
                }
            }
        }

        private void stopNavigating() {
            locationClient.removeLocationUpdates(locationListener);

            anchorLocation = null;
        }

        private boolean isNextStepNearby(float distanceTo) {
            return distanceTo < UPDATE_DISTANCE_THRESH;
        }

        private float getDistanceTo(Location from, Location to) {
            float[] res = new float[1];
            Location.distanceBetween(from.getLatitude(), from.getLongitude(), to.getLatitude(), to.getLongitude(), res);
            return res[0];
        }
    }
}
