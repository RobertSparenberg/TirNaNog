package net.frozenchaos.TirNaNog.automation.triggers;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "trigger")
@DiscriminatorColumn(name = "trigger_type")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Trigger {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "parameter_qualifier", nullable = false)
    private String parameterQualifier = "";

    public abstract boolean isTriggered(Parameter parameter);

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getParameterQualifier() {
        return parameterQualifier;
    }

    public void setParameterQualifier(String parameterQualifier) {
        this.parameterQualifier = parameterQualifier;
    }
}
