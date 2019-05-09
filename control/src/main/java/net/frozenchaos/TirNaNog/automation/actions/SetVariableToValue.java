package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "action_set_variable_Value")
@DiscriminatorValue(value = "setVariableValue")
public class SetVariableToValue extends Action {
    @Basic
    @Column(name = "variable_name", nullable = false)
    private String variableName = "";
    @Basic
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
