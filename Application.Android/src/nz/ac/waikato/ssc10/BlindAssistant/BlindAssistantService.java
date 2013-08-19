package nz.ac.waikato.ssc10.BlindAssistant;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import nz.ac.waikato.ssc10.input.BluetoothHeadsetUtils;
import nz.ac.waikato.ssc10.util.SpeechRecognizerUtil;

import java.util.ArrayList;

/**
 * A service which aids with the usability of the system. It allows for media buttons to be pressed
 * while the app is in the background, it also allows for the navigation service to persist without
 * being killed
 */
public class BlindAssistantService extends Service {
    private static final String TAG = "BlindAssistantService";
    private static final int NOTIFICATION = 0xdeadbeef;

    public static final String ACTION_START_LISTEN = "blind_assist_svc://action_listen";
    public static final String ACTION_SAY_TEXT = "blind_assist_svc://action_say_text";
    public static final String EXTRA_SAY_TEXT = "extra_say_text";

    private BlindAssistant mAssistant;
    private SpeechRecognizer mRecognizer;
    private RecognitionListener mRecognitionListener;
    private BluetoothHelper mBluetoothHelper;

    private final IBinder binder = new BlindAssistantBinder();

    private AudioManager audioManager;
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
        this.audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        this.mAssistant = new BlindAssistant(this);

        this.mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        this.mRecognizer.setRecognitionListener(new ServiceRecognitionListener());

        this.mBluetoothHelper = new BluetoothHelper(this, this);

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
    }

    public void stopListening() {
        mRecognizer.stopListening();
    }

    private Intent createListenIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, BlindAssistantService.this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        return intent;
    }

    public void listen() {
        // If the device does not support bluetooth then just start listening,
        // otherwise we'll wait for the on
        if (!this.mBluetoothHelper.start()) {
            mRecognizer.startListening(createListenIntent());
        }
    }

    public void say(String msg) {
        if (mBluetoothHelper.isOnHeadsetSco()) {
            this.mAssistant.say(msg);
        } else {
            this.mAssistant.sayOnHeadset("headset " + msg);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Received start id " + startId + ": " + intent);

        if (ACTION_START_LISTEN.equals(intent.getAction())) {
            listen();
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

        mBluetoothHelper.stop();

        mRecognizer.cancel();
        mRecognizer.destroy();

        mAssistant.shutdown();
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

        startForeground(NOTIFICATION, notification);
    }

    public void shutdown() {
        this.stopSelf();
    }

    private class ServiceRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            say("i am ready to assist you");

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
            sayStatus(SpeechRecognizerUtil.VoiceStatus.ERROR, i);

            mRecognizer.cancel();

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

    private void sayStatus(SpeechRecognizerUtil.VoiceStatus status, Object parameter) {
        switch (status) {
            case ERROR:
                int err = ((Integer) parameter).intValue();
                String errorDescription = SpeechRecognizerUtil.describeError(this, err);
                mAssistant.say("Your request failed because " + errorDescription);

                break;
        }
    }

    public class BluetoothHelper extends BluetoothHeadsetUtils {
        private Context mContext;
        private BlindAssistantService mService;

        public BluetoothHelper(Context context, BlindAssistantService service) {
            super(context);

            mContext = context;
            mService = service;
        }

        @Override
        public void onScoAudioDisconnected() {
            mService.stopListening();
        }

        @Override
        public void onScoAudioConnected() {
            mRecognizer.startListening(createListenIntent());
        }

        @Override
        public void onHeadsetDisconnected() {

        }

        @Override
        public void onHeadsetConnected() {

        }
    }
}
