package src.control;

import src.util.tools.BroadcastReceiver;
import src.util.tools.Intent;

public class EmployeeControl extends BroadcastReceiver {


    @Override
    protected void onReceive(Intent intent) {

        System.out.println("oi");
        if(intent == null) return;

        if(intent.getAction() == Intent.ACTION_REMOVE_EMPLOYEE){
            System.out.println("EmployeeControl aqui bb");
        }
    }
}
