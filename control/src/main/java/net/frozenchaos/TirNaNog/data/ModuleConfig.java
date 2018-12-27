package net.frozenchaos.TirNaNog.data;

import net.frozenchaos.TirNaNog.capabilities.CapabilityApplication;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "module_config")
@XmlRootElement
public class ModuleConfig implements Serializable {
    @Id
    @Column(nullable = false, updatable = false)
    private String name;

    @Column(nullable = false, updatable = true)
    private String ip;

    @Column(nullable = false, updatable = true)
    private long lastMessageTimestamp;

    @Column(nullable = false, updatable = true)
    private boolean hardwareInterfaceOnly;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "owningModule")
    private List<CapabilityApplication> capabilityApplications = new ArrayList<>();

    public ModuleConfig(String name, String ip, boolean hardwareInterfaceOnly, List<CapabilityApplication> capabilityApplications) {
        this.name = name;
        this.ip = ip;
        this.hardwareInterfaceOnly = hardwareInterfaceOnly;
        this.lastMessageTimestamp = -1;
        this.capabilityApplications.addAll(capabilityApplications);
    }

    /**
     * Default constructor for use by JPA.
     */
    public ModuleConfig() {
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
        this.capabilityApplications.addAll(capabilityApplications);
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
