package nz.ac.waikato.ssc10.map;

/**
 * A class which describes a navigation step
 */
public class NavigationStep {
    private int distanceValue;
    private int durationValue;

    private LatLng endLocation;
    private LatLng startLocation;

    private String instruction;
    private String travelMode;

    private NavigationStep next;
    private NavigationStep previous;

    public NavigationStep(int distanceValue, int durationValue, LatLng endLocation, LatLng startLocation, String instruction, String travelMode) {
        this.distanceValue = distanceValue;
        this.durationValue = durationValue;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
        this.instruction = instruction;
        this.travelMode = travelMode;
    }

    /**
     * Get the instruction for the map step (based on the previous instruction)
     * @return The instruction for the map step
     */
    public String getInstruction() {
        return (instruction.replaceAll("\\<.*?>",""));
    }

    public void setNext(NavigationStep next) {
        this.next = next;
    }

    public void setPrevious(NavigationStep previous) {
        this.previous = previous;
    }

    public NavigationStep getNext() {
        return this.next;
    }

    public NavigationStep getPrevious() {
        return this.previous;
    }
}
