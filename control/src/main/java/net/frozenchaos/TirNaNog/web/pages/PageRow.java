package net.frozenchaos.TirNaNog.web.pages;

public class PageRow {
    private long id;
    PageItem pageItems[];

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public PageItem[] getPageItems() {
        if(pageItems == null) {
            return new PageItem[0];
        }
        return pageItems;
    }

    public void setPageItems(PageItem pageItems[]) {
        this.pageItems = pageItems;
    }
}
