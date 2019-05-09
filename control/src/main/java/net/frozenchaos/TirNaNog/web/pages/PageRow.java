package net.frozenchaos.TirNaNog.web.pages;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "page_row")
public class PageRow {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private long id;
    @OneToMany
    private List<PageItem> pageItems;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<PageItem> getPageItems() {
        return pageItems;
    }

    public void setPageItems(List<PageItem> pageItems) {
        this.pageItems = pageItems;
    }
}
