package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@XmlRootElement
public class EnumParameterDefinition extends ParameterDefinition<Enum> {
    private Set<String> values = new HashSet<>();

    public EnumParameterDefinition() {
        super("Unknown EnumParameterDefinition", ParameterType.OUTPUT);
    }

    public EnumParameterDefinition(String name, ParameterType parameterType, String... values) {
        super(name, parameterType);
        Collections.addAll(this.values, values);
    }

    @XmlElementWrapper(name="values")
    @XmlElement(name="value")
    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values.addAll(values);
    }
}
