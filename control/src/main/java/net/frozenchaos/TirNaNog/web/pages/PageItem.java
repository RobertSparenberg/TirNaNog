package net.frozenchaos.TirNaNog.web.pages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import javax.persistence.*;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GraphPageItem.class, name = "GraphPageItem"),
        @JsonSubTypes.Type(value = ParameterPageItem.class, name = "ParameterPageItem"),
        @JsonSubTypes.Type(value = SingleRecordPageItem.class, name = "SingleRecordPageItem") }
)
@Entity
@Table(name = "page_item")
@DiscriminatorColumn(name = "page_item_type", discriminatorType = DiscriminatorType.STRING)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PageItem {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;

    public PageItem() {
    }

    protected PageItem(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
