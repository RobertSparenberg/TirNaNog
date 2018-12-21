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
    private String knowsMeByIp;

    @Column(nullable = false, updatable = true)
    @XmlElement
    private boolean isActuator;

    @Column(nullable = false, updatable = true)
    @XmlElement
    private boolean isSensor;

    @Column(nullable = false, updatable = true)
    @XmlElement
    private boolean isDumb;

    public ModuleConfig(String name, String ip, String knowsMeByIp, boolean isActuator, boolean isSensor, boolean isDumb) {
        this.name = name;
        this.ip = ip;
        this.knowsMeByIp = knowsMeByIp;
        this.isActuator = isActuator;
        this.isSensor = isSensor;
        this.isDumb = isDumb;
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

    public String getKnowsMeByIp() {
        return knowsMeByIp;
    }

    public void setKnowsMeByIp(String knowsMeByIp) {
        this.knowsMeByIp = knowsMeByIp;
    }

    public boolean isActuator() {
        return isActuator;
    }

    public void setActuator(boolean isActuator) {
        this.isActuator = isActuator;
    }

    public boolean isSensor() {
        return isSensor;
    }

    public void setSensor(boolean isSensor) {
        this.isSensor = isSensor;
    }

    public boolean isDumb() {
        return isDumb;
    }

    public void setDumb(boolean isDumb) {
        this.isDumb = isDumb;
    }
}
