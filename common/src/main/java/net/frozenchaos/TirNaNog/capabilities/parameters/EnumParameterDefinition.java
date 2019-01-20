package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EnumParameterDefinition extends ParameterDefinition {
    private Set<String> values = new HashSet<>();

    public EnumParameterDefinition(String name, ParameterType parameterType, String... values) {
        super(name, parameterType);
        Collections.addAll(this.values, values);
    }

    @XmlElement(name="values")
    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values.addAll(values);
    }
}
