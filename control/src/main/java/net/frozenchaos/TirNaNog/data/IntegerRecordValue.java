package net.frozenchaos.TirNaNog.data;

public class IntegerRecordValue extends RecordValue<Integer> {
    private Integer value;

    public IntegerRecordValue() {
        super();
    }

    public IntegerRecordValue(Integer value) {
        super();
        this.value = value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public void setValue(Integer value) {
        this.value = value;
    }
}
