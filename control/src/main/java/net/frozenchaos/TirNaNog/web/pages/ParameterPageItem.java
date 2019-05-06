package net.frozenchaos.TirNaNog.web.pages;

public class ParameterPageItem extends PageItem {
    private String parameterPath = "";

    public ParameterPageItem() {
        super();
    }

    public ParameterPageItem(long id, int width, String parameterPath) {
        super(id);
        this.parameterPath = parameterPath;
    }

    public String getParameterPath() {
        return parameterPath;
    }

    public void setParameterPath(String parameterPath) {
        this.parameterPath = parameterPath;
    }
}
