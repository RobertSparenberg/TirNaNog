package net.frozenchaos.TirNaNog.automation;

import net.frozenchaos.TirNaNog.automation.actions.Action;
import net.frozenchaos.TirNaNog.automation.triggers.Trigger;
import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import java.util.ArrayList;
import java.util.List;

public class Rule {
    private String name;
    private List<Trigger> triggers = new ArrayList<>();
    private List<Action> actions = new ArrayList<>();

    public void onParameter(Parameter parameter, Function function, AutomationControl automationControl) {
        for(Trigger trigger : triggers) {
            if(trigger.isTriggered()) {
                for(Action action : actions) {
                    action.perform(parameter, function, automationControl);
                }
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
