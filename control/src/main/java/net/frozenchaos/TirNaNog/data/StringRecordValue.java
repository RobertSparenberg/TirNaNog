package net.frozenchaos.TirNaNog.data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "String")
public class StringRecordValue extends RecordValue {
    @Basic
    @Column(name = "value")
    private String value;

    public StringRecordValue() {
        super();
    }

    public StringRecordValue(String value) {
        super();
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
