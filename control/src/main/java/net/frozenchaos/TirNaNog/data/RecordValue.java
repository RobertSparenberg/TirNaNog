package net.frozenchaos.TirNaNog.data;

public abstract class RecordValue<T> {
    private long timestamp = -1;

    protected RecordValue() {
        timestamp = System.currentTimeMillis();
    }

    public abstract T getValue();

    public abstract void setValue(T value);

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
