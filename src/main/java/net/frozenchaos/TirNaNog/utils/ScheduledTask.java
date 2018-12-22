package net.frozenchaos.TirNaNog.utils;

public abstract class ScheduledTask {
    private int initialDelay;
    private int delay;
    private boolean noLongerNeeded = false;

    protected ScheduledTask(int millisecondsBeforeExecution) {
        this.initialDelay = millisecondsBeforeExecution;
        this.delay = this.initialDelay;
    }

    public void setDelay(int millisecondsBeforeExecution) {
        this.initialDelay = millisecondsBeforeExecution;
        this.delay = this.initialDelay;
    }

    public void setNoLongerNeeded() {
        this.noLongerNeeded = true;
    }

    int getDelay() {
        return delay;
    }

    void deductFromDelay(int milliSeconds) {
        this.delay -= milliSeconds;
    }

    boolean isReadyForExecution() {
        return this.delay <= 0;
    }

    boolean isNoLongerNeeded() {
        return this.noLongerNeeded;
    }

    void reset() {
        this.noLongerNeeded = false;
        this.delay = this.initialDelay;
    }

    public abstract void doTask();
}
