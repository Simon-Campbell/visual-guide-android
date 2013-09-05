package nz.ac.waikato.ssc10.map.navigation;

import android.location.Location;
import nz.ac.waikato.ssc10.map.NoSuchRouteException;
import nz.ac.waikato.ssc10.map.interfaces.WalkingDirections;

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

    public NavigationStep getStep(int step) {
        return directions.getStep(step);
    }

    @Override
    public WalkingDirections routeFrom(Location l) throws NoSuchRouteException {
        return directions.routeFrom(l);
    }
}
