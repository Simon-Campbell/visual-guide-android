package nz.ac.waikato.ssc10.BlindAssistant;

import nz.ac.waikato.ssc10.text.PlaceholderMapStringTemplate;
import org.javatuples.Pair;

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
public class VoiceMethodMap extends AbstractMap<PlaceholderMapStringTemplate, VoiceMethod> {
    private Map<PlaceholderMapStringTemplate, VoiceMethod> map;

    public VoiceMethodMap() {
        this.map = new HashMap<PlaceholderMapStringTemplate, VoiceMethod>();
    }

    public VoiceMethodMap(Map<PlaceholderMapStringTemplate, VoiceMethod> map) {
        this.map = map;
    }

    @Override
    public Set<Entry<PlaceholderMapStringTemplate, VoiceMethod>> entrySet() {
        return map.entrySet();
    }

    public VoiceMethod put(String key, VoiceMethod value) {
        return map.put(new PlaceholderMapStringTemplate(key), value);
    }

    @Override
    public VoiceMethod put(PlaceholderMapStringTemplate key, VoiceMethod value) {
        return map.put(key, value);
    }

    @Override
    public VoiceMethod remove(Object key) {
        return map.remove(key);
    }

    public Pair<VoiceMethod, Map<String, String>> get(String concreteKey) {
        for (Entry<PlaceholderMapStringTemplate, VoiceMethod> entry : map.entrySet()) {
            Map<String, String> args = entry.getKey().variables(concreteKey);

            if (args != null) {
                return new Pair<VoiceMethod, Map<String, String>>(entry.getValue(), args);
            }
        }

        return null;
    }
}
