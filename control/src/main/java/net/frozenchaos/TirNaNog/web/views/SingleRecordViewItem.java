package net.frozenchaos.TirNaNog.web.views;

public class SingleRecordViewItem extends ViewItem {
    private String recordName;
    private String valuesToDisplay;

    public SingleRecordViewItem() {
        super();
    }

    public SingleRecordViewItem(long id, int width, String recordName, String valuesToDisplay) {
        super(id, width);
        this.recordName = recordName;
        this.valuesToDisplay = valuesToDisplay;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getValuesToDisplay() {
        return valuesToDisplay;
    }

    public void setValuesToDisplay(String valuesToDisplay) {
        this.valuesToDisplay = valuesToDisplay;
    }
}
