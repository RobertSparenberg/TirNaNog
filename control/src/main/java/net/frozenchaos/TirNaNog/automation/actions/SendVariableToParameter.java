package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "action_parameter_variable")
@DiscriminatorValue(value = "sendVariableToParameter")
public class SendVariableToParameter extends Action {
    @Column(name = "parameter_qualifier", nullable = false)
    private String parameterQualifier = "";
    @Column(name = "variable_name", nullable = false)
    private String variableName = "";

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        //todo: send variable to parameter impl
    }

    public String getParameterQualifier() {
        return parameterQualifier;
    }

    public void setParameterQualifier(String parameterQualifier) {
        this.parameterQualifier = parameterQualifier;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = variableName;
    }
}
