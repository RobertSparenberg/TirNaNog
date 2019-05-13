package net.frozenchaos.TirNaNog.data;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "record")
public class Record {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "timestamp", nullable = false)
    private long timestamp;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "valueName")
    private Map<String, RecordValue> values;

    public void setValue(String name, Object variable) {
        if(variable instanceof Integer) {
            values.put(name, new IntegerRecordValue((Integer) variable));
        } else if(variable instanceof byte[]) {
            values.put(name, new ByteArrayRecordValue((byte[]) variable));
        } else {
            values.put(name, new StringRecordValue(variable.toString()));
        }
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, RecordValue> getValues() {
        return values;
    }

    public void setValues(Map<String, RecordValue> values) {
        this.values = values;
    }
}
