package nz.ac.waikato.ssc10.map;

import android.location.Location;
import nz.ac.waikato.ssc10.map.interfaces.WalkingDirections;
import nz.ac.waikato.ssc10.navigation.NavigationStep;

import java.util.List;

/**
 * A class which decorates the specified walking directions with another
 * collection of points.
 */
public abstract class WalkingDirectionsDecorator implements WalkingDirections {
    private WalkingDirections directions;

    public WalkingDirectionsDecorator(WalkingDirections directions) {
        this.directions = directions;
    }

    @Override
    public List<NavigationStep> getSteps() {
        return directions.getSteps();
    }

    @Override
    public WalkingDirections routeFrom(Location l) throws NoSuchRouteException {
        return directions.routeFrom(l);
    }
}
