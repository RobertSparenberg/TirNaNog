package net.frozenchaos.TirNaNog.web.views;

import java.util.ArrayList;
import java.util.List;

public class ViewRow {
    List<ViewItem> viewItems = new ArrayList<>();

    public List<ViewItem> getViewItems() {
        return viewItems;
    }

    public void setViewItems(List<ViewItem> viewItems) {
        this.viewItems = viewItems;
    }
}
