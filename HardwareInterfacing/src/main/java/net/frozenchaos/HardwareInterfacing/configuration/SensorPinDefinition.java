package net.frozenchaos.HardwareInterfacing.configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SensorPinDefinition extends PinDefinition {
    private int pollFrequency;
    private int valueMultiplier;

    public SensorPinDefinition() {
    }

    public SensorPinDefinition(int pinNumber, Board board, int valueMultiplier, int pollFrequency) {
        super.setPinNumber(pinNumber);
        super.setBoard(board);
        this.valueMultiplier = valueMultiplier;
        this.pollFrequency = pollFrequency;
    }

    @XmlElement
    public int getValueMultiplier() {
        return valueMultiplier;
    }

    public void setValueMultiplier(int valueMultiplier) {
        this.valueMultiplier = valueMultiplier;
    }

    @XmlElement
    public int getPollFrequency() {
        return pollFrequency;
    }

    public void setPollFrequency(int pollFrequency) {
        this.pollFrequency = pollFrequency;
    }
}
