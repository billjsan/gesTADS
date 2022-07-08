package src.ui.screen;

import src.ui.GesTADSScreensInterface;
import src.util.tools.Intent;

public class ScreensImpl implements GesTADSScreensInterface {
    @Override
    public void loginScreen(Intent intent) {

        new LoginScreen(intent);
    }
}