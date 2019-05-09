package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "action_parameter_value")
@DiscriminatorValue(value = "sendValueToParameter")
public class SendValueToParameter extends Action {
    @Basic
    @Column(name = "parameter_qualifier", nullable = false)
    private String parameterQualifier = "";
    @Basic
    @Column(name = "value", nullable = false)
    private String value = "";

    @Override
    public void perform(Parameter parameter, Function function, AutomationControl automationControl) {
        //todo: send value to parameter impl
    }

    public String getParameterQualifier() {
        return parameterQualifier;
    }

    public void setParameterQualifier(String parameterQualifier) {
        this.parameterQualifier = parameterQualifier;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
