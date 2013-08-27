package nz.ac.waikato.ssc10.BlindAssistant.activities;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognitionListener;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.common.ConnectionResult;
import nz.ac.waikato.ssc10.BlindAssistant.R;
import nz.ac.waikato.ssc10.BlindAssistant.receivers.MediaButtonIntentReceiver;
import nz.ac.waikato.ssc10.BlindAssistant.services.BlindAssistantService;
import nz.ac.waikato.ssc10.util.SpeechRecognizerUtil;

import java.util.ArrayList;

import static com.google.android.gms.common.GooglePlayServicesUtil.isGooglePlayServicesAvailable;

/**
 * An activity that prompts the user for voice input. This is where commands are
 * input by touching a button on screen.
 */
public class VoicePromptActivity extends Activity {
    private static final String TAG = "VoicePromptActivity";

    private BlindAssistantService blindAssistantService;
    private boolean blindAssistantServiceBounded;

    private int googlePlayServices = ConnectionResult.SERVICE_MISSING;

    private TextView mStatus;
    private Button mButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_prompt);

        AudioManager audioManager = ((AudioManager) getSystemService(AUDIO_SERVICE));
        audioManager.registerMediaButtonEventReceiver(new ComponentName(this, MediaButtonIntentReceiver.class));

        this.doBindService();

        mButton = (Button) findViewById(R.id.request_assist_button);
        mStatus = (TextView) findViewById(R.id.textview_status);

        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (googlePlayServices == ConnectionResult.SUCCESS) {
                            blindAssistantService.startListening();
                        } else {
                            blindAssistantService.say("Google Play Services is not available");
                        }

                        return true;

                    case MotionEvent.ACTION_UP:
                        return false;
                }

                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        googlePlayServices = isGooglePlayServicesAvailable(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        this.blindAssistantService.shutdown();
        this.doUnbindService();
    }


    private void showStatus(SpeechRecognizerUtil.VoiceStatus status) {
        showStatus(status, null);
    }

    private void showStatus(SpeechRecognizerUtil.VoiceStatus status, Object parameter) {
        boolean showOnButton = true;
        CharSequence statusMsg = getText(R.string.voice_status_none);

        switch (status) {
            case READY:
                statusMsg = getText(R.string.voice_status_ready);
                break;
            case BEGINNING:
                statusMsg = getText(R.string.voice_status_start_talk);
                break;
            case FINISHED:
                statusMsg = getText(R.string.voice_status_end_talk);

                break;
            case ERROR:
                int err = ((Integer) parameter).intValue();

                String errorName = SpeechRecognizerUtil.getErrorName(err);
                String errorDescription = SpeechRecognizerUtil.describeError(this, err);

                statusMsg = String.format(
                        getText(R.string.voice_status_error).toString(),
                        errorName + ": " + errorDescription,
                        err
                );

                showOnButton = false;

                break;
            case RESULTS:
                statusMsg = String.format(
                        getText(R.string.voice_status_results).toString(),
                        parameter.toString()
                );

                showOnButton = false;

                break;
        }

        if (showOnButton) {
            Button b = this.mButton;

            b.setEnabled(false);
            b.setText(statusMsg);
        } else {
            mButton.setEnabled(true);
            mButton.setText(getText(R.string.request_assistance));
        }

        this.mStatus.setText(statusMsg);
    }

    private class VoiceRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            showStatus(SpeechRecognizerUtil.VoiceStatus.READY);
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG, "Speech recording has begun");

            showStatus(SpeechRecognizerUtil.VoiceStatus.BEGINNING);
        }

        @Override
        public void onRmsChanged(float v) {
        }

        @Override
        public void onBufferReceived(byte[] bytes) {
        }

        @Override
        public void onEndOfSpeech() {
            Log.d(TAG, "The user has stopped talking");

            showStatus(SpeechRecognizerUtil.VoiceStatus.FINISHED);
        }

        @Override
        public void onError(int i) {
            Log.e(TAG, "An error has occured " + i);

            showStatus(SpeechRecognizerUtil.VoiceStatus.ERROR, i);
        }

        @Override
        public void onResults(Bundle bundle) {
            Log.d(TAG, "Speech results are now available");

            ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            String bestResult = results.get(0);

            showStatus(SpeechRecognizerUtil.VoiceStatus.RESULTS, String.format("%s", bestResult));
        }

        @Override
        public void onPartialResults(Bundle bundle) {
            //To change body of implemented methods use File | Settings | File Templates.
        }

        @Override
        public void onEvent(int i, Bundle bundle) {
            //To change body of implemented methods use File | Settings | File Templates.
        }
    }

    private ServiceConnection serviceConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            blindAssistantService = ((BlindAssistantService.BlindAssistantBinder) service).getService();
            blindAssistantService.setRecognitionListener(new VoiceRecognitionListener());
        }

        public void onServiceDisconnected(ComponentName className) {
            blindAssistantService = null;
        }
    };

    private void doBindService() {
        bindService(new Intent(this, BlindAssistantService.class), serviceConnection, Context.BIND_AUTO_CREATE);

        blindAssistantServiceBounded = true;
    }

    void doUnbindService() {
        if (blindAssistantServiceBounded) {
            // Detach our existing connection.
            unbindService(serviceConnection);
            blindAssistantServiceBounded = false;
        }
    }

}