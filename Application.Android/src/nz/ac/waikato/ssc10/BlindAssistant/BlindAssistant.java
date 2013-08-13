package nz.ac.waikato.ssc10.BlindAssistant;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import nz.ac.waikato.ssc10.map.GoogleWalkingDirections;
import nz.ac.waikato.ssc10.map.NoSuchRouteException;
import nz.ac.waikato.ssc10.map.WalkingDirections;
import nz.ac.waikato.ssc10.navigation.NavigationStep;
import org.javatuples.Pair;

import java.io.IOException;
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

    private IncrementalNavigator navigator = null;
    private LocationClient locationClient = null;

    public BlindAssistant(Context context) {
        Log.d(TAG, "The blind assistant has been started");

        this.context = context;
        this.voiceMethodFactory = VoiceMethodFactory.createStandardFactory();

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
            final String from;

            if (location != null) {
                from = String.format("%f,%f", location.getLatitude(), location.getLongitude());
            } else {
                from = getCurrentLocationName();
            }

            final String to = destination + " New Zealand";
            say("getting directions to " + destination);

            WalkingDirectionsTask task = new WalkingDirectionsTask();
            task.execute(from, to);
        } else {
            say("stopping navigation");

            navigator.setWalkingDirections(null);
        }
    }

    @Override
    public void onNavigationPathChange(IncrementalNavigator guide) {
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
        final float bearing = getCurrentLocation().getBearing();
        String compassDirection;

        if (bearing > 337.5 || bearing <= 22.5)
            compassDirection = "north";
        else if (bearing > 22.5 && bearing <= 67.5)
            compassDirection = "north east";
        else if (bearing > 67.5 && bearing <= 112.5)
            compassDirection = "east";
        else if (bearing > 112.5 && bearing <= 157.5)
            compassDirection = "south east";
        else if (bearing > 157.5 && bearing <= 202.5)
            compassDirection = "south";
        else if (bearing > 202.5 && bearing <= 247.5)
            compassDirection = "south west";
        else if (bearing > 247.5 && bearing <= 292.5)
            compassDirection = "west";
        else
            compassDirection = "north west";

        say(String.format("you are facing %s", compassDirection));
    }

    private class WalkingDirectionsTask extends AsyncTask<String, Double, WalkingDirections> {

        @Override
        protected WalkingDirections doInBackground(String... strings) {
            WalkingDirections directions = null;
            try {
                directions = new GoogleWalkingDirections(strings[0], strings[1]);
            } catch (NoSuchRouteException ex) {
                Log.e(TAG, "A route was not able to be found", ex);

                say("i was unable to route you from " + strings[0] + " to " + strings[1]);
            } finally {
                return directions;
            }
        }

        @Override
        protected void onPostExecute(WalkingDirections result) {
            navigator.setWalkingDirections(result);
        }
    }

    public void sayInstruction(String instruction) {
        tts.speak(instruction, TextToSpeech.QUEUE_FLUSH, null);
    }

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
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());

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
        tts.speak(text, TextToSpeech.QUEUE_ADD, null);
    }

    public void shutdown() {
        tts.shutdown();
        navigator.shutdown();
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

            navigator = new IncrementalNavigator(locationClient);
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
