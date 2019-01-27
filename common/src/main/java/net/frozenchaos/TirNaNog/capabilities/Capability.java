package net.frozenchaos.TirNaNog.capabilities;

import net.frozenchaos.TirNaNog.capabilities.parameters.ParameterDefinition;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import java.util.ArrayList;
import java.util.List;

public class Capability {
    private String name;
    private List<ParameterDefinition> parameterDefinitions = new ArrayList<>();

    public Capability(String name, List<ParameterDefinition> parameterDefinitions) {
        setName(name);
        this.parameterDefinitions.addAll(parameterDefinitions);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElementWrapper(name = "parameterDefinitions")
    @XmlElement(name = "parameterDefinition")
    public List<ParameterDefinition> getParameterDefinitions() {
        return parameterDefinitions;
    }

    public void setParameterDefinitions(List<ParameterDefinition> parameterDefinitions) {
        this.parameterDefinitions.addAll(parameterDefinitions);
    }
}
