package nz.ac.waikato.ssc10.BlindAssistant;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * A service which aids with the usability of the system. It allows for media buttons to be pressed
 * while the app is in the background, it also allows for the navigation service to persist without
 * being killed
 */
public class BlindAssistantService extends Service {
    private static final String TAG = "BlindAssistantService";
    private static final int NOTIFICATION = 0xdeadbeef;

    private final IBinder binder = new BlindAssistantBinder();

    private NotificationManager notificationManager;

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class BlindAssistantBinder extends Binder {
        BlindAssistantService getService() {
            return BlindAssistantService.this;
        }
    }

    @Override
    public void onCreate() {
        this.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);

        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        notificationManager.cancel(NOTIFICATION);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.binder;
    }

    private void showNotification() {
        // The PendingIntent to launch our activity if the user selects this notification
        PendingIntent contentIntent =
                PendingIntent
                        .getActivity(this, 0,
                                new Intent(this, VoicePromptActivity.class), 0);

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification.Builder(this)
                .setContentTitle("Blind Assistant Service")
                .setSmallIcon(R.drawable.icon)
                .setOngoing(true)
                .setContentText("The service has started")
                .setContentIntent(contentIntent)
                .build();

        notificationManager.notify(NOTIFICATION, notification);
    }
}
