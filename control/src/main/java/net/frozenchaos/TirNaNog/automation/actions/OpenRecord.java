package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.Record;

public class OpenRecord extends Action {
    private String name = "Unnamed Record";

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        function.setRecord(new Record());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
