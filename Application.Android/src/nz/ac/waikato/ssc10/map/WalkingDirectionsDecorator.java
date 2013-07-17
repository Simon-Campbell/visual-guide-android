package nz.ac.waikato.ssc10.map;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 17/07/13
 * Time: 10:15 PM
 * To change this template use File | Settings | File Templates.
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
}
