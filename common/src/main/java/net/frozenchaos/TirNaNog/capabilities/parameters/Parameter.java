package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;

public abstract class Parameter<T> {
    private String name;

    public Parameter() {
    }

    public Parameter(ParameterDefinition<T> definition) {
        this.name = definition.getName();
    }

    @XmlElement(name = "parameter_name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean matchesDefinition(ParameterDefinition parameterDefinition) {
        return name.equals(parameterDefinition.getName()) && matchesTypeOfDefinition(parameterDefinition);
    }

    protected abstract boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition);

    public abstract T getValue();
}
