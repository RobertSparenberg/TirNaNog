package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;

public class IntegerParameter extends Parameter {
    private int value;

    @XmlElement(name = "value")
    public int getValueAsString() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    protected boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition) {
        return parameterDefinition instanceof IntegerParameterDefinition;
    }
}
