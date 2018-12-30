package net.frozenchaos.TirNaNog.capabilities;

import javax.xml.bind.annotation.XmlElement;

public abstract class Parameter {
    private String parameterDefinitionName;

    @XmlElement(name = "parameter_definition_name")
    public String getParameterDefinitionName() {
        return parameterDefinitionName;
    }

    public void setParameterDefinitionName(String name) {
        this.parameterDefinitionName = name;
    }
}
