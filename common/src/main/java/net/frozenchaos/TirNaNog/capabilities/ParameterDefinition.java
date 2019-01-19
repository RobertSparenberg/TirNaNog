package net.frozenchaos.TirNaNog.capabilities;

import javax.xml.bind.annotation.XmlElement;

public abstract class ParameterDefinition {
    private String name;
    private ParameterType parameterType;

    ParameterDefinition(String name, ParameterType parameterType) {
        this.name = name;
        this.parameterType = parameterType;
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ParameterType getParameterType() {
        return parameterType;
    }

    public void setParameterType(ParameterType parameterType) {
        this.parameterType = parameterType;
    }

    public enum ParameterType {
        OUTPUT, INPUT
    }
}
