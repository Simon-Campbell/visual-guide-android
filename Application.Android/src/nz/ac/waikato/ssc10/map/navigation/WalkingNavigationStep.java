package nz.ac.waikato.ssc10.map.navigation;

import android.location.Location;
import nz.ac.waikato.ssc10.map.LatLng;

/**
 * A class which describes a standard walking navigation step from
 * A to B.
 */
public class WalkingNavigationStep implements NavigationStep {
    private int distanceValue;
    private int durationValue;

    private Location endLocation;
    private Location startLocation;

    private String instruction;
    private String travelMode;

    private NavigationStep next;
    private NavigationStep previous;

    public WalkingNavigationStep(int distanceValue, int durationValue, LatLng endLocation, LatLng startLocation, String instruction, String travelMode) {
        this.distanceValue = distanceValue;
        this.durationValue = durationValue;
        this.endLocation = endLocation.toLocation();
        this.startLocation = startLocation.toLocation();
        this.instruction = instruction;
        this.travelMode = travelMode;
    }

    @Override
    public String getInstruction() {
        return (instruction.replaceAll("\\<.*?>", ""));
    }

    public void setNext(NavigationStep next) {
        this.next = next;
    }

    public void setPrevious(NavigationStep previous) {
        this.previous = previous;
    }

    @Override
    public Location getStartLocation() {
        return startLocation;
    }

    @Override
    public Location getEndLocation() {
        return endLocation;
    }
}
