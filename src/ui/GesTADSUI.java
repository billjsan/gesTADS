package src.ui;

import src.util.tools.GesLogger;
import src.util.tools.Intent;

public abstract class GesTADSUI {

    private final String TAG = GesTADSUI.class.getSimpleName();

    protected Intent mContextIntent;

    public GesTADSUI(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "mother constructor");
        onCreated();
    }

    public GesTADSUI(Intent intent){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "mother constructor with Intent");

        this.mContextIntent = intent;
        onStart();
    }

    public  void onCreated(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "mother onCreated");
        onStart();
    }

    public  void onStart(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "mother onStart");
        createView();
    }

    public abstract void createView();

    protected void onDestroy(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "onDestroy");
    }
}