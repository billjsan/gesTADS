package src.ui;

import src.ui.screen.GesTADSScreensImpl;
import src.util.tools.GesLogger;

import static src.util.tools.GesLogger.TAG;

// [CDS] explicar o que a classe faz
public class UIFactory {
    static final int NO_UI = 1;
    static final int SWING_UI = 2;

    static GesTADSSUIInterface getInterface(int ui) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getInterface: "+ ui);

        if (ui == SWING_UI) {
            return getSingUI();
        } else {
            return getNoUI();
        }
    }

    private static GesTADSSUIInterface getSingUI() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getSingUI");
        return null;
    }

    private static GesTADSSUIInterface getNoUI() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getNoUI");
        return new GesTADSScreensImpl();
    }
}
