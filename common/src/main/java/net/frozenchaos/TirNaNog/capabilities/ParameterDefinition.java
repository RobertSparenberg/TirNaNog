package net.frozenchaos.TirNaNog.capabilities;

import javax.xml.bind.annotation.XmlElement;

abstract class ParameterDefinition implements InputParameterDefinition, OutputParameterDefinition {
    private String name;

    ParameterDefinition(String name) {
        this.name = name;
    }

    @Override
    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
