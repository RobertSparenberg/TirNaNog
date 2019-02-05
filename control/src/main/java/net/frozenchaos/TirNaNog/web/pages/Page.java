package net.frozenchaos.TirNaNog.web.pages;

public class Page {
    private String name;
    private int order;
    private PageRow rows[];

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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public PageRow[] getRows() {
        if(rows == null) {
            return new PageRow[0];
        }
        return rows;
    }

    public void setRows(PageRow rows[]) {
        this.rows = rows;
    }
}
