package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

public class SendValueToParameter extends Action {
    private String parameterPath = "";
    private String value = "";

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
    }

    public String getParameterPath() {
        return parameterPath;
    }

    public void setParameterPath(String parameterPath) {
        this.parameterPath = parameterPath;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
