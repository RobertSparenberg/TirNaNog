package net.frozenchaos.TirNaNog.data;

public class ByteArrayRecordValue extends RecordValue<byte[]> {
    private byte[] value;

    public ByteArrayRecordValue() {
        super();
    }

    public ByteArrayRecordValue(byte[] value) {
        super();
        this.value = value;
    }

    @Override
    public byte[] getValue() {
        return value;
    }

    @Override
    public void setValue(byte[] value) {
        this.value = value;
    }
}
