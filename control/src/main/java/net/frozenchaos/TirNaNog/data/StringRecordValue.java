package net.frozenchaos.TirNaNog.data;

public class StringRecordValue extends RecordValue {
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
