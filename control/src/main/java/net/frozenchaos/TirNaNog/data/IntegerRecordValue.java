package net.frozenchaos.TirNaNog.data;

public class IntegerRecordValue extends RecordValue {
    private Integer value;

    public IntegerRecordValue() {
        super();
    }

    public IntegerRecordValue(Integer value) {
        super();
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
