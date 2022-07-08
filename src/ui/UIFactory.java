package src.ui;

import src.ui.screen.ScreensImpl;

public class UIFactory {
    static final int NO_UI = 1;
    static final int SWING_UI = 2;

    static GesTADSScreensInterface getInterface(int ui) {

        if (ui == SWING_UI) {
            return getSingUI();
        } else {
            return getNoUI();
        }
    }

    private static GesTADSScreensInterface getSingUI() {
        return null;
    }

    private static GesTADSScreensInterface getNoUI() {
        return new ScreensImpl();
    }
}
