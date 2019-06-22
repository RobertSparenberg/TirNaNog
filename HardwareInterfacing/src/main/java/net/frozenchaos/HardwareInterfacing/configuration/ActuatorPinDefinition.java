package net.frozenchaos.HardwareInterfacing.configuration;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ActuatorPinDefinition extends PinDefinition {
    public ActuatorPinDefinition() {
    }

    public ActuatorPinDefinition(int pinNumber, PinSignalType pinSignalType, Board board) {
        super.setPinNumber(pinNumber);
        super.setPinSignalType(pinSignalType);
        super.setBoard(board);
    }
}
