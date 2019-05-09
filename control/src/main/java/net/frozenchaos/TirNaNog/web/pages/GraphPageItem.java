package net.frozenchaos.TirNaNog.web.pages;

import javax.persistence.*;

@Entity
@Table(name = "page_item_graph")
@DiscriminatorValue(value = "graph")
public class GraphPageItem extends PageItem {
    @Basic
    @Column(name = "record_name", nullable = false)
    private String recordName = "";
    @Basic
    @Column(name = "record_value", nullable = false)
    private String recordValue = "";
    @Basic
    @Column(name = "update_delay", nullable = false)
    private int updateDelay = 0;
    @Basic
    @Column(name = "number_of_values_to_use", nullable = false)
    private int numberOfValuesToUse = 0;

    public GraphPageItem() {
        super();
    }

    public GraphPageItem(long id, int width, String recordName, String recordValue, int updateDelay, int numberOfValuesToUse) {
        super(id);
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
