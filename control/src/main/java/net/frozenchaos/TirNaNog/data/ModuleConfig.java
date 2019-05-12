package net.frozenchaos.TirNaNog.data;

import net.frozenchaos.TirNaNog.capabilities.CapabilityApplication;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
public class ModuleConfig implements Serializable {
    private String name;
    private String ip;
    private long lastMessageTimestamp;
    private boolean hardwareInterfaceOnly;
    private List<CapabilityApplication> capabilityApplications;
    private List<String> subscribedParameters;

    public ModuleConfig(String name, String ip, boolean hardwareInterfaceOnly) {
        this.name = name;
        this.ip = ip;
        this.hardwareInterfaceOnly = hardwareInterfaceOnly;
        this.lastMessageTimestamp = -1;
    }

    @XmlElement(name="name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public long getLastMessageTimestamp() {
        return lastMessageTimestamp;
    }

    public void setLastMessageTimestamp(long lastMessageTimestamp) {
        this.lastMessageTimestamp = lastMessageTimestamp;
    }

    @XmlElement(name="hardware_interface_only")
    public boolean isHardwareInterfaceOnly() {
        return hardwareInterfaceOnly;
    }

    public void setHardwareInterfaceOnly(boolean isDumb) {
        this.hardwareInterfaceOnly = isDumb;
    }

    @XmlElement(name="capability_applications")
    public List<CapabilityApplication> getCapabilityApplications() {
        return capabilityApplications;
    }

    public void setCapabilityApplications(List<CapabilityApplication> capabilityApplications) {
        this.capabilityApplications = capabilityApplications;
    }

    @XmlElement(name="subscribed_parameters")
    public List<String> getSubscribedParameters() {
        return subscribedParameters;
    }

    public void setSubscribedParameters(List<String> subscribedParameters) {
        this.subscribedParameters = subscribedParameters;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof ModuleConfig)) return false;

        ModuleConfig that = (ModuleConfig) o;

        if(!name.equals(that.name)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "ModuleConfig{"+
                "name='"+name+'\''+
                ", ip='"+ip+'\''+
                ", lastMessageTimestamp="+lastMessageTimestamp+
                ", hardwareInterfaceOnly="+hardwareInterfaceOnly+
                '}';
    }
}
