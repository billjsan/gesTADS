package src.ui;

import src.ui.screen.GesTADSScreensImpl;

public class UIFactory {
    static final int NO_UI = 1;
    static final int SWING_UI = 2;

    static GesTADSSUIInterface getInterface(int ui) {

        if (ui == SWING_UI) {
            return getSingUI();
        } else {
            return getNoUI();
        }
    }

    private static GesTADSSUIInterface getSingUI() {
        return null;
    }

    private static GesTADSSUIInterface getNoUI() {
        return new GesTADSScreensImpl();
    }
}
