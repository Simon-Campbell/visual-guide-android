package nz.ac.waikato.ssc10.text;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 4/08/13
 * Time: 3:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class PlaceholderMapStringTemplateTest {
    @Test
    public void testMatches_TwoPlaceholders() throws Exception {
        assertTrue(new PlaceholderMapStringTemplate("text {0} why u {1}").matches("text which is why u 80!"));
    }

    @Test
    public void testMatches_StartTwoPlaceholders() {
        assertTrue(new PlaceholderMapStringTemplate("{0}{1} this is stupid").matches(" this is stupid"));
        assertTrue(new PlaceholderMapStringTemplate("{0}{1} this is stupid").matches("the lion king this is stupid"));
    }

    @Test
    public void testMatches_ExampleNavigationStrings() {
        assertTrue(new PlaceholderMapStringTemplate("take me to {Destination} near {Near}").matches("take me to Silverdale Road near the Fish and Chip shop"));
        assertTrue(new PlaceholderMapStringTemplate("take me to {Destination} near {Near} on {Street}").matches("take me to the two dollar shop near the Fish and Chip shop on Silverdale Road"));
    }

    @Test
    public void testVariables_TwoPlaceholders() {
        Map<String, String> variables =
                new PlaceholderMapStringTemplate("text {two} why u {three} more")
                        .variables("text which is why u 80! more");

        assertTrue("which is".equals(variables.get("{two}")));
        assertTrue("80!".equals(variables.get("{three}")));

        new PlaceholderMapStringTemplate("text {two} why u {three}")
                .variables("text which is why u 80!");

        assertTrue("which is".equals(variables.get("{two}")));
        assertTrue("80!".equals(variables.get("{three}")));
    }

    @Test
    public void testVariables_OnePlaceholder() {
        Map<String, String> variables =
                new PlaceholderMapStringTemplate("take me to {Place}")
                        .variables("take me to silverdale road");

        assertTrue(variables.get("{Place}").equals("silverdale road"));
    }

    @Test
    public void test_ConcreteContainsConstPattern() {
        PlaceholderMapStringTemplate t1 = new PlaceholderMapStringTemplate("where am I");

        // Not a match so there should be no variables collection!
        assertTrue(t1.variables("where am I facing") == null);
    }
}
