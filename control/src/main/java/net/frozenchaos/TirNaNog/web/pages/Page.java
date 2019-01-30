package net.frozenchaos.TirNaNog.web.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Page {
    private String name;
    private List<PageRow> rows = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PageRow[] getRows() {
        return (PageRow[]) rows.toArray();
    }

    public void setRows(PageRow rows[]) {
        Collections.addAll(this.rows, rows);
    }
}
