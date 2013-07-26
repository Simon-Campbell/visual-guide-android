package nz.ac.waikato.ssc10.map;

import nz.ac.waikato.ssc10.BlindAssistant.GpsCoordinate;
import nz.ac.waikato.ssc10.navigation.NavigationStep;
import nz.ac.waikato.ssc10.navigation.PedestrianCrossing;
import nz.ac.waikato.ssc10.navigation.TimedNavigationStep;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileBasedAssistanceProvider {
    public static final String PEDESTRIAN_CROSSING_TYPE = "PEDESTRIAN_CROSSING";
    public static final String COMMENT_TOKEN = ";";

    public List<NavigationStep> steps = new ArrayList<NavigationStep>();

    public FileBasedAssistanceProvider(String filePath) throws IOException {
        BufferedReader br = null;
        String line = null;

        try {
            br = new BufferedReader(new FileReader(filePath));

            while ((line = br.readLine()) != null) {
                if (line.startsWith(COMMENT_TOKEN)) {
                    continue;
                }

                String[] fields = line.split(",");

                if (fields[0].equalsIgnoreCase(PEDESTRIAN_CROSSING_TYPE)) {
                    LatLng start = new LatLng(Double.valueOf(fields[1]), Double.valueOf(fields[2]));
                    LatLng end = new LatLng(Double.valueOf(fields[3]), Double.valueOf(fields[4]));

                    steps.add(new PedestrianCrossing(start, end));
                }
            }
        } finally {
            if (br != null)
                br.close();
        }
    }

    public List<NavigationStep> getSteps() {
        return steps;
    }

    /**
     * Returns the steps where any part of the step falls within the specified latitude
     * boundaries
     * @param northWest The north west latitude boundary
     * @param southEast The south east latitude boundary
     * @return A list of points within the boundaries
     */
    public List<NavigationStep> getSteps(LatLng northWest, LatLng southEast) {
        List<NavigationStep> view = new ArrayList<NavigationStep>(steps.size());

        for (NavigationStep s: steps) {
            LatLng start = s.getStartLocation();
            LatLng end = s.getEndLocation();

            if (isPointInRange(start, northWest, southEast) || isPointInRange(end, northWest, southEast)) {
                view.add(s);
            }
        }

        return view;
    }

    private static boolean isPointInRange(LatLng point, LatLng northWest, LatLng southEast) {
        /* Latitude is from north-south (90 - 0 - 90), longitude is from east-west (-180, 180) ... */
        return
                point.getLatitude() <= northWest.getLatitude() && point.getLatitude() >= southEast.getLatitude() &&
                        point.getLongitude() >= northWest.getLongitude() && point.getLongitude() <= southEast.getLongitude();
    }
}
