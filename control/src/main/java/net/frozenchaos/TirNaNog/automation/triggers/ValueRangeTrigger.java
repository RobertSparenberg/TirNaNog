package net.frozenchaos.TirNaNog.automation.triggers;

import net.frozenchaos.TirNaNog.capabilities.parameters.IntegerParameter;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

public class ValueRangeTrigger extends Trigger {
    private int minimumValue = -1;
    private int maximumValue = -1;

    @Override
    public boolean isTriggered(Parameter parameter) {
        if(parameter instanceof IntegerParameter && this.getParameterPath().equals(parameter.getParameterDefinitionPath()) && parameter.getValue() != null) {
            return (int) parameter.getValue() >= minimumValue && (int) parameter.getValue() <= maximumValue;
        }
        return false;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(int minimumValue) {
        this.minimumValue = minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue;
    }
}
