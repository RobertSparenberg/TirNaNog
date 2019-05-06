package net.frozenchaos.TirNaNog.automation.triggers;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

public class ExactValueTrigger extends Trigger {
    private String valueToMatch;

    @Override
    public boolean isTriggered(Parameter parameter) {
         return this.getParameterPath().equals(parameter.getName())
                 && valueToMatch.equals(parameter.getValue().toString());
    }

    public String getValueToMatch() {
        return valueToMatch;
    }

    public void setValueToMatch(String valueToMatch) {
        this.valueToMatch = valueToMatch;
    }
}
