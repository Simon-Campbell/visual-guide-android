package nz.ac.waikato.ssc10.map;

import android.location.Location;
import nz.ac.waikato.ssc10.navigation.NavigationStep;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 17/07/13
 * Time: 10:18 PM
 * To change this template use File | Settings | File Templates.
 */
public interface WalkingDirections {
    public List<NavigationStep> getSteps();
    public WalkingDirections routeFrom(Location location) throws NoSuchRouteException;
}
