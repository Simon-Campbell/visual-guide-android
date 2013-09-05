package nz.ac.waikato.ssc10.map;

import nz.ac.waikato.ssc10.map.interfaces.CullableStepProvider;
import nz.ac.waikato.ssc10.map.navigation.NavigationStep;
import nz.ac.waikato.ssc10.map.navigation.PedestrianCrossing;
import nz.ac.waikato.ssc10.util.MapUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class StreamNavigationAssistanceProvider implements CullableStepProvider {
    public static final String PEDESTRIAN_CROSSING_TYPE = "PEDESTRIAN_CROSSING";
    public static final String COMMENT_TOKEN = ";";

    public List<NavigationStep> steps = new ArrayList<NavigationStep>();

    public StreamNavigationAssistanceProvider(InputStream stream) throws IOException {
        BufferedReader br = null;
        String line = null;

        try {
            br = new BufferedReader(new InputStreamReader(stream));

            while ((line = br.readLine()) != null) {
                if (line.startsWith(COMMENT_TOKEN)) {
                    continue;
                }

                String[] fields = line.split(",");

                if (fields[0].equalsIgnoreCase(PEDESTRIAN_CROSSING_TYPE)) {
                    String thoroughFare = fields[1];

                    LatLng start = new LatLng(Double.valueOf(fields[2]), Double.valueOf(fields[3]));
                    LatLng end = new LatLng(Double.valueOf(fields[4]), Double.valueOf(fields[5]));

                    steps.add(new PedestrianCrossing(thoroughFare, start, end));
                }
            }
        } finally {
            if (br != null)
                br.close();
        }
    }

    @Override
    public List<NavigationStep> getSteps() {
        return steps;
    }

    /**
     * Returns the steps where any part of the step falls within the specified latitude
     * boundaries
     *
     * @param northEast The north west latitude boundary
     * @param southWest The south east latitude boundary
     * @return A list of points within the boundaries
     */
    @Override
    public List<NavigationStep> getSteps(LatLng northEast, LatLng southWest) {
        List<NavigationStep> view = new ArrayList<NavigationStep>(steps.size());

        for (NavigationStep s : steps) {
            LatLng start = LatLng.fromLocation(s.getStartLocation());
            LatLng end = LatLng.fromLocation(s.getEndLocation());

            if (MapUtil.isPointInArea(start, northEast, southWest) || MapUtil.isPointInArea(end, northEast, southWest)) {
                view.add(s);
            }
        }

        return view;
    }

}
