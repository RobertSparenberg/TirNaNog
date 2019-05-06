package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;

@XmlSeeAlso({ByteArrayParameterDefinition.class, EnumParameterDefinition.class, IntegerParameterDefinition.class})
@XmlType
public abstract class ParameterDefinition<T> {
    private String name;
    private ParameterType parameterType;

    public ParameterDefinition(String name, ParameterType parameterType) {
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

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ParameterDefinition)) return false;

        ParameterDefinition that = (ParameterDefinition) o;

        if(name != null ? !name.equals(that.name) : that.name != null) return false;
        if(parameterType != that.parameterType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31*result+(parameterType != null ? parameterType.hashCode() : 0);
        return result;
    }

    public enum ParameterType {
        OUTPUT, INPUT
    }
}
