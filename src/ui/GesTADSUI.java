package src.ui;

import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class GesTADSUI {

    private final String TAG = GesTADSUI.class.getSimpleName();
    protected Intent mContextIntent;
    private ExecutorService mExecutor;

    public GesTADSUI() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "constructor");
        onCreate();
        //newThread();
    }

    public GesTADSUI(Intent intent) {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "constructor with Intent");

        this.mContextIntent = intent;
        onCreate();
        //newThread();
    }

    private void newThread() {
        mExecutor = Executors.newSingleThreadExecutor();
        mExecutor.submit(new Runnable() {
            @Override
            public void run() {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG,Thread.currentThread(), "onRun");
                //GesTADSUI.this.onCreated();
            }
        });
    }

    public void onCreate() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "onCreated");
        onStart();
    }

    public void onStart() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "onStart");
        createView();
    }

    public abstract void createView();

    protected void onDestroy() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "onDestroy");

        //mExecutor.shutdown();
    }
}