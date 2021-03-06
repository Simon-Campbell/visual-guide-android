package nz.ac.waikato.ssc10.map;

import nz.ac.waikato.ssc10.map.navigation.NavigationStep;
import nz.ac.waikato.ssc10.map.navigation.PedestrianCrossing;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class CsvNavigationStepProviderTest {
    final String filePath = "test/resources/assist-locations.txt";

    @Test
    public void testGetSteps() throws Exception {
        final InputStream fileStream = new FileInputStream(filePath);
        final CsvNavigationStepProvider assistanceProvider = new CsvNavigationStepProvider(fileStream);
        final List<NavigationStep> steps = assistanceProvider.getSteps();

        assertTrue("steps cannot be null", steps != null);
        assertTrue("steps should be size 2", steps.size() == 2);

        final NavigationStep step1 = new PedestrianCrossing("Silverdale Rd", new LatLng(-37.790002, 175.325749), new LatLng(-37.789922, 175.32588));
        assertTrue("step should be equal", steps.get(0).equals(step1));
    }

    @Test
    public void testGetSteps_Cull() throws Exception {
        /*
                   +lat
            -lng    +   +lng
                   -lat
        */
        final InputStream fileStream = new FileInputStream(filePath);
        final LatLng northEast = new LatLng(-37.79, 176.0);
        final LatLng southWest = new LatLng(-38.0, 175.0);
        final CsvNavigationStepProvider assistanceProvider = new CsvNavigationStepProvider(fileStream);
        final List<NavigationStep> steps = assistanceProvider.getSteps(northEast, southWest);

        assertTrue("steps cannot be null", steps != null);
        assertTrue("steps should be size 1", steps.size() == 1);

        final NavigationStep stepTest = new PedestrianCrossing("Silverdale Rd", new LatLng(-37.790002, 175.325749), new LatLng(-37.789922, 175.32588));
        assertTrue("step should be equal", steps.get(0).equals(stepTest));
    }
}
