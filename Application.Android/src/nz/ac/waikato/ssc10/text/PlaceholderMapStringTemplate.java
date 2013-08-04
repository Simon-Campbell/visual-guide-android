package nz.ac.waikato.ssc10.text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class which compares template strings to templated and non-templated
 * versions.
 */
public class PlaceholderMapStringTemplate {
    static final Pattern pattern = Pattern.compile("\\{[A-Za-z0-9]+\\}");

    final String[] constants;
    final ArrayList<String> keys;

    final String template;

    public PlaceholderMapStringTemplate(String template) {
        this.template = template;
        this.constants = pattern.split(template);

        // Get the ordered list of keys defined in the curly braces.
        this.keys = getKeys(template);
    }

    private ArrayList<String> getKeys(String template) {
        Matcher m = pattern.matcher(template);
        ArrayList<String> keys = new ArrayList<String>();

        while (m.find()) {
            String s = m.group();

            if (keys.contains(s)) {
                throw new IllegalArgumentException("The template pattern contained the same key more than once.");
            } else {
                keys.add(s);
            }
        }

        return keys;
    }

    public Map<String, String> variables(String concrete) {
        final Map<String, String> keyMap = new HashMap<String, String>(keys.size());

        int lastIndex = 0;
        int literalMatches = 0;

        // For each literal group, we want to check if it exists in 'concrete'
        // in order.
        for (int i = 0; i < constants.length; i++) {
            int idx = concrete.indexOf(constants[i], lastIndex);

            if (idx != -1) {
                if (i != 0) {
                    keyMap.put(keys.get(i - 1), concrete.substring(lastIndex, idx));
                }

                literalMatches++;
            } else {
                return null;
            }

            lastIndex = (idx + constants[i].length());
        }

        // If we have not consumed the whole string there must be
        // pattern at the end. Let's extract it and put it in the
        // key map.
        if (lastIndex != concrete.length()) {
            keyMap.put(keys.get(keys.size() - 1), concrete.substring(lastIndex));
        }

        if ((literalMatches - constants.length) == 0) {
            return keyMap;
        } else {
            return null;
        }
    }

    public boolean matches(String concrete) {
        final String[] literalGroups = pattern.split(template);

        int lastIndex = 0;
        int literalMatches = 0;

        // For each literal group, we want to check if it exists in 'concrete'
        // in order.
        for (String literalGroup : literalGroups) {
            int idx = concrete.indexOf(literalGroup, lastIndex);

            if (idx != -1) {
                literalMatches++;
            } else {
                return false;
            }

            lastIndex = (idx + literalGroup.length());
        }

        return (literalMatches - literalGroups.length) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceholderMapStringTemplate template1 = (PlaceholderMapStringTemplate) o;

        return template.equals(template1.template);
    }

    @Override
    public int hashCode() {
        return template.hashCode();
    }
}
