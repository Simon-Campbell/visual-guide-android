package nz.ac.waikato.ssc10.text;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 3/08/13
 * Time: 3:53 PM
 * To change this template use File | Settings | File Templates.
 */
public class StringTemplateComparatorTest {

    @Before
    public void setUp() {
    }

    @Test
    public void testMatches_TwoPlaceholders() throws Exception {
        assertTrue(new PlaceholderStringTemplate("text * why u *").matches("text which is why u 80!"));
    }

    @Test
    public void testMatches_StartTwoPlaceholders() {
        assertTrue(new PlaceholderStringTemplate("** this is stupid").matches(" this is stupid"));
        assertTrue(new PlaceholderStringTemplate("** this is stupid").matches("the lion king this is stupid"));
    }

    @Test
    public void testMatches_ExampleNavigationStrings() {
        assertTrue(new PlaceholderStringTemplate("take me to * near *").matches("take me to Silverdale Road near the Fish and Chip shop"));
        assertTrue(new PlaceholderStringTemplate("take me to * near * on *").matches("take me to the two dollar shop near the Fish and Chip shop on Silverdale Road"));
    }

    @Test
    public void testVariables_TwoPlaceholders() {
        String[] variables = new PlaceholderStringTemplate("text * why u *").variables("text which is why u 80!");

        assertTrue("which is".equals(variables[0]));
        assertTrue("80!".equals(variables[1]));

    }
}
