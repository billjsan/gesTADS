package src.ui;

import src.util.tools.GesLogger;

public abstract class GesTADSUI {

    private final String TAG = GesTADSUI.class.getSimpleName();

    public GesTADSUI(){
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "GesTADSUI mother constructor");
        onCreated();
    }
    public  void onCreated(){
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "GesTADSUI mother onCreated");
        onStart();
    }

    public  void onStart(){
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "GesTADSUI mother onStart");
        createView();
    }

    public abstract void createView();

    protected void onDestroy(){
        if(GesLogger.ISLOGABLE) GesLogger.d(TAG, "onDestroy");
    }

}