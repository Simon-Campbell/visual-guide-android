package nz.ac.waikato.ssc10.map;

import android.location.Location;
import nz.ac.waikato.ssc10.map.interfaces.ContextualWalkingDirections;
import nz.ac.waikato.ssc10.map.interfaces.WalkingDirections;
import nz.ac.waikato.ssc10.navigation.NavigationStep;
import nz.ac.waikato.ssc10.navigation.PedestrianCrossing;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/**
 * A class which modifies a given walking directions object and ensures that
 * accessibility points are used along the route.
 */
public class DisabilityWalkingDirections extends WalkingDirectionsDecorator implements ContextualWalkingDirections {
    private static int MAX_QUEUE_SIZE = 16;

    private Queue<Location> locations;
    private Location location;

    public DisabilityWalkingDirections(WalkingDirections directions) {
        super(directions);

        location = null;
        locations = new ArrayDeque<Location>(MAX_QUEUE_SIZE);
    }

    /**
     * Tell the location to give additional context when generating
     * the steps
     *
     * @param location A location to give additional context to
     *                 the walking directions
     */
    @Override
    public void tell(Location location) {
        this.location = location;

        while (locations.size() >= MAX_QUEUE_SIZE) {
            locations.remove();
        }

        locations.add(location);
    }

    @Override
    public List<NavigationStep> getSteps() {
        List<NavigationStep> steps;

        if (location != null) {
            steps = decorateSteps(super.getSteps(), getAidPoints());
        } else {
            steps = super.getSteps();
        }

        return steps;
    }

    @Override
    public WalkingDirections routeFrom(Location location) throws NoSuchRouteException {
        return new DisabilityWalkingDirections(super.routeFrom(location));
    }

    private static List<NavigationStep> getAidPoints() {
        List<NavigationStep> aidPoints = new ArrayList<NavigationStep>();

        aidPoints.add(new PedestrianCrossing("Silverdale Rd", new LatLng(-37.790002, 175.325749), new LatLng(-37.789922, 175.32588)));
        aidPoints.add(new PedestrianCrossing("Hillcrest Rd", new LatLng(-37.787851, 175.319446), new LatLng(-37.787892, 175.319573)));

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
