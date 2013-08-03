package nz.ac.waikato.ssc10.BlindAssistant;

import android.content.Context;
import android.location.*;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Locale;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 9/07/13
 * Time: 2:12 PM
 * To change this template use File | Settings | File Templates.
 */
public class BlindAssistant {
    private static final String TAG = "BlindAssistant";

    private VoiceMethodFactory mMapper;
    private Context mContext;
    private TextToSpeech mTts;

    private boolean isNavigating = false;

    private LocationManager mLocationManager;
    private Location mLastLocation;
    private LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "The location has changed to " + location.toString());

            mLastLocation = location;
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

    public BlindAssistant(Context context) {
        Log.d(TAG, "The blind assistant has been started");

        mContext = context;
        mMapper = new VoiceMethodFactory();

        // We need to assist the user using a TTS listener!
        mTts = new TextToSpeech(mContext, new TextToSpeechInitListener());

        // Set up this class so that it can track
        // the users location
        setUpLocationTracking();
        //startLocationTracking();
    }

    private void setUpLocationTracking() {
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mLastLocation = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
    }

    private void startLocationTracking() {
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
    }

    public void assist(List<String> request) {
        Log.d(TAG, "The blind assistant is assisting the user with the request " + request);

        say("I cannot assist with this input");
    }

    public void assist(String request) {
        Log.d(TAG, "The blind assistant is assisting the user with the request " + request);

        Method m = mMapper.get(request);

        try {
            if (m != null) {
                m.invoke(mMapper, this);
            } else {
                say("I am unable to help you with the request. I heard " + request);
            }
        } catch (IllegalAccessException e) {
            say("The method invoked was not able to be accessed");
        } catch (InvocationTargetException e) {
            say("The invocation suffered from an exception");
        } finally {
        }
    }

    public boolean isNavigating() {
        return this.isNavigating;
    }

    public String getLocationName() {
        String locationName;
        Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());

        if (mLastLocation != null) {
            try {
                List<Address> addresses = geocoder.getFromLocation(mLastLocation.getLatitude(), mLastLocation.getLongitude(), 1);

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
        mTts.speak(text, TextToSpeech.QUEUE_ADD, null);
    }

    public void shutdown() {
        mTts.shutdown();
        mLocationManager.removeUpdates(mLocationListener);
    }

    private class TextToSpeechInitListener implements TextToSpeech.OnInitListener {

        @Override
        public void onInit(int i) {
        }
    }
}
