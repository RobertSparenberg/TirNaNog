package net.frozenchaos.TirNaNog.automation.triggers;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

public abstract class Trigger {
    private long id;
    private String parameterQualifier;

    public abstract boolean isTriggered(Parameter parameter);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParameterQualifier() {
        return parameterQualifier;
    }

    public void setParameterQualifier(String parameterQualifier) {
        this.parameterQualifier = parameterQualifier;
    }
}
