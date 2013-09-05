package nz.ac.waikato.ssc10.map.interfaces;

import android.location.Location;
import nz.ac.waikato.ssc10.map.navigation.NavigationStep;

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

    /**
     * A listener for how the walking directions has changed based on new
     * contexts
     */
    public interface ContextualWalkingDirectionsListener {
        /**
         * An event which occurs when a step is inserted at a
         * specific index
         *
         * @param stepIdx The stepIndex where the navigation step was inserted,
         *                the new step takes this index.
         * @param newStep The step that was inserted into the list
         */
        void onStepInsertion(int stepIdx, NavigationStep newStep);

        /**
         * An event which occurs when a step is removed at a specific
         * index
         *
         * @param stepIdx     The index where the navigation step was
         *                    removed from,
         * @param removedStep
         */
        void onStepRemoval(int stepIdx, NavigationStep removedStep);
    }
}
