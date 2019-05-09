package net.frozenchaos.TirNaNog.automation;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;
import net.frozenchaos.TirNaNog.data.Record;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "function")
public class Function {
    @Id
    @Column(name = "name", nullable = false)
    private String name = "New Automation Function";
    @Basic
    @Column(name = "active", nullable = false)
    private boolean active = false;

    @OneToMany
    private List<Rule> rules = new ArrayList<>();
    @Transient
    private Map<String, Object> variables = new HashMap<>();
    @OneToMany
    @MapKey(name = "variable_name")
    private Record record = null;

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
