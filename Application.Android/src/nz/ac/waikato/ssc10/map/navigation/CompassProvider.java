package nz.ac.waikato.ssc10.map.navigation;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * A class which provides compass bearing information
 */
public class CompassProvider implements SensorEventListener {
    private SensorManager mSensorManager;
    private float[] mGravity;
    private float[] mGeomagnetic;

    private boolean hasAzimuth = false;
    private double azimuth = 0.0;

    private CompassChangedListener compassChangedListener = null;

    /**
     * Sets a listener object to listen for compass changed events
     *
     * @param listener The object that will listen to when the compass
     *                 has changed
     */
    public void setCompassChangedListener(CompassChangedListener listener) {
        this.compassChangedListener = listener;
    }

    /**
     * Creates a compass provider from the sensor manager
     *
     * @param sensorManager The sensor manager that will be used to
     *                      provide the compass provider with device
     *                      sensor updates
     */
    public CompassProvider(SensorManager sensorManager) {
        this.mSensorManager = sensorManager;

        this.mSensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        this.mSensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD), SensorManager.SENSOR_DELAY_NORMAL);
    }

    /**
     * Returns if the compass provider has a bearing result
     *
     * @return True if the compass provider has a bearing result, false otherwise
     */
    public boolean hasBearing() {
        return hasAzimuth;
    }

    /**
     * Returns the compass bearing, if it does not exist then it returns 0.0
     *
     * @return The compass bearing, 0.0 if the bearing does not exist
     */
    public double getBearing() {
        return this.azimuth;
    }

    /**
     * Shutdown the compass provider so that it does not provide anymore
     * updates.
     */
    public void shutdown() {
        this.mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        // This code is taken from the following Stack Overflow question about "Android Compass Bearing"
        // and adapted to suit the needs of this application:
        //  http://stackoverflow.com/questions/15155985/android-compass-bearing
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER:
                mGravity = sensorEvent.values;
                break;
            case Sensor.TYPE_MAGNETIC_FIELD:
                mGeomagnetic = sensorEvent.values;
                break;
        }

        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];

            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);

            if (success) {
                double old = this.azimuth;
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);

                this.hasAzimuth = true;
                this.azimuth = Math.toDegrees(orientation[0]);

                if (this.azimuth < 0) {
                    this.azimuth += 360.0;
                }

                if (compassChangedListener != null) {
                    compassChangedListener.onCompassBearingChanged(old, azimuth);
                }
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    public interface CompassChangedListener {
        void onCompassBearingChanged(double oldBearing, double newBearing);
    }

    /**
     * Get the cardinal facing direction shown by the specified bearing,
     * e.g. north, south
     *
     * @param bearing The bearing to get the cardinal direction from
     * @return The cardinal direction (e.g. "north") as a string
     */
    public static String getCardinalFacingDirection(double bearing) {
        String compassDirection;

        if (bearing > 337.5 || bearing <= 22.5)
            compassDirection = "north";
        else if (bearing > 22.5 && bearing <= 67.5)
            compassDirection = "north east";
        else if (bearing > 67.5 && bearing <= 112.5)
            compassDirection = "east";
        else if (bearing > 112.5 && bearing <= 157.5)
            compassDirection = "south east";
        else if (bearing > 157.5 && bearing <= 202.5)
            compassDirection = "south";
        else if (bearing > 202.5 && bearing <= 247.5)
            compassDirection = "south west";
        else if (bearing > 247.5 && bearing <= 292.5)
            compassDirection = "west";
        else
            compassDirection = "north west";

        return compassDirection;
    }
}
