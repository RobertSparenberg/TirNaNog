package net.frozenchaos.TirNaNog.capabilities.parameters;

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

    public boolean matchesDefinition(ParameterDefinition parameterDefinition) {
        return parameterDefinitionName.equals(parameterDefinition.getName()) && matchesTypeOfDefinition(parameterDefinition);
    }

    protected abstract boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition);
}
