package net.frozenchaos.TirNaNog.automation.triggers;

public class ExactValueTrigger extends Trigger {
    private String valueToMatch;

    @Override
    public boolean isTriggered() {
        return false;
    }

    public String getValueToMatch() {
        return valueToMatch;
    }

    public void setValueToMatch(String valueToMatch) {
        this.valueToMatch = valueToMatch;
    }
}
