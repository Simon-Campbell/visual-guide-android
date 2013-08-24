package nz.ac.waikato.ssc10.BlindAssistant.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import nz.ac.waikato.ssc10.BlindAssistant.BlindAssistant;
import nz.ac.waikato.ssc10.BlindAssistant.R;
import nz.ac.waikato.ssc10.BlindAssistant.activities.VoicePromptActivity;
import nz.ac.waikato.ssc10.input.BluetoothHeadsetHelper;
import nz.ac.waikato.ssc10.input.BluetoothSpeechRecognizer;
import nz.ac.waikato.ssc10.util.SpeechRecognizerUtil;

import java.util.ArrayList;

/**
 * A service which aids with the usability of the system. It allows for media buttons to be pressed
 * while the app is in the background, it also allows for the navigation service to persist without
 * being killed
 */
public class BlindAssistantService extends Service {
    private static final String TAG = "BlindAssistantService";
    private static final int SERVICE_NOTIFICATION_ID = 0xdeadbeef;

    public static final String ACTION_START_LISTEN = "blind_assist_svc://action_listen";
    public static final String ACTION_SAY_TEXT = "blind_assist_svc://action_say_text";
    public static final String EXTRA_SAY_TEXT = "extra_say_text";

    private BlindAssistant mAssistant;
    private RecognitionListener recognitionListener;
    private BluetoothSpeechRecognizer mRecognizer;

    private final IBinder binder = new BlindAssistantBinder();
    private BluetoothHeadsetHelper bluetoothHelper;

    public void setRecognitionListener(RecognitionListener recognitionListener) {
        this.recognitionListener = recognitionListener;
    }

    /**
     * Class for clients to access.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with
     * IPC.
     */
    public class BlindAssistantBinder extends Binder {
        public BlindAssistantService getService() {
            return BlindAssistantService.this;
        }
    }

    @Override
    public void onCreate() {
        this.bluetoothHelper = new BluetoothHeadsetHelper(this);
        this.bluetoothHelper.start();

        // The code needs to intercept the Bluetooth recognition to provide appropriate
        // feedback
        this.mRecognizer = new BluetoothSpeechRecognizer(this, this.bluetoothHelper);
        this.mRecognizer.setRecognitionListener(new ServiceRecognitionListener());

        this.mAssistant = new BlindAssistant(this, this.bluetoothHelper);

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }

    public void stopListening() {
        mRecognizer.stop();
    }

    public void startListening() {
        mRecognizer.listen();
    }

    public void say(String msg) {
        this.mAssistant.say(msg);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);

        if (ACTION_START_LISTEN.equals(intent.getAction())) {
            startListening();
        } else if (ACTION_SAY_TEXT.equals(intent.getAction())) {
            say("this feature is not implemented");
        }

        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        stopForeground(true);

        mRecognizer.shutdown();
        mAssistant.shutdown();

        // Stop the bluetooth helper!
        bluetoothHelper.stop();
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

        startForeground(SERVICE_NOTIFICATION_ID, notification);
    }

    public void shutdown() {
        this.stopSelf();
    }

    private void sayStatus(SpeechRecognizerUtil.VoiceStatus status, Object parameter) {
        switch (status) {
            case ERROR:
                int err = ((Integer) parameter).intValue();
                String errorDescription = SpeechRecognizerUtil.describeError(this, err);

                say(String.format("Your request failed because %s", errorDescription));

                break;
        }
    }


    /**
     * A recognition listener which gives voice output for errors and parses
     * results, everything else is passed up to the any other listeners.
     */
    private class ServiceRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            if (recognitionListener != null) {
                recognitionListener.onReadyForSpeech(bundle);
            }
        }

        @Override
        public void onBeginningOfSpeech() {
            if (recognitionListener != null) {
                recognitionListener.onBeginningOfSpeech();
            }
        }

        @Override
        public void onRmsChanged(float v) {
            if (recognitionListener != null) {
                recognitionListener.onRmsChanged(v);
            }
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            if (recognitionListener != null) {
                recognitionListener.onBufferReceived(bytes);
            }
        }

        @Override
        public void onEndOfSpeech() {
            if (recognitionListener != null) {
                recognitionListener.onEndOfSpeech();
            }
        }

        @Override
        public void onError(int i) {
            sayStatus(SpeechRecognizerUtil.VoiceStatus.ERROR, i);

            if (recognitionListener != null) {
                recognitionListener.onError(i);
            }
        }

        @Override
        public void onResults(Bundle bundle) {
            ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            mAssistant.assist(results.get(0));

            if (recognitionListener != null) {
                recognitionListener.onResults(bundle);
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            if (recognitionListener != null) {
                recognitionListener.onPartialResults(bundle);
            }
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            if (recognitionListener != null) {
                recognitionListener.onEvent(i, bundle);
            }
        }
    }
}
