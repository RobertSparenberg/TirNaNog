package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;

public abstract class Parameter<T> {
    private String parameterDefinitionPath;

    public Parameter() {
    }

    public Parameter(String parameterDefinitionPath) {
        this.parameterDefinitionPath = parameterDefinitionPath;
    }

    @XmlElement(name = "parameter_definition_path")
    public String getParameterDefinitionPath() {
        return parameterDefinitionPath;
    }

    public void setParameterDefinitionPath(String name) {
        this.parameterDefinitionPath = name;
    }

    public boolean matchesDefinition(ParameterDefinition parameterDefinition) {
        return parameterDefinitionPath.equals(parameterDefinition.getName()) && matchesTypeOfDefinition(parameterDefinition);
    }

    protected abstract boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition);

    public abstract T getValue();
}
