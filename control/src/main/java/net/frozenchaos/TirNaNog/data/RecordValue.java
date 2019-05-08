package net.frozenchaos.TirNaNog.data;

import javax.persistence.*;

@Entity
@Table(name = "record_value")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "record_type", discriminatorType = DiscriminatorType.STRING)
public abstract class RecordValue {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
