package nz.ac.waikato.ssc10.util;

import android.location.Location;
import nz.ac.waikato.ssc10.map.LatLng;
import nz.ac.waikato.ssc10.map.navigation.NavigationStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Collection of utility methods
 */
public class MapUtil {
    /**
     * Get a list of navigation points in the area defined by the 'square' with north-east point defined by
     * northEast, and south-west defined by southWest.
     *
     * @param points    The points to filter
     * @param northEast The northwest point of the area
     * @param southWest The southeast point of the area
     * @return A list of filtered NavigationStep's
     */
    public static List<NavigationStep> areaFilter(List<NavigationStep> points, Location northEast, Location southWest) {
        List<NavigationStep> filtered = new ArrayList<NavigationStep>();

        for (NavigationStep pt : points) {
            if (isPointInArea(pt.getEndLocation(), northEast, southWest) || isPointInArea(pt.getStartLocation(), northEast, southWest)) {
                filtered.add(pt);
            }
        }

        return filtered;
    }

    /**
     * States if a point defined by a location is in an area
     *
     * @param location
     * @param northEast
     * @param southWest
     * @return
     */
    public static boolean isPointInArea(Location location, Location northEast, Location southWest) {
        /* Latitude is from north-south (90 - 0 - 90), longitude is from east-west (-180, 180) ... */
        return
                location.getLatitude() <= northEast.getLatitude() && location.getLatitude() >= southWest.getLatitude() &&
                        location.getLongitude() <= northEast.getLongitude() && location.getLongitude() >= southWest.getLongitude();
    }

    public static boolean isPointInArea(LatLng point, LatLng northEast, LatLng southWest) {
        /* Latitude is from north-south (90 - 0 - -90), longitude is from east-west (-180, 180) ... */
        return
                point.getLatitude() <= northEast.getLatitude() && point.getLatitude() >= southWest.getLatitude() &&
                        point.getLongitude() <= northEast.getLongitude() && point.getLongitude() >= southWest.getLongitude();
    }
}
