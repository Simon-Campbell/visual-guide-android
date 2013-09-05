package nz.ac.waikato.ssc10.map.interfaces;

import android.location.Location;
import nz.ac.waikato.ssc10.map.NoSuchRouteException;
import nz.ac.waikato.ssc10.map.navigation.NavigationStep;

/**
 * An interface describing directions on a route. Any objects defining an
 * implementation are implicitly ordered according to "step"
 */
public interface WalkingDirections extends StepProvider {
    /**
     * Get the step at the specified step id
     *
     * @param step The step at the specified step id
     * @return A navigation step
     */
    public NavigationStep getStep(int step);

    /**
     * Use the current walking directions to route from the specified location
     *
     * @param location The location to route from
     * @return A walking directions object
     * @throws NoSuchRouteException When no route can be made
     */
    public WalkingDirections routeFrom(Location location) throws NoSuchRouteException;
}
