package nz.ac.waikato.ssc10.map;

import android.location.Location;
import nz.ac.waikato.ssc10.navigation.NavigationStep;

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
        List<NavigationStep> steps = super.getSteps();
        List<NavigationStep> aidPoints = getAidPoints();

        return decorateSteps(steps, aidPoints);
    }

    @Override
    public WalkingDirections routeFrom(Location location) throws NoSuchRouteException {
        return super.routeFrom(location);
    }

    private List<NavigationStep> getAidPoints() {
        List<NavigationStep> aidPoints = new ArrayList<NavigationStep>();
        return aidPoints;
    }

    private List<NavigationStep> decorateSteps(List<NavigationStep> corePath, List<NavigationStep> aidPoints) {
        for (NavigationStep coreStep : corePath) {
            List<NavigationStep> aidOnPath = findStepsNearPath(coreStep, aidPoints);
        }

        return corePath;
    }

    private List<NavigationStep> findStepsNearPath(NavigationStep step, List<NavigationStep> aidPoints) {
        List<NavigationStep> nearPath = new ArrayList<NavigationStep>();

        return aidPoints;
    }
}
