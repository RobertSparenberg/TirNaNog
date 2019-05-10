package net.frozenchaos.TirNaNog.data;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "Bytes")
public class ByteArrayRecordValue extends RecordValue {
    @Column(name = "value", nullable = false)
    private byte[] value;

    public ByteArrayRecordValue() {
        super();
    }

    public ByteArrayRecordValue(byte[] value) {
        super();
        this.value = value;
    }

    public byte[] getValue() {
        return value;
    }

    public void setValue(byte[] value) {
        this.value = value;
    }
}
