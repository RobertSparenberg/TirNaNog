package net.frozenchaos.TirNaNog.data;

public class ByteArrayRecordValue extends RecordValue {
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
