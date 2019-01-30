package net.frozenchaos.TirNaNog.web.pages;

public class GraphPageItem extends PageItem {
    private String recordName;
    private String recordValue;
    private int updateDelay;
    private int numberOfValuesToUse;

    public GraphPageItem() {
        super();
    }

    public GraphPageItem(long id, int width, String recordName, String recordValue, int updateDelay, int numberOfValuesToUse) {
        super(id, width);
        this.recordName = recordName;
        this.recordValue = recordValue;
        this.updateDelay = updateDelay;
        this.numberOfValuesToUse = numberOfValuesToUse;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getRecordValue() {
        return recordValue;
    }

    public void setRecordValue(String recordValue) {
        this.recordValue = recordValue;
    }

    public int getUpdateDelay() {
        return updateDelay;
    }

    public void setUpdateDelay(int updateDelay) {
        this.updateDelay = updateDelay;
    }

    public int getNumberOfValuesToUse() {
        return numberOfValuesToUse;
    }

    public void setNumberOfValuesToUse(int numberOfValuesToUse) {
        this.numberOfValuesToUse = numberOfValuesToUse;
    }
}
