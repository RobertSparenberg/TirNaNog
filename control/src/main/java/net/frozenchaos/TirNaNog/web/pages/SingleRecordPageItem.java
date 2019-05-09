package net.frozenchaos.TirNaNog.web.pages;

import javax.persistence.*;

@Entity
@Table(name = "page_item_record")
@DiscriminatorValue(value = "record")
public class SingleRecordPageItem extends PageItem {
    @Basic
    @Column(name = "record_name", nullable = false)
    private String recordName = "";
    @Basic
    @Column(name = "values_to_display", nullable = false)
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
