package nz.ac.waikato.ssc10.BlindAssistant;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;

import java.util.ArrayList;

/**
 * A service which aids with the usability of the system. It allows for media buttons to be pressed
 * while the app is in the background, it also allows for the navigation service to persist without
 * being killed
 */
public class BlindAssistantService extends Service {
    private static final String TAG = "BlindAssistantService";
    private static final int NOTIFICATION = 0xdeadbeef;

    private BlindAssistant mAssistant;
    private SpeechRecognizer mRecognizer;
    private RecognitionListener mRecognitionListener;

    private final IBinder binder = new BlindAssistantBinder();
    private NotificationManager notificationManager;

    public void setRecognitionListener(RecognitionListener recognitionListener) {
        this.mRecognitionListener = recognitionListener;
    }

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
        this.mAssistant = new BlindAssistant(this);

        this.mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        this.mRecognizer.setRecognitionListener(new ServiceRecognitionListener());

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }

    public void listen() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, BlindAssistantService.this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        mRecognizer.startListening(intent);
    }

    public void say(String msg) {
        this.mAssistant.say(msg);
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

    public void shutdown() {
        mRecognizer.cancel();
        mRecognizer.destroy();

        mAssistant.shutdown();
    }

    private class ServiceRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            if (mRecognitionListener != null) {
                mRecognitionListener.onReadyForSpeech(bundle);
            }
        }

        @Override
        public void onBeginningOfSpeech() {
            if (mRecognitionListener != null) {
                mRecognitionListener.onBeginningOfSpeech();
            }
        }

        @Override
        public void onRmsChanged(float v) {
            if (mRecognitionListener != null) {
                mRecognitionListener.onRmsChanged(v);
            }
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
            if (mRecognitionListener != null) {
                mRecognitionListener.onBufferReceived(bytes);
            }
        }

        @Override
        public void onEndOfSpeech() {
            if (mRecognitionListener != null) {
                mRecognitionListener.onEndOfSpeech();
            }
        }

        @Override
        public void onError(int i) {
            if (mRecognitionListener != null) {
                mRecognitionListener.onError(i);
            }
        }

        @Override
        public void onResults(Bundle bundle) {
            ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            mAssistant.assist(results.get(0));

            if (mRecognitionListener != null) {
                mRecognitionListener.onResults(bundle);
            }
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            if (mRecognitionListener != null) {
                mRecognitionListener.onPartialResults(bundle);
            }
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            if (mRecognitionListener != null) {
                mRecognitionListener.onEvent(i, bundle);
            }
        }
    }

    private void showStatus(SpeechRecognizerUtil.VoiceStatus status, Object parameter) {
        switch (status) {
            case ERROR:
                int err = ((Integer) parameter).intValue();
                String errorDescription = SpeechRecognizerUtil.describeError(this, err);

                mAssistant.say("Your request failed because " + errorDescription);
                mRecognizer.cancel();

                break;
        }
    }

}
