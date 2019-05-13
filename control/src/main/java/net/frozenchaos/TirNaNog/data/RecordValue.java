package net.frozenchaos.TirNaNog.data;

import javax.persistence.*;

@Entity
@Table(name = "record_value")
@DiscriminatorColumn(name = "record_type", discriminatorType = DiscriminatorType.STRING)
//@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class RecordValue {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "value_name")
    private String valueName;
    @Column(name = "timestamp", nullable = false)
    private long timestamp = -1;

    protected RecordValue() {
        timestamp = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
