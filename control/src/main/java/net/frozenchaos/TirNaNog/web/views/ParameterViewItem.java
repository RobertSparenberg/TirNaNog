package net.frozenchaos.TirNaNog.web.views;

public class ParameterViewItem extends ViewItem {
    private String parameterPath;
    private int updateDelay = -1;

    public ParameterViewItem() {
        super();
    }

    public ParameterViewItem(long id, int width, String parameterPath) {
        super(id, width);
        this.parameterPath = parameterPath;
    }

    public ParameterViewItem(long id, int width, String parameterPath, int updateDelay) {
        super(id, width);
        this.parameterPath = parameterPath;
        this.updateDelay = updateDelay;
    }

    public String getParameterPath() {
        return parameterPath;
    }

    public void setParameterPath(String parameterPath) {
        this.parameterPath = parameterPath;
    }
}
