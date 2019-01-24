package net.frozenchaos.TirNaNog.data;

import java.util.Map;

public class Record {
    private String name;
    private Map<String, Object> values;

    public void setValue(String name, Object variable) {
        values.put(name, variable);
    }
}
