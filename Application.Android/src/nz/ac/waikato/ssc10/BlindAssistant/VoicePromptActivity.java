package nz.ac.waikato.ssc10.BlindAssistant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import nz.ac.waikato.ssc10.map.GoogleWalkingDirections;
import nz.ac.waikato.ssc10.map.WalkingDirections;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 9/07/13
 * Time: 1:57 PM
 * To change this template use File | Settings | File Templates.
 */
public class VoicePromptActivity extends Activity {
    private static final String TAG = "VoicePromptActivity";

    private BlindAssistant mAssistant;
    private SpeechRecognizer mRecognizer;
    private TextView mStatus;
    private Button mButton;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_prompt);

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                WalkingDirections route = new GoogleWalkingDirections("Hamilton, New Zealand", "Te Awamutu, New Zealand");
//            }
//        });
//
//        //
//        t.start();

        mButton = (Button) findViewById(R.id.request_assist_button);
        mStatus = (TextView) findViewById(R.id.textview_status);

        mRecognizer = SpeechRecognizer.createSpeechRecognizer(this);
        mAssistant = new BlindAssistant(this);

        mButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startListening();

                        return true;

                    case MotionEvent.ACTION_UP:
                        //stopListening();
                        //showStatus(VoiceStatus.RESULTS , "");

                        return false;
                }

                return false;
            }
        });

        mRecognizer.setRecognitionListener(new VoiceRecognitionListener());
    }

    private void startListening() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, VoicePromptActivity.this.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        mRecognizer.startListening(intent);
    }

    private void stopListening() {
        mRecognizer.stopListening();
    }

    protected void onDestroy() {
        super.onDestroy();

        mRecognizer.cancel();
        mRecognizer.destroy();

        mAssistant.shutdown();
    }

    private enum VoiceStatus {
        READY,
        BEGINNING,
        FINISHED,
        ERROR,
        RESULTS
    }

    private void showStatus(VoiceStatus status) {
        showStatus(status, null);
    }

    private void showStatus(VoiceStatus status, Object parameter) {
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

                String errorName = getErrorName(err);
                String errorDescription = describeVoiceError(err);

                statusMsg = String.format(
                        getText(R.string.voice_status_error).toString(),
                        errorName + ": " + errorDescription,
                        err
                );

                showOnButton = false;

                mAssistant.say("Your request failed because " + errorDescription);
                mRecognizer.cancel();

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

    private String getErrorName(int error) {
        String desc;

        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                desc = "ERROR_AUDIO";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                desc = "ERROR_CLIENT";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                desc = "ERROR_INSUFFICIENT_PERMISSIONS";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                desc = "ERROR_NETWORK";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                desc = "ERROR_NETWORK_TIMEOUT";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                desc = "ERROR_NO_MATCH";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                desc = "ERROR_RECOGNIZER_BUSY";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                desc = "ERROR_SERVER";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                desc = "ERROR_SPEECH_TIMEOUT";
                break;
            default:
                desc = "ERROR_UNKNOWN";
        }

        return desc;
    }

    private String describeVoiceError(int error) {
        int key = R.string.voice_err_unk;

        switch (error) {
            case SpeechRecognizer.ERROR_AUDIO:
                key = R.string.voice_err_audio;
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                key = R.string.voice_err_client;
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                key = R.string.voice_err_insufficient_permissions;
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                key = R.string.voice_err_network;
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                key = R.string.voice_err_network_timeout;
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                key = R.string.voice_err_no_match;
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                key = R.string.voice_err_recognizer_busy;
                break;
            case SpeechRecognizer.ERROR_SERVER:
                key = R.string.voice_err_server;
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                key = R.string.voice_err_speech_timeout;
                break;
            default:
                key = R.string.voice_err_unk;
        }

        return getString(key);
    }

    private CharSequence voiceResultsToSentence(ArrayList<String> results, float[] confidence) {
        final float MINIMUM_CONFIDENCE = 0.70f;
        return results.get(0);
    }

    private class VoiceRecognitionListener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            showStatus(VoiceStatus.READY);
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.d(TAG, "Speech recording has begun");

            showStatus(VoiceStatus.BEGINNING);
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

            showStatus(VoiceStatus.FINISHED);
        }

        @Override
        public void onError(int i) {
            Log.e(TAG, "An error has occured " + i);

            showStatus(VoiceStatus.ERROR, i);
        }

        @Override
        public void onResults(Bundle bundle) {
            Log.d(TAG, "Speech results are now available");

            ArrayList<String> results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            float[] confidence = bundle.getFloatArray(SpeechRecognizer.CONFIDENCE_SCORES);

            String bestResult = voiceResultsToSentence(results, confidence).toString();
            showStatus(VoiceStatus.RESULTS, bestResult);

            mAssistant.assist(bestResult);
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
}