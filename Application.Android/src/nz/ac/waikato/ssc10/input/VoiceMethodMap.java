package nz.ac.waikato.ssc10.input;

import nz.ac.waikato.ssc10.text.PlaceholderMapStringTemplate;
import org.javatuples.Pair;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A map which contains maps string templates to voice methods, it also provides
 * helper methods for building a voice method map.
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

    /**
     * Put an entry in the map by creating a PlaceholderMapStringTemplate using
     * the key.
     *
     * @param key   The key to make a PlaceholderMapStringTemplate with, this will be used
     *              to identify entries.
     * @param value The value associated with the key
     * @return The value that was put in
     */
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

    /**
     * Get a pair containing the voice method and the named arguments defined by
     * the key from the concreteKey.
     *
     * @param concreteKey The concrete key which matches against a template
     * @return A Pair containing a voice method and a named argument map
     */
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
