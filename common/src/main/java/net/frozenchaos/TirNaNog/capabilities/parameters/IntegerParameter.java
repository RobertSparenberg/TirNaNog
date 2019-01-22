package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;

public class IntegerParameter extends Parameter<Integer> {
    private int value;

    public void setValue(int value) {
        this.value = value;
    }

    @XmlElement(name = "value")
    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    protected boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition) {
        return parameterDefinition instanceof IntegerParameterDefinition;
    }
}
