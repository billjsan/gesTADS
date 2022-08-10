package src.ui.screen;

import src.ui.GesTADSSUIInterface;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import static src.util.tools.GesLogger.TAG;

public class GesTADSScreensImpl implements GesTADSSUIInterface {
    @Override
    public void loginScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "loginScreen "+ intent);
        new LoginScreen(intent);
    }

    @Override
    public void homeScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "homeScreen "+ intent);
        new HomeScreen(intent);
    }

    @Override
    public void mainScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "mainScreen"+ intent);
        new MainScreen(intent);
    }

    @Override
    public void registerScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "registerScreen "+ intent);
        new RegisterEmployeeScreen(intent);
    }

    @Override
    public void dialogScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "dialogScreen "+ intent);
        new DialogScreen(intent);
    }

    @Override
    public void removeScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "removeScreen "+ intent);

    }

    @Override
    public void searchScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "searchScreen "+ intent);

    }

    @Override
    public void searchEmployeeScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "searchEmployeeScreen "+ intent);

        new SearchEmployeeScreen(intent);
    }

    @Override
    public void registerProductScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "registerProductScreen "+ intent);

        new RegisterProductScreen(intent);
    }

    @Override
    public void searchProductScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "searchProductScreen "+ intent);

        new SearchProductScreen(intent);
    }
}