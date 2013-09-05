package nz.ac.waikato.ssc10.map.interfaces;

import nz.ac.waikato.ssc10.map.navigation.NavigationStep;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 4/09/13
 * Time: 9:08 PM
 * To change this template use File | Settings | File Templates.
 */
public interface StepProvider {
    List<NavigationStep> getSteps();
}
