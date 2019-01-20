package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;

public class EnumParameter extends Parameter {
    private String value;

    @XmlElement(name = "value")
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    protected boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition) {
        if(parameterDefinition instanceof EnumParameterDefinition) {
            return ((EnumParameterDefinition) parameterDefinition).getValues().contains(value);
        }
        return false;
    }
}
