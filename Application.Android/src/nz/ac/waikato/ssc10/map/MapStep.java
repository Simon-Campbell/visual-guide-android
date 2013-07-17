package nz.ac.waikato.ssc10.map;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 16/07/13
 * Time: 10:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class MapStep {
    private int distanceValue;
    private int durationValue;

    private LatLng endLocation;
    private LatLng startLocation;

    private String instruction;
    private String travelMode;

    public MapStep(int distanceValue, int durationValue, LatLng endLocation, LatLng startLocation, String instruction, String travelMode) {
        this.distanceValue = distanceValue;
        this.durationValue = durationValue;
        this.endLocation = endLocation;
        this.startLocation = startLocation;
        this.instruction = instruction;
        this.travelMode = travelMode;
    }
}
