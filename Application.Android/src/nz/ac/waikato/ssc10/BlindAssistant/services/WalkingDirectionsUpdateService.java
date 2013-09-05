package nz.ac.waikato.ssc10.BlindAssistant.services;

import android.app.IntentService;
import android.content.Intent;
import android.location.Location;
import android.os.*;
import android.util.Log;
import nz.ac.waikato.ssc10.map.NoSuchRouteException;
import nz.ac.waikato.ssc10.map.interfaces.WalkingDirections;
import nz.ac.waikato.ssc10.navigation.IncrementalNavigator;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 26/08/13
 * Time: 5:25 PM
 * To change this template use File | Settings | File Templates.
 */
public class WalkingDirectionsUpdateService extends IntentService {
    private static final String TAG = "WalkingDirectionsUpdateService";

    public static final String ACTION_UPDATE_ROUTE = "action_update_if_new_path";

    public static final String EXTRA_MESSENGER = "extra_messenger";
    public static final String EXTRA_RESULT_RECEIVER = "extra_result_receiver";
    public static final String EXTRA_LOCATION = "extra_location";

    public static final int RESULT_OK = 0;
    public static final int RESULT_NO_ROUTE = 1;

    private IncrementalNavigator navigator;
    private IBinder mBinder = new WalkingDirectionsUpdateServiceBinder();

    public WalkingDirectionsUpdateService() {
        super(TAG);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class WalkingDirectionsUpdateServiceBinder extends Binder {
        public WalkingDirectionsUpdateService getService() {
            return WalkingDirectionsUpdateService.this;
        }
    }

    /**
     * Set the navigator that is read from to the specified
     * navigator
     *
     * @param navigator
     */
    public void setNavigator(IncrementalNavigator navigator) {
        this.navigator = navigator;
    }

    private void updateWalkingDirections(Messenger messenger, Location from) throws RemoteException {
        Message msg = new Message();

        try {
            msg.what = RESULT_OK;
            msg.obj = navigator.routeFrom(from);
            messenger.send(msg);
        } catch (NoSuchRouteException ex) {
            ex.printStackTrace();

            // No such route was found!
            msg.what = RESULT_NO_ROUTE;
            messenger.send(msg);
        } finally {
        }
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i(TAG, "onHandleIntent " + intent);

        if (ACTION_UPDATE_ROUTE.equals(intent.getAction())) {
            Messenger messenger = intent.getParcelableExtra(EXTRA_MESSENGER);
            Location from = intent.getParcelableExtra(EXTRA_LOCATION);

            try {
                updateWalkingDirections(messenger, from);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public interface WalkingDirectionsUpdaterListener {
        void onUpdated(WalkingDirections oldDirections, WalkingDirections newDirections);

        void onNoRouteFound(WalkingDirections oldDirections);
    }

    ;
}
