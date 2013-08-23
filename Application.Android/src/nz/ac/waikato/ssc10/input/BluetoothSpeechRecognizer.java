package nz.ac.waikato.ssc10.input;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;

/**
 * A class which deals with a voice input channel from Bluetooth channels if present
 * and any other available channels
 */
public class BluetoothSpeechRecognizer {
    private boolean isRecognizing = false;

    private SpeechRecognizer speechRecognizer;
    private RecognitionListener recognitionListener;
    private BluetoothHeadsetHelper bluetoothHelper;

    private Context context;

    public BluetoothSpeechRecognizer(Context context, BluetoothHeadsetHelper headsetHelper) {
        this.context = context;

        this.bluetoothHelper = headsetHelper;
        this.bluetoothHelper.addBluetoothHeadsetListener(btHeadsetListener);
        this.bluetoothHelper.start();

        this.speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
        this.speechRecognizer.setRecognitionListener(new BluetoothRecognitionListener());
    }

    /**
     * Start listening on the voice input stream
     */
    public void listen() {
        // If the device does not support bluetooth then just start listening,
        // otherwise we'll wait for the on
        speechRecognizer.startListening(createListenIntent());

        isRecognizing = true;
    }

    /**
     * @param listener
     */
    public void setRecognitionListener(RecognitionListener listener) {
        this.recognitionListener = listener;
    }

    /**
     * Stop listening on the voice input stream
     */
    public void stop() {
        speechRecognizer.cancel();

        isRecognizing = false;
    }

    public void shutdown() {
        stop();

        bluetoothHelper.removeBluetoothHeadsetListener(btHeadsetListener);

        speechRecognizer.cancel();
        speechRecognizer.destroy();
    }

    /**
     * Return if this voice input channel is actively
     * recognizing.
     *
     * @return True if this voice input is recognizing, false
     *         otherwise
     */
    public boolean isRecognizing() {
        return isRecognizing;
    }

    private Intent createListenIntent() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 5);

        return intent;
    }

    private BluetoothHeadsetHelper.BluetoothHeadsetListener btHeadsetListener = new BluetoothHeadsetHelper.BluetoothHeadsetListener() {
        @Override
        public void onScoAudioConnected() {
        }

        @Override
        public void onScoAudioDisconnected() {
        }

        @Override
        public void onHeadsetDisconnected() {
        }

        @Override
        public void onHeadsetConnected() {
        }
    };

    private class BluetoothRecognitionListener implements RecognitionListener {

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
            speechRecognizer.cancel();

            if (recognitionListener != null) {
                recognitionListener.onError(i);
            }

            isRecognizing = false;

        }

        @Override
        public void onResults(Bundle bundle) {
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
