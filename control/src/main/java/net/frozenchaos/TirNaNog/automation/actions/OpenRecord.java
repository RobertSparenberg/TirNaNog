package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.Record;

public class OpenRecord extends Action {
    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        function.setRecord(new Record());
    }
}
