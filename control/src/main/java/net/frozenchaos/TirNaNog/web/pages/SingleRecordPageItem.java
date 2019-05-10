package net.frozenchaos.TirNaNog.web.pages;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "page_item_record")
@DiscriminatorValue(value = "record")
public class SingleRecordPageItem extends PageItem {
    @Column(name = "record_name", nullable = false)
    private String recordName = "";
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
