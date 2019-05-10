package net.frozenchaos.TirNaNog.automation.triggers;

import net.frozenchaos.TirNaNog.capabilities.parameters.IntegerParameter;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "trigger_value_range")
@DiscriminatorValue(value = "valueRange")
public class ValueRangeTrigger extends Trigger {
    @Column(name = "minimum", nullable = false)
    private int minimumValue = -1;
    @Column(name = "maximum", nullable = false)
    private int maximumValue = -1;

    @Override
    public boolean isTriggered(Parameter parameter) {
        if(parameter instanceof IntegerParameter && this.getParameterQualifier().equals(parameter.getName()) && parameter.getValue() != null) {
            return (int) parameter.getValue() >= minimumValue && (int) parameter.getValue() <= maximumValue;
        }
        return false;
    }

    public int getMinimumValue() {
        return minimumValue;
    }

    public void setMinimumValue(int minimumValue) {
        this.minimumValue = minimumValue;
    }

    public int getMaximumValue() {
        return maximumValue;
    }

    public void setMaximumValue(int maximumValue) {
        this.maximumValue = maximumValue;
    }
}
