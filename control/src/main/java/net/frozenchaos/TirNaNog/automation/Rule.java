package net.frozenchaos.TirNaNog.automation;

import net.frozenchaos.TirNaNog.automation.actions.Action;
import net.frozenchaos.TirNaNog.automation.triggers.Trigger;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.persistence.*;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name = "function_rule")
public class Rule {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;
    @OneToMany
    private List<Trigger> triggers = new LinkedList<>();
    @OneToMany
    private List<Action> actions = new LinkedList<>();

    public void onParameter(Parameter parameter, Function function, AutomationControl automationControl) {
        for(Trigger trigger : triggers) {
            if(trigger.isTriggered(parameter)) {
                for(Action action : actions) {
                    action.perform(parameter, function, automationControl);
                }
            }
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<Trigger> getTriggers() {
        return triggers;
    }

    public void setTriggers(List<Trigger> triggers) {
        this.triggers = triggers;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }
}
