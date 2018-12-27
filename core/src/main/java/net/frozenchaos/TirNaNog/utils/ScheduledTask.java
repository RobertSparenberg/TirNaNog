package net.frozenchaos.TirNaNog.utils;

public abstract class ScheduledTask {
    private int initialDelay;
    private int delay;
    private boolean noLongerNeeded = false;

    protected ScheduledTask(int millisecondsBeforeExecution) {
        initialDelay = millisecondsBeforeExecution;
        delay = initialDelay;
    }

    public void setDelay(int millisecondsBeforeExecution) {
        initialDelay = millisecondsBeforeExecution;
        delay = initialDelay;
    }

    public void setNoLongerNeeded() {
        noLongerNeeded = true;
    }

    int getDelay() {
        return delay;
    }

    void deductFromDelay(int milliSeconds) {
        delay -= milliSeconds;
    }

    boolean isReadyForExecution() {
        return delay <= 0;
    }

    boolean isNoLongerNeeded() {
        return noLongerNeeded;
    }

    void reset() {
        noLongerNeeded = false;
        delay = initialDelay;
    }

    public abstract void doTask();
}
