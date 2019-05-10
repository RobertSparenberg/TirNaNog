package net.frozenchaos.TirNaNog.data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Integer")
public class IntegerRecordValue extends RecordValue {
    @Column(name = "value", nullable = false)
    private int value;

    public IntegerRecordValue() {
        super();
    }

    public IntegerRecordValue(Integer value) {
        super();
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
