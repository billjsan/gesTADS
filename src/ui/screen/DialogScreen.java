package src.ui.screen;

import src.ui.GesTADSUI;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

public class DialogScreen extends GesTADSUI {

    private final String TAG = "DialogScreen";
    public DialogScreen(Intent intent) {
        super(intent);
    }

    @Override
    protected void createView() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "createView");

        if(mContextIntent == null || mContextIntent.getAction() != Intent.ACTION_UI_FLAG){

            onDestroy();
            return;
        }

        System.out.println();
        System.out.println(formattedTitle("DIALOG"));
        System.out.println(formattedTitle(mContextIntent.getString(Intent.KEY_MESSAGE_DIALOG)));
        System.out.println(formattedTitle("-"));
        System.out.println(formattedTitle("-"));
        System.out.println();

        onDestroy();
    }

    @Override
    protected void onDestroy() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"onDestroy");
        super.onDestroy();
    }
}
