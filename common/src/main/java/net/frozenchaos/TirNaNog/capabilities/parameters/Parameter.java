package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;

public abstract class Parameter<T> {
    private String name;
    private String qualifier;
    private long timestamp;

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

    @XmlElement(name = "qualifier")
    public String getQualifier() {
        return qualifier;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public void setQualifier(String qualifier) {
        this.qualifier = qualifier;
    }

    public boolean matchesDefinition(ParameterDefinition parameterDefinition) {
        return name.equals(parameterDefinition.getName()) && matchesTypeOfDefinition(parameterDefinition);
    }

    protected abstract boolean matchesTypeOfDefinition(ParameterDefinition parameterDefinition);

    public abstract T getValue();

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Parameter)) return false;

        Parameter parameter = (Parameter) o;

        if(name != null ? !name.equals(parameter.name) : parameter.name != null) return false;
        if(qualifier != null ? !qualifier.equals(parameter.qualifier) : parameter.qualifier != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31*result+(qualifier != null ? qualifier.hashCode() : 0);
        return result;
    }
}
