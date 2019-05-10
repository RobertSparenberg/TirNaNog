package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "action_set_variable_Value")
@DiscriminatorValue(value = "setVariableValue")
public class SetVariableToValue extends Action {
    @Column(name = "variable_name", nullable = false)
    private String variableName = "";
    @Column(name = "value", nullable = false)
    private String value;

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        function.setVariable(variableName, value);
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
