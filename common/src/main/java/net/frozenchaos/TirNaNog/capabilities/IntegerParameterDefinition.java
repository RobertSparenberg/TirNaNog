package net.frozenchaos.TirNaNog.capabilities;

import javax.xml.bind.annotation.XmlElement;

public class IntegerParameterDefinition extends ParameterDefinition {
    private int rangeMinimum;
    private int rangeMaximum;
    private int step;

    public IntegerParameterDefinition(String name, int rangeMinimum, int rangeMaximum, int step) {
        super(name);
        this.rangeMinimum = rangeMinimum;
        this.rangeMaximum = rangeMaximum;
        this.step = step;
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

    @XmlElement(name="step")
    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }
}
