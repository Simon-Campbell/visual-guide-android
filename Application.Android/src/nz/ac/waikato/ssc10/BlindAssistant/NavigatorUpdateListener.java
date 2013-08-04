package nz.ac.waikato.ssc10.BlindAssistant;

import android.graphics.Point;

/**
 * An interface for defining events for objects which
 * listen to navigation guide events.
 */
public interface NavigatorUpdateListener {
    /**
     * This event is called when the guide leaves the recommended path
     *
     * @param guide The guide that has left the path
     */
    void onPersonLeavePath(IncrementalNavigator guide);

    /**
     * This event is called when the guide reaches a junction
     *
     * @param guide The guide that has reached a junction point
     *              on the path
     */
    void onPersonReachJunction(IncrementalNavigator guide, Point junction);
}
