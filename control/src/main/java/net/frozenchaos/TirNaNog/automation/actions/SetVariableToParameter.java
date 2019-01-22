package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

public class SetVariableToParameter extends Action {
    private String name;

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        function.setVariable(name, parameter.getValue().toString());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
