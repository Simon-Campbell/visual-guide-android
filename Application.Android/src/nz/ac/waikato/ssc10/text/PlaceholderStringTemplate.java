package nz.ac.waikato.ssc10.text;

import java.util.Comparator;
import java.util.regex.Pattern;

/**
 * A class which compares template strings to templated and non-templated
 * versions.
 */
public class PlaceholderStringTemplate {
    static final Pattern pattern = Pattern.compile("\\*");
    final String template;

    public PlaceholderStringTemplate(String template) {
        this.template = template;
    }

    public String[] variables(String concrete) {
        final String[] literalGroups = pattern.split(template);
        String[] variables = new String[literalGroups.length];

        int lastIndex = 0;
        int literalMatches = 0;

        // For each literal group, we want to check if it exists in 'concrete'
        // in order.
        for (int i = 0; i < literalGroups.length; i++) {
            int idx = concrete.indexOf(literalGroups[i], lastIndex);

            if (idx != -1) {
                literalMatches++;
            } else {
                return null;
            }

            if (i != 0) {
                variables[i - 1] = concrete.substring(lastIndex, idx);
            }

            lastIndex = (idx + literalGroups[i].length());
        }

        variables[literalGroups.length - 1] = concrete.substring(lastIndex);

        if ((literalMatches - literalGroups.length) == 0)       {
            return variables;
        } else {
            return  null;
        }
    }

    public boolean matches(String concrete) {
        final String[] literalGroups = pattern.split(template);

        int lastIndex = 0;
        int literalMatches = 0;

        // For each literal group, we want to check if it exists in 'concrete'
        // in order.
        for (int i = 0; i < literalGroups.length; i++) {
            int idx = concrete.indexOf(literalGroups[i], lastIndex);

            if (idx != -1) {
                literalMatches++;
            } else {
                return false;
            }

            lastIndex = (idx + literalGroups[i].length());
        }

        return (literalMatches - literalGroups.length) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PlaceholderStringTemplate template1 = (PlaceholderStringTemplate) o;

        if (!template.equals(template1.template)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return template.hashCode();
    }
}
