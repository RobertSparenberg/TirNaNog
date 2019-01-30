package net.frozenchaos.TirNaNog.web.pages;

public class ParameterPageItem extends PageItem {
    private String parameterPath;
    private int updateDelay = -1;

    public ParameterPageItem() {
        super();
    }

    public ParameterPageItem(long id, int width, String parameterPath) {
        super(id, width);
        this.parameterPath = parameterPath;
    }

    public ParameterPageItem(long id, int width, String parameterPath, int updateDelay) {
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
