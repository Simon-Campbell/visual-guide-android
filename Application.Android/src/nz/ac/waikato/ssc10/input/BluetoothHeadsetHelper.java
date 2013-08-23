package nz.ac.waikato.ssc10.input;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * A subclass of BluetoothHeadsetUtils which allows for listener
 * classes to listen in on the events.
 */
public class BluetoothHeadsetHelper extends BluetoothHeadsetUtils {
    private List<BluetoothHeadsetListener> mListeners;

    public BluetoothHeadsetHelper(Context context) {
        super(context);

        mListeners = new ArrayList<BluetoothHeadsetListener>();
    }

    /**
     * Add a listener to listen to the events
     *
     * @param listener The listener that will listen to the events
     */
    public void addBluetoothHeadsetListener(BluetoothHeadsetListener listener) {
        mListeners.add(listener);
    }

    /**
     * Remove a listener that is listening to the events
     *
     * @param listener The listener that will stop listening to
     *                 events
     */
    public void removeBluetoothHeadsetListener(BluetoothHeadsetListener listener) {
        mListeners.remove(listener);
    }

    /**
     * An interface to listening to Bluetooth Headset events
     */
    public interface BluetoothHeadsetListener {
        void onScoAudioConnected();

        void onScoAudioDisconnected();

        void onHeadsetDisconnected();

        void onHeadsetConnected();
    }

    ;

    @Override
    public void onScoAudioDisconnected() {
        for (BluetoothHeadsetListener l : mListeners)
            l.onScoAudioDisconnected();

    }

    @Override
    public void onScoAudioConnected() {
        for (BluetoothHeadsetListener l : mListeners)
            l.onScoAudioConnected();
    }

    @Override
    public void onHeadsetDisconnected() {
        for (BluetoothHeadsetListener l : mListeners)
            l.onHeadsetDisconnected();
    }

    @Override
    public void onHeadsetConnected() {
        for (BluetoothHeadsetListener l : mListeners)
            l.onHeadsetConnected();
    }
}
