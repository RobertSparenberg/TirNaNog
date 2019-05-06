package net.frozenchaos.TirNaNog.web.pages;

public class SingleRecordPageItem extends PageItem {
    private String recordName = "";
    private String valuesToDisplay = "";

    public SingleRecordPageItem() {
        super();
    }

    public SingleRecordPageItem(long id, int width, String recordName, String valuesToDisplay) {
        super(id);
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
