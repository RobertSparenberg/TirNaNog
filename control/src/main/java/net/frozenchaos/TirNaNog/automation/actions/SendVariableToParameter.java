package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "action_parameter_variable")
@DiscriminatorValue(value = "sendVariableToParameter")
public class SendVariableToParameter extends Action {
    @Basic
    @Column(name = "parameter_qualifier", nullable = false)
    private String parameterQualifier = "";
    @Basic
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
