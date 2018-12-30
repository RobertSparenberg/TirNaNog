package net.frozenchaos.TirNaNog.capabilities;

import javax.xml.bind.annotation.XmlElement;

public class IntegerParameter extends Parameter {
    private int value;

    @XmlElement(name = "value")
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
