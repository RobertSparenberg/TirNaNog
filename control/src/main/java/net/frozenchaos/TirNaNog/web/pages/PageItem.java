package net.frozenchaos.TirNaNog.web.pages;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY)
@JsonSubTypes({
        @JsonSubTypes.Type(value = GraphPageItem.class, name = "GraphPageItem"),
        @JsonSubTypes.Type(value = ParameterPageItem.class, name = "ParameterPageItem"),
        @JsonSubTypes.Type(value = SingleRecordPageItem.class, name = "SingleRecordPageItem") }
)
public abstract class PageItem {
    private Long id;

    public PageItem() {
    }

    protected PageItem(long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
