package net.frozenchaos.TirNaNog.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@Entity
@Table(name = "module_config")
@XmlRootElement
public class ModuleConfig implements Serializable {
    @Id
    @Column(nullable = false, updatable = false)
    @XmlElement
    private String name;

    @Column(nullable = false, updatable = true)
    private String ip;

    @Column(nullable = false, updatable = true)
    private long lastMessageTimestamp;

    @Column(nullable = false, updatable = true)
    @XmlElement
    private boolean isConsumer;

    @Column(nullable = false, updatable = true)
    @XmlElement
    private boolean isProducer;

    @Column(nullable = false, updatable = true)
    @XmlElement
    private boolean hardwareInterfaceOnly;

    public ModuleConfig(String name, String ip, boolean isConsumer, boolean isProducer, boolean hardwareInterfaceOnly) {
        this.name = name;
        this.ip = ip;
        this.isConsumer = isConsumer;
        this.isProducer = isProducer;
        this.hardwareInterfaceOnly = hardwareInterfaceOnly;
        this.lastMessageTimestamp = -1;
    }

    /**
     * Default constructor for use by JPA.
     */
    public ModuleConfig() {
    }

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

    public boolean isConsumer() {
        return isConsumer;
    }

    public void setConsumer(boolean isActuator) {
        this.isConsumer = isActuator;
    }

    public boolean isProducer() {
        return isProducer;
    }

    public void setProducer(boolean isSensor) {
        this.isProducer = isSensor;
    }

    public boolean isHardwareInterfaceOnly() {
        return hardwareInterfaceOnly;
    }

    public void setHardwareInterfaceOnly(boolean isDumb) {
        this.hardwareInterfaceOnly = isDumb;
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
