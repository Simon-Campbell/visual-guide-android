package nz.ac.waikato.ssc10.input;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * A subclass of BluetoothHeadsetUtils which allows for listener
 * classes to listen in on the events.
 */
public class BluetoothHeadsetHelper {
    private List<BluetoothHeadsetListener> listeners;

    private Context context;
    private BluetoothHeadsetUtils bluetoothUtils;

    public BluetoothHeadsetHelper(Context context) {
        this.context = context;

        this.bluetoothUtils = new BluetoothHeadsetHelperHelper(context);
        this.listeners = new ArrayList<BluetoothHeadsetListener>();
    }

    /**
     * Add a listener to listen to the events
     *
     * @param listener The listener that will listen to the events
     */
    public void addBluetoothHeadsetListener(BluetoothHeadsetListener listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener that is listening to the events
     *
     * @param listener The listener that will stop listening to
     *                 events
     */
    public void removeBluetoothHeadsetListener(BluetoothHeadsetListener listener) {
        listeners.remove(listener);
    }

    public void start() {
        bluetoothUtils.start();
    }

    public void stop() {
        bluetoothUtils.stop();
    }

    public boolean isBluetoothAudioConnected() {
        return bluetoothUtils.isOnHeadsetSco();
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

    private class BluetoothHeadsetHelperHelper extends BluetoothHeadsetUtils {
        public BluetoothHeadsetHelperHelper(Context c) {
            super(c);
        }

        @Override
        public void onScoAudioDisconnected() {
            for (BluetoothHeadsetListener l : listeners) {
                l.onScoAudioDisconnected();
            }
        }

        @Override
        public void onScoAudioConnected() {
            for (BluetoothHeadsetListener l : listeners) {
                l.onScoAudioConnected();
            }
        }

        @Override
        public void onHeadsetDisconnected() {
            for (BluetoothHeadsetListener l : listeners) {
                l.onHeadsetDisconnected();
            }
        }

        @Override
        public void onHeadsetConnected() {
            for (BluetoothHeadsetListener l : listeners) {
                l.onHeadsetConnected();
            }
        }
    }
}
