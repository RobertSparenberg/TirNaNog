package net.frozenchaos.TirNaNog.automation;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.Record;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Function {
    private List<Rule> rules = new ArrayList<>();
    private Map<String, Object> variables = new HashMap<>();
    private Record record = null;

    private String name = "New Automation Function";
    private boolean active = false;

    public void onParameter(String namespace, Parameter parameter, AutomationControl automationControl) {
        for(Rule rule : rules) {
            rule.onParameter(parameter, this, automationControl);
        }
    }

    public List<Rule> getRules() {
        return rules;
    }

    public void setRules(List<Rule> rules) {
        this.rules.addAll(rules);
    }

    public Object getVariable(String name) {
        return variables.get(name);
    }

    public void setVariable(String name, String value) {
        this.variables.put(name, value);
    }

    public Record getRecord() {
        return record;
    }

    public void setRecord(Record record) {
        this.record = record;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
