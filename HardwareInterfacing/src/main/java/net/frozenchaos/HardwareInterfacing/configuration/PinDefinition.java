package net.frozenchaos.HardwareInterfacing.configuration;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlSeeAlso({ActuatorPinDefinition.class, SensorPinDefinition.class })
public abstract class PinDefinition {
    private int pinNumber;
    private Board board;

    @XmlElement
    public int getPinNumber() {
        return pinNumber;
    }

    public void setPinNumber(int pinNumber) {
        this.pinNumber = pinNumber;
    }

    @XmlElement
    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    @XmlRootElement
    @XmlEnum(String.class)
    public enum Board {
        PI, ARDUINO
    }
}
