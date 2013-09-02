package nz.ac.waikato.ssc10.navigation;

import android.location.Location;
import nz.ac.waikato.ssc10.map.LatLng;

/**
 * A step that involves a pedestrian crossing.
 */
public class PedestrianCrossing implements NavigationStep {
    public static final double DEFAULT_DISTANCE_THRESHOLD = 1.0f;

    private String thoroughFare;

    private Location pt1;
    private Location pt2;

    private double distanceThreshold;

    /**
     * Create a pedestrian crossing object on the following thoroughfare at
     * points pt1 and pt2
     * @param thoroughFare The thoroughfare (e.g. street) that the crossing is
     *                     on
     * @param pt1 One side of the crossing
     * @param pt2 The other side of the crossing
     */
    public PedestrianCrossing(String thoroughFare, LatLng pt1, LatLng pt2) {
        this.thoroughFare = thoroughFare;
        this.distanceThreshold = DEFAULT_DISTANCE_THRESHOLD;
        this.pt1 = pt1.toLocation();
        this.pt2 = pt2.toLocation();
    }

    /**
     * Indicates whether this object is touched by the navigator
     * @param navigator The navigator for the person that you would like to check
     *                  is touching, or not touching this navigation point.
     * @return  True if the pedestrian crossing is touched by a person at the specified
     *          location.
     */
    public boolean touchedBy(IncrementalNavigator navigator) {
        Location l  = navigator.getLocation();
        float[] f   = new float[1];

        Location.distanceBetween(l.getLatitude(), l.getLongitude(), pt1.getLatitude(), pt1.getLongitude(), f);

        if (f[0] < distanceThreshold) {
            return true;
        }

        Location.distanceBetween(l.getLatitude(), l.getLongitude(), pt2.getLatitude(), pt2.getLongitude(), f);

        if (f[0] < distanceThreshold) {
            return true;
        }

        return false;
    }

    @Override
    public String getInstruction() {
        return "walk across the pedestrian crossing";
    }

    @Override
    public NavigationStep getNext() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public NavigationStep getPrevious() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Location getStartLocation() {
        return pt1;
    }

    @Override
    public Location getEndLocation() {
        return pt2;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PedestrianCrossing that = (PedestrianCrossing) o;

        if (!pt2.equals(that.pt2)) return false;
        if (!pt1.equals(that.pt1)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = pt1.hashCode();
        result = 31 * result + pt2.hashCode();
        return result;
    }
}
