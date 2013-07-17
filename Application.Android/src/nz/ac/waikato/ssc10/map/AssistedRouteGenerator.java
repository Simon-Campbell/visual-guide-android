package nz.ac.waikato.ssc10.map;

import nz.ac.waikato.ssc10.BlindAssistant.GpsCoordinate;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which generates a route for users
 */
public class AssistedRouteGenerator {
    private GpsCoordinate endPoint;

    /**
     * Construct a route for the user from their current position,
     * to the specified endPoint
     *
     * @param endPoint The end point of the route
     */
    public AssistedRouteGenerator(GpsCoordinate endPoint) {
        this.endPoint = endPoint;
    }

    /**
     * Get the directions for a new route from the point specified
     *
     * @param fromPoint The point to start routing from
     * @return A list of steps that the user must follow
     */
    public List<GpsCoordinate> route(GpsCoordinate fromPoint) {
        List<GpsCoordinate> route = new ArrayList<GpsCoordinate>();
        route.add(fromPoint);

        // stuff in between!

        route.add(endPoint);

        return route;
    }

    /**
     * Generate a route from a place name
     *
     * @param name The name of the end point
     * @return A route generator which can route to the
     *         specified place name
     */
    public static AssistedRouteGenerator fromName(String name) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
