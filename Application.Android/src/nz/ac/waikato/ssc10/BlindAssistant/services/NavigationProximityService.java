package nz.ac.waikato.ssc10.BlindAssistant.services;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.LocationClient;

import java.util.List;

/**
 * A service which listens for proximity alerts set by a
 * navigator and deals with them.
 */
public class NavigationProximityService extends IntentService {
    public static final String TAG = "NavigationProximityService";

    public NavigationProximityService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (LocationClient.hasError(intent)) {
            int errorCode = LocationClient.getErrorCode(intent);

            Log.e(TAG, "Got error code " + errorCode);
        } else {
            int transitionType = LocationClient.getGeofenceTransition(intent);

            if (transitionType == Geofence.GEOFENCE_TRANSITION_ENTER) {
                List<Geofence> triggerList =
                        LocationClient.getTriggeringGeofences(intent);

                String[] triggerIds = new String[triggerList.size()];

                for (int i = 0; i < triggerIds.length; i++) {
                    // Store the Id of each geofence ...
                    triggerIds[i] = triggerList.get(i).getRequestId();
                }
            } else {

            }
        }
    }
}
