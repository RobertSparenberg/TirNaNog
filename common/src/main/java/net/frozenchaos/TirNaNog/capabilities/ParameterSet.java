package net.frozenchaos.TirNaNog.capabilities;

import net.frozenchaos.TirNaNog.capabilities.parameters.Parameter;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class ParameterSet {
    private final List<Parameter> parameters = new ArrayList<>();

    public ParameterSet() {
    }

    @XmlElement(name = "parameters")
    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters.addAll(parameters);
    }
}
