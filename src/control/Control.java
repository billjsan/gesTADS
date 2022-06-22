package src.control;

import src.model.model.Admin;
import src.model.model.Employee;
import src.model.repository.Repository;
import src.ui.UIManager;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.List;


public class Control extends BroadcastReceiver {

    private final Repository mRepository;
    private final UIManager mUIManager;
    private boolean shouldRun = true;
    private Integer currentPrivilege = 0;
    private boolean isLoggedIn = false;
    private final String TAG = Control.class.getSimpleName();
    private Employee mCurrentUser = null;

    public Control(){
        if(GesLogger.ISFULLLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName() + " Control");
        mRepository = Repository.getInstance();
        mUIManager = UIManager.getInstance();
    }

    /**
     * Entry point application
     */
    public void startApplication(){
        //mUIManager.startUIManager();

        while(shouldRun){
            if(!isLoggedIn){
                mUIManager.startLoginUI();
            }else {
                mUIManager.startHomeUI(currentPrivilege);
            }
        }
    }

    @Override
    public void onReceive(Intent intent) {

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG, "onReceive");
        if(intent == null || intent.getAction() == null) return;

        //usecases
        switch (intent.getAction()){
            case Intent.ACTION_LOGIN:
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, "ACTION_LOGIN");

                String login = intent.getString(Intent.KEY_USERNAME);
                String pass = intent.getString(Intent.KEY_PASSWORD);
                String matricula = intent.getString(Intent.KEY_MATRICULA);

                if(isValidCredential(matricula, login, pass)){
                    isLoggedIn = true;
                }
                break;

            case Intent.ACTION_REGISTER:
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, "ACTION_REGISTER");

                Admin admin = new Admin();
                admin.setNome(intent.getString(Intent.KEY_NAME));
                admin.setLogin(intent.getString(Intent.KEY_USERNAME));
                admin.setSenha(intent.getString(Intent.KEY_PASSWORD));
                admin.setCpf(intent.getString(Intent.KEY_CPF));

                mRepository.addEmployee(admin);
                break;
        }
    }

    /**
     * Method acess the repository employees list and
     * verify if the data receveid is valid
     *
     * @param matricula
     * @param login
     * @param pass
     * @return
     */
    private boolean isValidCredential(String matricula, String login, String pass) {

        List<Employee> employees = mRepository.getEmployees();

        if(employees.isEmpty()){
            mUIManager.startFirstLoginScreen();
            return false;
        }

        for (Employee employee :employees) {
            if(employee.getMatricula().equals(matricula)){
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
                    GesLogger.d(TAG,Thread.currentThread().getName() + " matricula encontrada: " + matricula);

                if(employee.getSenha().equals(pass) && employee.getLogin().equals(login)){
                    mCurrentUser = employee;

                    if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {
                        GesLogger.d(TAG,Thread.currentThread().getName() + " Login realizado com sucesso");
                    }
                    return true;
                }

                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE )
                    GesLogger.d(TAG, Thread.currentThread().getName() +
                            " senha ou login errados l:" + login + " p:" + pass);
            }
        }

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG,
                Thread.currentThread().getName() + " Matricula nao encontrada");
        return false;
    }
}