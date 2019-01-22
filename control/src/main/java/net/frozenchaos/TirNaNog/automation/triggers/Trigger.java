package net.frozenchaos.TirNaNog.automation.triggers;

import net.frozenchaos.TirNaNog.automation.Rule;

public abstract class Trigger {
    private Rule rule;
    private String parameterPath;

    public abstract boolean isTriggered();

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    public String getParameterPath() {
        return parameterPath;
    }

    public void setParameterPath(String parameterPath) {
        this.parameterPath = parameterPath;
    }
}
