package test;

import src.ui.GesTADSUI;

public class ScreenInputTest extends GesTADSUI {

    @Override
    protected void createView() {

        String textFromTheUser = screenGetTextFromUser();
        Integer integerFromTheUser = screenGetIntegerFromUser();
    }
}