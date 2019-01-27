package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

public abstract class Action {
    private int id;

    public abstract void perform(Parameter parameter, Function function, AutomationControl automationControl);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
