package net.frozenchaos.TirNaNog.data;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;

@Entity
@Table(name = "capabilities")
public class Capability {
    @Id
    @Column(nullable = false, updatable = false)
    @XmlElement
    private final String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_config")
    private final ModuleConfig owningModule;

    @Column(nullable = false, updatable = false)
    private final boolean consumer;

    @Column(nullable = false, updatable = false)
    private final boolean producer;

    public Capability(ModuleConfig owningModule, String name, boolean consumer, boolean producer) {
        this.owningModule = owningModule;
        this.name = name;
        this.consumer = consumer;
        this.producer = producer;
    }

    public boolean isConsumer() {
        return consumer;
    }

    public boolean isProducer() {
        return producer;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Capability)) return false;

        Capability that = (Capability) o;

        if(!name.equals(that.name)) return false;
        if(!owningModule.equals(that.owningModule)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = owningModule.hashCode();
        result = 31*result+name.hashCode();
        return result;
    }
}
