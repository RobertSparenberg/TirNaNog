package net.frozenchaos.TirNaNog.capabilities;

import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class ParameterDefinition {
    private String name;
    private DataType dataType;
    private int rangeMinimum;
    private int rangeMaximum;
    private int step;
    private List<String> acceptedValues = new ArrayList<>();

    /**
     * Create an INTEGER parameter definition
     * @param name The name of the parameter
     * @param rangeMinimum The minimum value the parameter will accept
     * @param rangeMaximum The maximum value the parameter will accept
     * @param step The value increment between values from the minimum that will be accepted. If the maximum falls outside of this step, it is still accepted.
     */
    public ParameterDefinition(String name, int rangeMinimum, int rangeMaximum, int step) {
        this.name = name;
        this.dataType = DataType.INTEGER;
        this.rangeMinimum = rangeMinimum;
        this.rangeMaximum = rangeMaximum;
        this.step = step;
    }

    /**
     * Create a STRING parameter definition
     * @param name The name of the parameter
     * @param rangeMinimum The minimum number of characters that have to be in the string
     * @param rangeMaximum The maximum number of characters allowed in the string
     */
    public ParameterDefinition(String name, int rangeMinimum, int rangeMaximum) {
        this.name = name;
        this.dataType = DataType.STRING;
        this.rangeMinimum = rangeMinimum;
        this.rangeMaximum = rangeMaximum;
    }

    /**
     * Create an ENUM parameter definition
     * @param name The name of the parameter
     * @param acceptedValues A list of values that is accepted
     */
    public ParameterDefinition(String name, List<String> acceptedValues) {
        this.name = name;
        this.dataType = DataType.ENUM;
        setAcceptedValues(acceptedValues);
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="data_type")
    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
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

    @XmlElement(name="accepted_values")
    public List<String> getAcceptedValues() {
        return acceptedValues;
    }

    public void setAcceptedValues(List<String> acceptedValues) {
        this.acceptedValues.addAll(acceptedValues);
    }
}
