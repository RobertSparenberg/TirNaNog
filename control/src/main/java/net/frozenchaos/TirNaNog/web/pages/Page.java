package net.frozenchaos.TirNaNog.web.pages;

import java.util.List;

public class Page {
    private Long id;
    private String name;
    private int order;
    private List<PageRow> rows;

    public Page() {
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public List<PageRow> getRows() {
        return rows;
    }

    public void setRows(List<PageRow> rows) {
        this.rows = rows;
    }
}
