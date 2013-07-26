package nz.ac.waikato.ssc10.navigation;

import nz.ac.waikato.ssc10.BlindAssistant.GpsCoordinate;
import nz.ac.waikato.ssc10.map.LatLng;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 20/07/13
 * Time: 6:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class PedestrianCrossing implements NavigationStep {
    private LatLng start;
    private LatLng end;

    public PedestrianCrossing(LatLng start, LatLng end) {
        this.start = start;
        this.end = end;
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
    public LatLng getStartLocation() {
        return start;
    }

    @Override
    public LatLng getEndLocation() {
        return end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PedestrianCrossing that = (PedestrianCrossing) o;

        if (!end.equals(that.end)) return false;
        if (!start.equals(that.start)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = start.hashCode();
        result = 31 * result + end.hashCode();
        return result;
    }
}
