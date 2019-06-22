package net.frozenchaos.HardwareInterfacing.configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class SensorPinDefinition extends PinDefinition {
    private int pollFrequency;

    public SensorPinDefinition() {
    }

    public SensorPinDefinition(int pinNumber, PinSignalType pinSignalType, Board board, int pollFrequency) {
        super.setPinNumber(pinNumber);
        super.setPinSignalType(pinSignalType);
        super.setBoard(board);
        this.pollFrequency = pollFrequency;
    }

    @XmlElement
    public int getPollFrequency() {
        return pollFrequency;
    }

    public void setPollFrequency(int pollFrequency) {
        this.pollFrequency = pollFrequency;
    }
}
