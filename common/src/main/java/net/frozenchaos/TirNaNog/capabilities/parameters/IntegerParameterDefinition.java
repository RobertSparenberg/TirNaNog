package net.frozenchaos.TirNaNog.capabilities.parameters;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IntegerParameterDefinition extends ParameterDefinition<Integer> {
    private int rangeMinimum;
    private int rangeMaximum;

    public IntegerParameterDefinition() {
        super("Unknown IntegerParameterDefinition", ParameterType.OUTPUT);
    }

    public IntegerParameterDefinition(String name, ParameterType parameterType, int rangeMinimum, int rangeMaximum) {
        super(name, parameterType);
        this.rangeMinimum = rangeMinimum;
        this.rangeMaximum = rangeMaximum;
    }

    @XmlElement(name="range_minimum")
    public int getRangeMinimum() {
        return rangeMinimum;
    }

    public void setRangeMinimum(int rangeMinimum) {
        this.rangeMinimum = rangeMinimum;
    }

    @XmlElement(name="range_maximum")
    public int getRangeMaximum() {
        return rangeMaximum;
    }

    public void setRangeMaximum(int rangeMaximum) {
        this.rangeMaximum = rangeMaximum;
    }
}
