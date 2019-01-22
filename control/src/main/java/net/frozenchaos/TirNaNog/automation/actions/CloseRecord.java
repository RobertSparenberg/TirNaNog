package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.Record;

public class CloseRecord extends Action {
    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        Record record = function.getRecord();
        if(record != null) {
            automationControl.getRecordRepository().save(record);
            function.setRecord(null);
        }
    }
}
