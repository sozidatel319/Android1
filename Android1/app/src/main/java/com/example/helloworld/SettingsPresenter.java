package com.example.helloworld;

public final class SettingsPresenter {

    private boolean themecolor;
    private boolean fontsize;
    private boolean unitofmeasure;

    private static SettingsPresenter instance;

    public static SettingsPresenter getInstance() {
        instance = instance == null ? new SettingsPresenter() : instance;
        return instance;
    }

    public boolean getThemecolor() {
        return themecolor;
    }

    public void setThemecolor(boolean themecolor) {
        this.themecolor = themecolor;
    }

    public boolean getFontsize() {
        return fontsize;
    }

    public void setFontsize(boolean fontsize) {
        this.fontsize = fontsize;
    }

    public boolean getUnitofmeasure() {
        return unitofmeasure;
    }

    public void setUnitofmeasure(boolean unitofmeasure) {
        this.unitofmeasure = unitofmeasure;
    }
}
