package net.frozenchaos.TirNaNog.automation;

import net.frozenchaos.TirNaNog.automation.actions.Action;
import net.frozenchaos.TirNaNog.automation.triggers.Trigger;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import java.util.LinkedList;
import java.util.List;

public class Rule {
    private int order;
    private List<Trigger> triggers = new LinkedList<>();
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
