package nz.ac.waikato.ssc10.BlindAssistant;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 10/07/13
 * Time: 11:57 AM
 * To change this template use File | Settings | File Templates.
 */
public class IncrementalPathGuide {
    private int currentIdx = 0;
    private float bearing;

    private Queue<MovementContainer> movementHistory;
    private List<GpsCoordinate> route;

    private NavigationGuideUpdateListener navigationGuideUpdateListener;

    public IncrementalPathGuide(GpsCoordinate start, GpsCoordinate end, float initialBearing) {
    }

    public IncrementalPathGuide(List<GpsCoordinate> route, float initialBearing) {
        this.route = route;
        this.bearing = bearing;

        this.movementHistory = new ArrayBlockingQueue<MovementContainer>(128);
    }

    public String getCurrentInstruction() {
        return "100";
    }

    private GpsCoordinate getLastCheckpoint() {
        return route.get(currentIdx);
    }

    private GpsCoordinate getNextCheckpoint() {
        GpsCoordinate next = null;

        if (currentIdx < route.size()) {
            next = route.get(currentIdx + 1);
        }

        return next;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public void move(GpsCoordinate coordinate) {
        this.move(coordinate, bearing);
    }

    public void move(GpsCoordinate coordinate, float bearing) {
        this.currentIdx = getNextRouteIndex();
        this.movementHistory.add(new MovementContainer(coordinate, bearing));
    }

    private int getNextRouteIndex() {
        return 0;
    }

    private class MovementContainer {
        private final GpsCoordinate coordinate;
        private final double bearing;

        public MovementContainer(GpsCoordinate coordinate, double bearing) {
            this.coordinate = coordinate;
            this.bearing = bearing;
        }

        private double getBearing() {
            return bearing;
        }

        private GpsCoordinate getCoordinate() {
            return coordinate;
        }
    }

    public void setNavigationGuideUpdateListener(NavigationGuideUpdateListener listener) {
        this.navigationGuideUpdateListener = listener;
    }
}
