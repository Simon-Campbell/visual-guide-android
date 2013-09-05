package nz.ac.waikato.ssc10.map.interfaces;

import android.location.Location;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 4/09/13
 * Time: 9:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface ContextualWalkingDirections extends WalkingDirections {
    /**
     * Tell the location to give additional context when generating
     * the steps
     *
     * @param location A location to give additional context to
     *                 the walking directions
     */
    void tell(Location location);
}
