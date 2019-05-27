package net.frozenchaos.TirNaNog.web.pages;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "page")
public class Page {
    @Id
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "pageOrder", nullable = false, unique = true)
    private int pageOrder;
    @OneToMany
    private List<PageRow> rows;

    public Page() {
    }

    public Page(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageOrder() {
        return pageOrder;
    }

    public void setPageOrder(int pageOrder) {
        this.pageOrder = pageOrder;
    }

    public List<PageRow> getRows() {
        return rows;
    }

    public void setRows(List<PageRow> rows) {
        this.rows = rows;
    }
}
