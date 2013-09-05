package nz.ac.waikato.ssc10.map.navigation;

import android.location.Location;

/**
 *
 */
public interface NavigationStep {
    /**
     * Get the instruction for the map step (based on the previous instruction)
     *
     * @return The instruction for the map step
     */
    String getInstruction();

    /**
     * Get the location that this step will start on
     *
     * @return A LatLng object for the start of the step
     */
    Location getStartLocation();

    /**
     * Get the location that this step will end on
     *
     * @return A LatLng object for the end of the step
     */
    Location getEndLocation();
}
