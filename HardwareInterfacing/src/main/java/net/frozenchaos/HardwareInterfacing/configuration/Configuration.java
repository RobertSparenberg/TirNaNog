package net.frozenchaos.HardwareInterfacing.configuration;


import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement
public class Configuration {
    private List<PinDefinition> pinDefinitions;

    @XmlElementWrapper(name="pinDefinitions")
    @XmlElement(name = "pinDefinition")
    public List<PinDefinition> getPinDefinitions() {
        return pinDefinitions;
    }

    public void setPinDefinitions(List<PinDefinition> pinDefinitions) {
        this.pinDefinitions = pinDefinitions;
    }
}
