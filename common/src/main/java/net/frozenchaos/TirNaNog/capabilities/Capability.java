package net.frozenchaos.TirNaNog.capabilities;

import java.util.ArrayList;
import java.util.List;

public class Capability {
    private String name;
    private List<ParameterDefinition> inputParameterDefinitions = new ArrayList<>();
    private List<ParameterDefinition> outputParameterDefinitions = new ArrayList<>();

    public Capability(String name, List<ParameterDefinition> inputParameterDefinitions) {
        setName(name);
        setInputParameterDefinitions(inputParameterDefinitions);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ParameterDefinition> getInputParameterDefinitions() {
        return inputParameterDefinitions;
    }

    public void setInputParameterDefinitions(List<ParameterDefinition> inputParameterDefinitions) {
        this.inputParameterDefinitions = inputParameterDefinitions;
    }

    public List<ParameterDefinition> getOutputParameterDefinitions() {
        return outputParameterDefinitions;
    }

    public void setOutputParameterDefinitions(List<ParameterDefinition> outputParameterDefinitions) {
        this.outputParameterDefinitions.addAll(outputParameterDefinitions);
    }
}
