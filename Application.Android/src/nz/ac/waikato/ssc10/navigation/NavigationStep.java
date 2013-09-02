package nz.ac.waikato.ssc10.navigation;

import android.location.Location;
import nz.ac.waikato.ssc10.map.LatLng;

/**
 *
 */
public interface NavigationStep {
    /**
     * Get the instruction for the map step (based on the previous instruction)
     * @return The instruction for the map step
     */
    String getInstruction();

    /**
     * Get the next navigation step in relation to this step
     * @return
     *  The next step in the navigation, it is null if
     *  it does not exist.
     */
    NavigationStep getNext();

    /**
     * Get the previous navigation step in relation to this step
     * @return
     *  The previous step in the navigation, it is null if
     *  it does not exist.
     */
    NavigationStep getPrevious();

    /**
     * Get the location that this step will start on
     * @return A LatLng object for the start of the step
     */
    Location getStartLocation();

    /**
     * Get the location that this step will end on
     * @return A LatLng object for the end of the step
     */
    Location getEndLocation();
}
