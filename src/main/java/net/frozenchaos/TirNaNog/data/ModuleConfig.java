package net.frozenchaos.TirNaNog.data;

import net.frozenchaos.TirNaNog.Capability;

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
    private List<Capability> capabilities = new ArrayList<>();

    public ModuleConfig(String name, String ip, boolean hardwareInterfaceOnly, List<Capability> capabilities) {
        this.name = name;
        this.ip = ip;
        this.hardwareInterfaceOnly = hardwareInterfaceOnly;
        this.lastMessageTimestamp = -1;
        this.capabilities.addAll(capabilities);
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

    @XmlElement(name="interface_only")
    public boolean isHardwareInterfaceOnly() {
        return hardwareInterfaceOnly;
    }

    public void setHardwareInterfaceOnly(boolean isDumb) {
        this.hardwareInterfaceOnly = isDumb;
    }

    @XmlElement(name="capabilities")
    public List<Capability> getCapabilities() {
        return capabilities;
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
}
