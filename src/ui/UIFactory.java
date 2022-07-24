package src.ui;

import src.ui.screen.GesTADSScreensImpl;
// [CDS] explicar o que a classe faz
public class UIFactory {
    static final int NO_UI = 1;
    static final int SWING_UI = 2;

    static GesTADSSUIInterface getInterface(int ui) {
        //[LAS]

        if (ui == SWING_UI) {
            return getSingUI();
        } else {
            return getNoUI();
        }
    }

    private static GesTADSSUIInterface getSingUI() {
        //[LAS]
        return null;
    }

    private static GesTADSSUIInterface getNoUI() {
        //[LAS]
        return new GesTADSScreensImpl();
    }
}
