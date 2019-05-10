package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.Record;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "action_record_open")
@DiscriminatorValue(value = "openRecord")
public class OpenRecord extends Action {
    @Column(name = "record_name")
    private String name = "Unnamed Record";

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        function.setRecord(new Record());
        //todo: records need refinement
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
