package net.frozenchaos.TirNaNog.automation.triggers;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "trigger_exact_value")
@DiscriminatorValue(value = "exactValue")
public class ExactValueTrigger extends Trigger {
    @Basic
    @Column(name = "value", nullable = false)
    private String valueToMatch = "";

    @Override
    public boolean isTriggered(Parameter parameter) {
         return this.getParameterQualifier().equals(parameter.getName())
                 && valueToMatch.equals(parameter.getValue().toString());
    }

    public String getValueToMatch() {
        return valueToMatch;
    }

    public void setValueToMatch(String valueToMatch) {
        this.valueToMatch = valueToMatch;
    }
}
