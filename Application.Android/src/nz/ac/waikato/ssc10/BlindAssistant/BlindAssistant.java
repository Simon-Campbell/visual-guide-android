package nz.ac.waikato.ssc10.BlindAssistant;

import android.content.Context;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import nz.ac.waikato.ssc10.input.BluetoothHeadsetHelper;
import nz.ac.waikato.ssc10.input.VoiceMethod;
import nz.ac.waikato.ssc10.input.VoiceMethodFactory;
import nz.ac.waikato.ssc10.map.GoogleWalkingDirections;
import nz.ac.waikato.ssc10.map.LatLng;
import nz.ac.waikato.ssc10.map.NoSuchRouteException;
import nz.ac.waikato.ssc10.map.WalkingDirections;
import nz.ac.waikato.ssc10.navigation.CompassProvider;
import nz.ac.waikato.ssc10.navigation.IncrementalNavigator;
import nz.ac.waikato.ssc10.navigation.NavigationStep;
import nz.ac.waikato.ssc10.navigation.NavigatorUpdateListener;
import org.javatuples.Pair;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 9/07/13
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlindAssistant implements NavigatorUpdateListener {
    private static final String TAG = "BlindAssistant";

    private VoiceMethodFactory voiceMethodFactory;
    private Location lastLocation;
    private Context context;
    private TextToSpeech tts;

    private boolean isNavigating = false;

    private SensorManager sensorManager;
    private CompassProvider compassProvider;
    private Geocoder geocoder;

    private IncrementalNavigator navigator = null;
    private LocationClient locationClient = null;

    private BluetoothHeadsetHelper bluetoothHelper;

    public BlindAssistant(Context context, BluetoothHeadsetHelper bluetoothHelper) {
        Log.d(TAG, "The blind assistant has been started");

        this.geocoder = new Geocoder(context, Locale.getDefault());
        this.bluetoothHelper = bluetoothHelper;
        this.context = context;
        this.voiceMethodFactory = VoiceMethodFactory.createStandardFactory();

        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        this.compassProvider = new CompassProvider(sensorManager);

        // We need to assist the user using a TTS listener!
        this.tts = new TextToSpeech(this.context, new TextToSpeechInitListener());

        // Set up a location client, when it connects then we'll create a navigation
        // guide object
        this.locationClient = new LocationClient(context,
                googlePlayerServicesConnectionCallbacks,
                googlePlayServicesOnConnectionFailedListener);

        // Start the connect process!
        this.locationClient.connect();
    }

    public void sayCurrentLocation() {
        say(String.format(context.getString(R.string.say_current_location), getCurrentLocationName()));
    }

    public void navigateTo(String destination) {
        if (navigator == null) {
            say("please wait until the location service has been connected to then try again");

            // ..
            return;
        }

        if (destination != null) {
            final Location location = getCurrentLocation();
            final String stringFrom = getCurrentLocationName();

            final WalkingDirectionsTask task;

            if (location != null) {
                task = new WalkingDirectionsTask(location);

                say("getting directions to " + destination);
                task.execute(destination);
            } else {
                say("unable to get your location. please try again");
            }

        } else {
            say("stopping navigation");

            navigator.setWalkingDirections(null);
        }
    }

    @Override
    public void onPathUpdated(IncrementalNavigator navigator, List<NavigationStep> newSteps) {
        Log.d(TAG, "onPathUpdated -> New steps size = " + newSteps.size());

        say("the path was updated");
    }


    private long lastLookAwayWarning = 0;

    @Override
    public void onMoveFromPath(IncrementalNavigator guide, double bearingTo) {
        long now = System.currentTimeMillis();
        final long WARNING_THRESHOLD = 7500;

        if ((now - lastLookAwayWarning) > WARNING_THRESHOLD) {
            lastLookAwayWarning = now;

            say("you are moving away from the next destination");
        }
    }

    @Override
    public void onNavigationStepChange(IncrementalNavigator guide, NavigationStep step) {
        if (step != null) {
            sayInstruction(guide.getCurrentInstruction());
        } else {
            say("you have arrived at your destination");
        }
    }

    public void sayUserCompassDirection() {
        if (navigator != null) {
            final double bearing = compassProvider.getBearing();

            say(String.format("you are facing %s", CompassProvider.getCardinalFacingDirection(bearing)));
        } else {
            say("your heading direction is unknown");
        }

    }

    private class WalkingDirectionsTask extends AsyncTask<String, Double, WalkingDirections> {
        private Location locationFrom = null;
        private String stringFrom = null;

        public WalkingDirectionsTask(Location from) {
            this.locationFrom = from;
            this.stringFrom = "your current location";
        }

        @Override
        protected WalkingDirections doInBackground(String... strings) {
            WalkingDirections directions = null;

            try {
                if (locationFrom != null) {
                    directions = new GoogleWalkingDirections(locationFrom, strings[0]);
                } else {
                    throw new NoSuchRouteException("no location was specified");
                }
            } catch (NoSuchRouteException ex) {
                Log.e(TAG, "A route was not able to be found", ex);

                say("i was unable to route you from " + stringFrom + " to " + strings[0]);
            } finally {
                return directions;
            }
        }

        @Override
        protected void onPostExecute(WalkingDirections result) {
            navigator.setWalkingDirections(result);
        }
    }

    /**
     * Say the specified text as an instruction
     *
     * @param instruction The text to say as an instruction
     */
    public void sayInstruction(String instruction) {
        HashMap<String, String> params = null;

        if (bluetoothHelper.isBluetoothAudioConnected()) {
            params = new HashMap<String, String>();
            params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_VOICE_CALL));
        }

        tts.speak(instruction, TextToSpeech.QUEUE_FLUSH, params);
    }

    /**
     * If the user is navigating then say the distance to the next point in the navigation route. If they are not
     * navigating then say that they are not currently navigating.
     */
    public void sayDistanceToNextPoint() {
        Location now = locationClient.getLastLocation();
        NavigationStep current = navigator.getCurrentStep();

        if (current != null) {
            LatLng next = current.getEndLocation();

            float[] results = new float[1];
            Location.distanceBetween(now.getLatitude(), now.getLongitude(), next.getLatitude(), next.getLongitude(), results);

            // Round the result because it is irrelevant to the user how many cm they are
            // away from the destination (too much information)
            int distance = Math.round(results[0]);
            say("the next destination is " + distance + " meters away");
        } else {
            say("you are not currently navigating");
        }
    }

    /**
     * If the user is navigating then say the distance to the last (end) point in the navigation route. If they are not
     * navigating then say that they are not currently navigating.
     */
    public void sayDistanceToEnd() {
        Location now = locationClient.getLastLocation();
        NavigationStep current = navigator.getCurrentStep();

        if (current != null) {
            LatLng next = current.getEndLocation();

            float[] results = new float[1];
            Location.distanceBetween(now.getLatitude(), now.getLongitude(), next.getLatitude(), next.getLongitude(), results);

            // Round the result because it is irrelevant to the user how many cm they are
            // away from the destination (too much information)
            int distance = Math.round(results[0]);
            say("the final destination is at least " + distance + " meters away");
        } else {
            say("you are not currently navigating");
        }
    }

    /**
     * Assist the user with a request string from the user
     *
     * @param request The request from the user
     */
    public void assist(String request) {
        Log.d(TAG, "The blind assistant is assisting the user with the request " + request);

        Pair<VoiceMethod, Map<String, String>> m = voiceMethodFactory.get(request);

        try {
            if (m != null) {
                VoiceMethod method = m.getValue0();
                method.invoke(this, m.getValue1());
            } else {
                say("I am unable to help you with the request. I heard " + request);
            }
        } finally {
        }
    }

    private Location getCurrentLocation() {
        if (navigator != null) {
            return navigator.getLastLocation();
        }

        return null;
    }

    public String getCurrentLocationName() {
        if (navigator != null) {
            return getLocationName(navigator.getLastLocation());
        } else {
            return "unknown";
        }
    }

    public String getLocationName(Location location) {
        String locationName;

        if (location != null) {
            try {
                List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);

                if (addresses.isEmpty()) {
                    locationName = "unknown location";
                } else {
                    Address primary = addresses.get(0);
                    locationName = primary.getFeatureName() + " " + expandThoroughfareSuffix(primary.getThoroughfare()) + " " + primary.getCountryName();
                }
            } catch (IOException e) {
                e.printStackTrace();

                locationName = "unknown location";
            }
        } else {
            locationName = "location yet to be determined";
        }

        return locationName;
    }

    private static String expandThoroughfareSuffix(String thoroughfare) {
        return thoroughfare.replaceFirst("\\wRd$", "road");
    }

    public void say(String text) {
        HashMap<String, String> params = null;

        if (bluetoothHelper.isBluetoothAudioConnected()) {
            params = new HashMap<String, String>();
            params.put(TextToSpeech.Engine.KEY_PARAM_STREAM, String.valueOf(AudioManager.STREAM_VOICE_CALL));
        }

        tts.speak(text, TextToSpeech.QUEUE_ADD, params);
    }

    public void shutdown() {
        tts.shutdown();
        navigator.shutdown();
        compassProvider.shutdown();
    }

    private class TextToSpeechInitListener implements TextToSpeech.OnInitListener {

        @Override
        public void onInit(int i) {
        }
    }

    private GooglePlayServicesClient.ConnectionCallbacks googlePlayerServicesConnectionCallbacks = new GooglePlayServicesClient.ConnectionCallbacks() {
        @Override
        public void onConnected(Bundle bundle) {
            say("the application has connected to the location service");

            navigator = new IncrementalNavigator(context, locationClient, compassProvider, geocoder);
            navigator.setNavigatorUpdateListener(BlindAssistant.this);
        }

        @Override
        public void onDisconnected() {
            say("the application has disconnected from the location service");

            navigator.shutdown();
        }
    };

    private GooglePlayServicesClient.OnConnectionFailedListener googlePlayServicesOnConnectionFailedListener = new GooglePlayServicesClient.OnConnectionFailedListener() {
        @Override
        public void onConnectionFailed(ConnectionResult connectionResult) {
            say("the connection to Google Play Services failed");
        }
    };

}
