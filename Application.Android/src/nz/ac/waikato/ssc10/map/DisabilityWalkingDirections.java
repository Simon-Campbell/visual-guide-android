package nz.ac.waikato.ssc10.map;

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

        // TODO: Add functionality so that map route contains disability information

        return steps;
    }
}
