package net.frozenchaos.HardwareInterfacing.configuration;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActuatorPinDefinition extends PinDefinition {
    private PinSignalType pinSignalType;

    public ActuatorPinDefinition() {
    }

    public ActuatorPinDefinition(int pinNumber, Board board, PinSignalType pinSignalType) {
        super.setPinNumber(pinNumber);
        super.setBoard(board);
        this.pinSignalType = pinSignalType;
    }

    public PinSignalType getPinSignalType() {
        return pinSignalType;
    }

    public void setPinSignalType(PinSignalType pinSignalType) {
        this.pinSignalType = pinSignalType;
    }

    @XmlRootElement
    @XmlEnum(String.class)
    public enum PinSignalType {
        DIGITAL, PWM
    }
}
