package net.frozenchaos.TirNaNog.web.pages;

public abstract class PageItem {
    private long id;
    private int width;

    public PageItem() {
    }

    protected PageItem(long id, int width) {
        this.id = id;
        this.width = width;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
