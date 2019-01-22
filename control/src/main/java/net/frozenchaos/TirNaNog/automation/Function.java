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

    public void onParameter(Parameter parameter, AutomationControl automationControl) {
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
}
