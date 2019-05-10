package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "action_set_variable_parameter")
@DiscriminatorValue(value = "setVariableParameter")
public class SetVariableToParameter extends Action {
    @Column(name = "variable_name", nullable = false)
    private String variableName = "";
    @Column(name = "parameter_qualifier", nullable = false)
    private String parameterQualifier = "";

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        function.setVariable(variableName, parameter.getValue().toString());
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    public String getParameterQualifier() {
        return parameterQualifier;
    }

    public void setParameterQualifier(String parameterQualifier) {
        this.parameterQualifier = parameterQualifier;
    }
}
