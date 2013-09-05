package nz.ac.waikato.ssc10.map.navigation;

import java.util.List;

/**
 * An interface for defining events for objects which
 * listen to navigation guide events.
 */
public interface NavigatorUpdateListener {
    /**
     * This event is called when the guide moves from the recommended path
     *
     * @param guide     The guide that has moves from path
     * @param bearingTo The bearing to the next destination
     */
    void onMoveFromPath(IncrementalNavigator guide, double bearingTo);

    /**
     * Called when a path is updated, this may happen when the user goes to far away from the path or if
     * the user is looking in the wrong direction
     *
     * @param navigator The navigator who has signalled a path update
     * @param newSteps  The new steps that have been generated
     */
    void onPathUpdated(IncrementalNavigator navigator, List<NavigationStep> newSteps);

    /**
     * This event is called when the guide reaches a junction
     *
     * @param guide The guide that has reached a junction point
     *              on the path
     */
    void onNavigationStepChange(IncrementalNavigator guide, NavigationStep navigationStep);
}
