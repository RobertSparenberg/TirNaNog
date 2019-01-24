package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.Record;

public class SetVariableOnRecord extends Action {
    private String name = "Unknown";
    private String variableName = "Unknown";

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        Record record = function.getRecord();
        Object variable = function.getVariable(variableName);
        if(record != null && variable != null) {
            record.setValue(name, variable);
        }
    }
}
