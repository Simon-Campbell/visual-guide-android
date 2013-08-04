package nz.ac.waikato.ssc10.BlindAssistant;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import nz.ac.waikato.ssc10.map.GoogleWalkingDirections;
import nz.ac.waikato.ssc10.map.NoSuchRouteException;
import nz.ac.waikato.ssc10.map.WalkingDirections;
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
public class BlindAssistant {
    private static final String TAG = "BlindAssistant";

    private VoiceMethodFactory voiceMethodFactory;
    private Context context;
    private TextToSpeech tts;

    private boolean isNavigating = false;
    private IncrementalNavigator navigator = null;

    public BlindAssistant(Context context) {
        Log.d(TAG, "The blind assistant has been started");

        this.context = context;
        this.voiceMethodFactory = VoiceMethodFactory.createStandardFactory();

        // We need to assist the user using a TTS listener!
        this.tts = new TextToSpeech(this.context, new TextToSpeechInitListener());
        this.navigator = new IncrementalNavigator((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));
    }

    public void assist(List<String> request) {
        Log.d(TAG, "The blind assistant is assisting the user with the request " + request);

        say("I cannot assist with this input");
    }

    public void sayCurrentLocation() {
        say("you are currently at " + getCurrentLocationName());
    }

    public void navigateTo(String destination) {
        if (destination != null) {
            final String from = getCurrentLocationName();
            final String to = destination + " New Zealand";

            say("getting directions to " + destination);

            WalkingDirectionsTask task = new WalkingDirectionsTask();
            task.execute(from, to);
        } else {
            say("stopping navigation");

            navigator.setWalkingDirections(null);
        }
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

    public void assist(String request) {
        Log.d(TAG, "The blind assistant is assisting the user with the request " + request);

        Pair<VoiceMethod, Map<String, String>> m = voiceMethodFactory.get(request);
        VoiceMethod method = m.getValue0();

        try {
            if (m != null) {
                method.invoke(this, m.getValue1());
            } else {
                say("I am unable to help you with the request. I heard " + request);
            }
        } finally {
        }
    }

    public String getCurrentLocationName() {
        return getLocationName(navigator.getLastLocation());
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
}
