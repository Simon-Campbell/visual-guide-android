package nz.ac.waikato.ssc10.map.interfaces;

import android.location.Location;
import nz.ac.waikato.ssc10.map.NoSuchRouteException;

/**
 *
 */
public interface WalkingDirections extends StepProvider {
    public WalkingDirections routeFrom(Location location) throws NoSuchRouteException;
}
