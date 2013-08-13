package nz.ac.waikato.ssc10.util;

import android.content.Context;
import android.speech.SpeechRecognizer;
import nz.ac.waikato.ssc10.BlindAssistant.R;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 12/08/13
 * Time: 10:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class SpeechRecognizerUtil {

    /**
     * Gets the name of an error by it's ID
     *
     * @param error The error id
     * @return The name of the error as a string
     */
    public static String getErrorName(int error) {
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

    /**
     * Describes a speech recognizer error in the users
     * locale using the context
     *
     * @param ctx
     * @param error The error code from the speech recognizer
     * @return A string description of the error
     */
    public static String describeError(Context ctx, int error) {
        int key;

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

        return ctx.getString(key);
    }

    /**
     * Simpler description of the voice status as an
     * enumeration
     */
    public enum VoiceStatus {
        READY,
        BEGINNING,
        FINISHED,
        ERROR,
        RESULTS
    }
}
