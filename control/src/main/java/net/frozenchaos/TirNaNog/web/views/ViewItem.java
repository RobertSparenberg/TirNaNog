package net.frozenchaos.TirNaNog.web.views;

public abstract class ViewItem {
    private long id;
    private int width;

    public ViewItem() {
    }

    protected ViewItem(long id, int width) {
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
