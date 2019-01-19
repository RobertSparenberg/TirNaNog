package net.frozenchaos.TirNaNog.capabilities;

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

    public List<ParameterDefinition> getParameterDefinitions() {
        return parameterDefinitions;
    }

    public void setParameterDefinitions(List<ParameterDefinition> parameterDefinitions) {
        this.parameterDefinitions.addAll(parameterDefinitions);
    }
}
