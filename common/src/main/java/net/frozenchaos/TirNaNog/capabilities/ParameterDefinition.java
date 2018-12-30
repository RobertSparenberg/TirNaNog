package net.frozenchaos.TirNaNog.capabilities;

import javax.xml.bind.annotation.XmlElement;

public abstract class ParameterDefinition {
    private String name;

    ParameterDefinition(String name) {
        this.name = name;
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
