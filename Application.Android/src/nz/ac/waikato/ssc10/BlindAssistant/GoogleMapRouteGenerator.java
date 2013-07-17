package nz.ac.waikato.ssc10.BlindAssistant;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which generates a route for users
 */
public class GoogleMapRouteGenerator {
    private GpsCoordinate endPoint;

    /**
     * Construct a route for the user from their current position,
     * to the specified endPoint
     *
     * @param endPoint The end point of the route
     */
    public GoogleMapRouteGenerator(GpsCoordinate endPoint) {
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
    public static GoogleMapRouteGenerator fromName(String name) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
