package nz.ac.waikato.ssc10.BlindAssistant;

import nz.ac.waikato.ssc10.text.PlaceholderMapStringTemplate;
import org.javatuples.Pair;

import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: Simon
 * Date: 3/08/13
 * Time: 5:14 PM
 * To change this template use File | Settings | File Templates.
 */
public class VoiceMethodMap extends AbstractMap<PlaceholderMapStringTemplate, Method> {
    private Map<PlaceholderMapStringTemplate, Method> map;

    public VoiceMethodMap() {
        this.map = new HashMap<PlaceholderMapStringTemplate, Method>();
    }

    public VoiceMethodMap(Map<PlaceholderMapStringTemplate, Method> map) {
        this.map = map;
    }

    @Override
    public Set<Entry<PlaceholderMapStringTemplate, Method>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Method put(PlaceholderMapStringTemplate key, Method value) {
        return map.put(key, value);
    }

    @Override
    public Method remove(Object key) {
        return map.remove(key);
    }

    public Pair<Method, Map<String, String>> get(String concreteKey) {
        for (Entry<PlaceholderMapStringTemplate, Method> entry : map.entrySet()) {
            Map<String, String> args = entry.getKey().variables(concreteKey);

            if (args != null) {
                return new Pair<Method, Map<String, String>>(entry.getValue(), args);
            }
        }

        return null;
    }
}
