package net.frozenchaos.TirNaNog.data;

public abstract class RecordValue {
    private long timestamp = -1;
    private int id;

    protected RecordValue() {
        timestamp = System.currentTimeMillis();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
