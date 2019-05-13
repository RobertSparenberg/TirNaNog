package net.frozenchaos.TirNaNog.automation;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

public interface ParameterListener {
    void onParameter(String parameterQualifier, Parameter parameter);
}
