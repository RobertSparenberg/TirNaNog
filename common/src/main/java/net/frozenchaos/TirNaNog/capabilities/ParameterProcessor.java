package net.frozenchaos.TirNaNog.capabilities;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.capabilities.parameters.ParameterDefinition;

public abstract class ParameterProcessor {
    private final ParameterDefinition parameterDefinition;

    protected ParameterProcessor(ParameterDefinition parameterToProcess) {
        this.parameterDefinition = parameterToProcess;
    }

    public boolean canProcess(Parameter parameter) {
        return parameter.matchesDefinition(parameterDefinition);
    }

    public abstract void processParamter(Parameter parameter);
}
