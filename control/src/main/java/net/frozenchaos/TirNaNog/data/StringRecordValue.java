package net.frozenchaos.TirNaNog.data;

public class StringRecordValue extends RecordValue<String> {
    private String value;

    public StringRecordValue() {
        super();
    }

    public StringRecordValue(String value) {
        super();
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }
}
