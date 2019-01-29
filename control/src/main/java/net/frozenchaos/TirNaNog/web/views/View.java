package net.frozenchaos.TirNaNog.web.views;

import java.util.ArrayList;
import java.util.List;

public class View {
    private List<ViewRow> rows = new ArrayList<>();

    public List<ViewRow> getRows() {
        return rows;
    }

    public void setRows(List<ViewRow> rows) {
        this.rows.addAll(rows);
    }
}
