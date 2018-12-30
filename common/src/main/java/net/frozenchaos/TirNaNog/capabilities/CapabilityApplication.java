package net.frozenchaos.TirNaNog.capabilities;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
public class CapabilityApplication {
    private String name;
    private List<Capability> capabilities = new ArrayList<>();

    public CapabilityApplication() {
    }

    public CapabilityApplication(String name, List<Capability> capabilities) {
        this.name = name;
        this.capabilities = capabilities;
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlElement(name="capabilities")
    public List<Capability> getCapabilities() {
        return capabilities;
    }

    public void setCapabilities(List<Capability> capabilities) {
        this.capabilities.addAll(capabilities);
    }
}
