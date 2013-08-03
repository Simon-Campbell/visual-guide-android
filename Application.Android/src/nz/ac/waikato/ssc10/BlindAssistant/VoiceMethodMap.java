package nz.ac.waikato.ssc10.BlindAssistant;

import android.util.Pair;
import nz.ac.waikato.ssc10.text.PlaceholderStringTemplate;

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
public class VoiceMethodMap extends AbstractMap<PlaceholderStringTemplate, Method> {
    private Map<PlaceholderStringTemplate, Method> map;

    public VoiceMethodMap() {
        this.map = new HashMap<PlaceholderStringTemplate, Method>();
    }

    public VoiceMethodMap(Map<PlaceholderStringTemplate, Method> map) {
        this.map = map;
    }

    @Override
    public Set<Entry<PlaceholderStringTemplate, Method>> entrySet() {
        return map.entrySet();
    }

    @Override
    public Method put(PlaceholderStringTemplate key, Method value) {
        return map.put(key, value);
    }

    @Override
    public Method remove(Object key) {
        return map.remove(key);
    }

    public Pair<Method, String[]> get(String concreteKey) {
        for (Entry<PlaceholderStringTemplate, Method> entry : map.entrySet()) {
            String[] args = entry.getKey().variables(concreteKey);

            if (args != null) {
                return new Pair<Method, String[]>(entry.getValue(), args);
            }
        }

        return null;
    }
}
