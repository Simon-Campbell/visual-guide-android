package nz.ac.waikato.ssc10.map;

import nz.ac.waikato.ssc10.BlindAssistant.GpsCoordinate;
import nz.ac.waikato.ssc10.navigation.NavigationStep;

import java.net.URI;

import nz.ac.waikato.ssc10.navigation.PedestrianCrossing;
import org.junit.Test;

import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FileBasedAssistanceProviderTest {
    @Test
    public void testGetSteps() throws Exception {
        final String filePath = "test/resources/assist-locations.txt";

        final FileBasedAssistanceProvider assistanceProvider = new FileBasedAssistanceProvider(filePath);
        final List<NavigationStep> steps = assistanceProvider.getSteps();

        assertTrue("steps cannot be null", steps != null);
        assertTrue("steps should be size 2", steps.size() == 2);

        final NavigationStep step1 = new PedestrianCrossing(new LatLng(100.0, 100.0), new LatLng(200.0, 200.0));
        assertTrue("step should be equal", steps.get(0).equals(step1));
    }

    @Test
    public void testGetSteps_Cull() throws Exception {
        final String filePath = "test/resources/assist-locations.txt";

        /*
                   +lat
            -lng    +   +lng
                   -lat
        */
        final LatLng northWest = new LatLng(30.0, 20.0);
        final LatLng southEast = new LatLng(20.0, 30.0);

        final FileBasedAssistanceProvider assistanceProvider = new FileBasedAssistanceProvider(filePath);
        final List<NavigationStep> steps = assistanceProvider.getSteps(northWest, southEast);

        assertTrue("steps cannot be null", steps != null);
        assertTrue("steps should be size 1", steps.size() == 1);

        final NavigationStep stepTest = new PedestrianCrossing(new LatLng(25.0, 25.0), new LatLng(25.0, 25.0));
        assertTrue("step should be equal", steps.get(0).equals(stepTest));
    }
}
