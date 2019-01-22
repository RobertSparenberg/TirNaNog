package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;

public class EnumParameter extends Parameter<String> {
    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    @XmlElement(name = "value")
    @Override
    public String getValue() {
        return value;
    }

    @Override
    protected boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition) {
        if(parameterDefinition instanceof EnumParameterDefinition) {
            return ((EnumParameterDefinition) parameterDefinition).getValues().contains(value);
        }
        return false;
    }
}
