package nz.ac.waikato.ssc10.navigation;

import nz.ac.waikato.ssc10.map.LatLng;

/**
 * A class which describes a navigation step
 */
public class TimedNavigationStep implements NavigationStep {
    private int distanceValue;
    private int durationValue;

    private LatLng endLocation;
    private LatLng startLocation;

    private String instruction;
    private String travelMode;

    private NavigationStep next;
    private NavigationStep previous;

    public TimedNavigationStep(int distanceValue, int durationValue, LatLng endLocation, LatLng startLocation, String instruction, String travelMode) {
        this.distanceValue = distanceValue;
        this.durationValue = durationValue;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
        this.instruction = instruction;
        this.travelMode = travelMode;
    }

    @Override
    public String getInstruction() {
        return (instruction.replaceAll("\\<.*?>",""));
    }

    public void setNext(NavigationStep next) {
        this.next = next;
    }

    public void setPrevious(NavigationStep previous) {
        this.previous = previous;
    }

    @Override
    public NavigationStep getNext() {
        return this.next;
    }

    @Override
    public NavigationStep getPrevious() {
        return this.previous;
    }

    @Override
    public LatLng getStartLocation() {
        return startLocation;
    }

    @Override
    public LatLng getEndLocation() {
        return endLocation;
    }
}
