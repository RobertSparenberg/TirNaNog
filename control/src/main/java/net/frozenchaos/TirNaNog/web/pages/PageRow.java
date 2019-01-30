package net.frozenchaos.TirNaNog.web.pages;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PageRow {
    private long id;
    List<PageItem> pageItems = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PageItem[] getPageItems() {
        return (PageItem[]) pageItems.toArray();
    }

    public void setPageItems(PageItem pageItems[]) {
        Collections.addAll(this.pageItems, pageItems);
    }
}
