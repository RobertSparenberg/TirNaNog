package net.frozenchaos.TirNaNog.web.pages;

import javax.persistence.*;

@Entity
@Table(name = "page_item_parameter")
@DiscriminatorValue(value = "parameter")
public class ParameterPageItem extends PageItem {
    @Basic
    @Column(name = "parameter_qualifier", nullable = false)
    private String parameterQualifier = "";

    public ParameterPageItem() {
        super();
    }

    public ParameterPageItem(long id, int width, String parameterQualifier) {
        super(id);
        this.parameterQualifier = parameterQualifier;
    }

    public String getParameterQualifier() {
        return parameterQualifier;
    }

    public void setParameterQualifier(String parameterQualifier) {
        this.parameterQualifier = parameterQualifier;
    }
}
