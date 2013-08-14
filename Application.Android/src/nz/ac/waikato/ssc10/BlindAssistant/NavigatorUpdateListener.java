package nz.ac.waikato.ssc10.BlindAssistant;

import nz.ac.waikato.ssc10.navigation.NavigationStep;

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
     * @param oldSteps  The old steps that have been replaced
     * @param oldStep   The old step that the user was carrying out
     */
    void onPathUpdated(IncrementalNavigator navigator, List<NavigationStep> newSteps, List<NavigationStep> oldSteps, NavigationStep oldStep);

    /**
     * This event is called when the guide reaches a junction
     *
     * @param guide The guide that has reached a junction point
     *              on the path
     */
    void onNavigationStepChange(IncrementalNavigator guide, NavigationStep navigationStep);
}
