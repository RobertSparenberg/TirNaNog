package net.frozenchaos.TirNaNog.automation.actions;

import net.frozenchaos.TirNaNog.automation.AutomationControl;
import net.frozenchaos.TirNaNog.automation.Function;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.*;

@Entity
@Table(name = "action")
@DiscriminatorColumn(name = "action_type")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Action {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;

    public abstract void perform(Parameter parameter, Function function, AutomationControl automationControl);

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
