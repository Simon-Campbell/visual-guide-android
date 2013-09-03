package nz.ac.waikato.ssc10.map;

import android.location.Location;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import nz.ac.waikato.ssc10.navigation.IncrementalNavigator;
import nz.ac.waikato.ssc10.navigation.NavigationStep;
import nz.ac.waikato.ssc10.navigation.PedestrianCrossing;

import java.util.ArrayList;
import java.util.List;

/**
 * A class which modifies a given walking directions object and ensures that
 * accessibility points are used along the route.
 */
public class DisabilityWalkingDirections extends WalkingDirectionsDecorator {
    public DisabilityWalkingDirections(WalkingDirections directions) {
        super(directions);
    }

    @Override
    public List<NavigationStep> getSteps() {
        return decorateSteps(super.getSteps(), getAidPoints());
    }

    @Override
    public WalkingDirections routeFrom(Location location) throws NoSuchRouteException {
        return new DisabilityWalkingDirections(super.routeFrom(location));
    }

    private List<NavigationStep> getAidPoints() {
        List<NavigationStep> aidPoints = new ArrayList<NavigationStep>();

        aidPoints.add(new PedestrianCrossing("Silverdale Rd", new LatLng(-37.790002, 175.325749), new LatLng(-37.789922, 175.32588)));
        aidPoints.add(new PedestrianCrossing("Hillcrest Rd", new LatLng(-37.787851,175.319446), new LatLng(-37.787892,175.319573)));

        return aidPoints;
    }

    private List<NavigationStep> decorateSteps(List<NavigationStep> corePath, List<NavigationStep> aidPoints) {

        for (NavigationStep coreStep : corePath) {
            List<NavigationStep> aidOnPath = findStepsOnPath(coreStep, aidPoints);
        }

        return corePath;
    }

    private List<NavigationStep> findStepsOnPath(NavigationStep step, List<NavigationStep> aidPoints) {
        List<NavigationStep> onPath = new ArrayList<NavigationStep>();

        for (NavigationStep aidStep : aidPoints) {
        }

        return aidPoints;
    }
}
