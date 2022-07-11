package src.control;

import src.GesTADSApp;
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
    private boolean isLoggedIn = false;
    private final String TAG = Control.class.getSimpleName();
    private volatile Employee mCurrentUser = null;

    public Control(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"Control constructor");

        mRepository = Repository.getInstance();
        mUIManager = UIManager.getInstance();
    }

    /**
     * Entry point application
     */
    public void startApplication(){
        //[LAS]

        //GesTADSApp.infoThread();

        mRepository.startRepository();
        mUIManager.startUIManager();

        GesTADSApp.infoThread();

        mUIManager.startHomeUI(null);
//        while(shouldRun){
//            // [LAS]
//            if(!isLoggedIn){
//                mUIManager.startLoginUI(null);
//            }//else  {
////                mUIManager.startHomeUI(mCurrentUser.getPrivilegio());
////            }
//        }
    }

    @Override
    public void onReceive(Intent intent) {

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG, "onReceive");
        if(intent == null || intent.getAction() == null) return;

        //usecases
        switch (intent.getAction()){
            case Intent.LAUNCH_REGISTER_SCREEN:
                launchRegisterScreen(null);
                break;

            case Intent.LAUNCH_LOGIN_SCREEN:
                launchLoginScreen(intent);
                break;

            case Intent.LAUNCH_REMOVE_USER_SCREEN:
                launchRemoveScreen(intent);
                break;

            case Intent.ACTION_VALIDATE_NEW_USER:
                validateNewUser(intent);
                break;

            case Intent.ACTION_LOGIN:

                System.out.println("erro, nao deveria rodar a tela de login por aqui");
                break;

            case Intent.ACTION_QUIT:
                finalizeApp();
                break;

            case Intent.ACTION_LOGOUT:
                logout();
                break;

            case Intent.ACTION_CHECK_CREDENTIALS:
                checkCredentials(intent);
                break;
        }
    }

    private void launchRemoveScreen(Intent intent) {
        mUIManager.startRemoveUI(intent);
    }

    private void launchLoginScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "launchLoginScreen");

        mUIManager.startLoginUI(intent);
    }

    private void logout() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "logout");

        isLoggedIn = false;
        mUIManager.startHomeUI(null);
    }

    private void validateNewUser(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "validateNewUser");

        if(mRepository.isDbReady()){
            List<Employee> employees = mRepository.getEmployees();
            Employee newEmployee = populateUser(intent);

            for (Employee empl: employees) {
                if(empl.getCpf().equals(newEmployee.getCpf())){

                    Intent dialogIntent = new Intent(Intent.ACTION_UI_FLAG);
                    dialogIntent.putString(Intent.KEY_MESSAGE_DIALOG, "Usuário ja cadastrado");

                    mUIManager.startDialogUI(dialogIntent);

                    if(isLoggedIn){
                        mUIManager.startMainUI(intent);
                    }else {
                        mUIManager.startHomeUI(intent);
                    }
                    return;
                }
            }

            mRepository.addEmployee(newEmployee);

            Intent dialogIntent = new Intent(Intent.ACTION_UI_FLAG);
            dialogIntent.putString(Intent.KEY_MESSAGE_DIALOG, "Usuário cadastrado com sucesso");

            mUIManager.startDialogUI(dialogIntent);

            mUIManager.startMainUI(intent);
        }else {

            Intent dialogIntent = new Intent(Intent.ACTION_UI_FLAG);
            dialogIntent.putString(Intent.KEY_MESSAGE_DIALOG, "banco de dados offline");

            mUIManager.startDialogUI(dialogIntent);

            mUIManager.startMainUI(intent);
        }
    }

    private Employee populateUser(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "populateUser");

        return new Employee(
                intent.getString(Intent.KEY_NAME),
                intent.getString(Intent.KEY_USERNAME),
                intent.getString(Intent.KEY_PASSWORD),
                intent.getString(Intent.KEY_CARGO),
                intent.getString(Intent.KEY_ADMISSAO),
                intent.getString(Intent.KEY_SEXO),
                intent.getString(Intent.KEY_CPF),
                intent.getString(Intent.KEY_RG),
                intent.getString(Intent.KEY_ENDERECO),
                intent.getString(Intent.KEY_ESTADO_CIVIL),
                intent.getString(Intent.KEY_MATRICULA)
        );
    }

    private void checkCredentials(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "checkCredentials");

        if(isValidCredentials(intent)){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "credentials validated");

            isLoggedIn = true;
            mUIManager.startMainUI(intent);
        }else {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "credentials refused");

            mUIManager.startLoginUI(intent);
        }
    }

    private boolean isValidCredentials(Intent intent) {

        String login = intent.getString(Intent.KEY_USERNAME);
        String pass = intent.getString(Intent.KEY_PASSWORD);
        String mat = intent.getString(Intent.KEY_MATRICULA);

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) GesLogger.d(TAG, Thread.currentThread(),
                "isValidCredentials: login:" + login + " pass:" + pass + " mat:" + mat);

        do {
            try {
                System.out.println("Aguarde...");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG, e.getMessage());
            }
        }while (!mRepository.isDbReady());

        List<Employee> employees = mRepository.getEmployees();

        for (Employee employee : employees) {
            if(employee.getMatricula().equals(mat)){
                if(employee.getSenha().equals(pass) && employee.getLogin().equals(login)){

                    mCurrentUser = employee;
                    return true;
                }
            }
        }
        return false;
    }

    private void finalizeApp() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "finalizeApp");

        mRepository.closeDatabase();
        BroadcastReceiver.shutdownBroadcastExecutors();
    }

    private void login(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "login");

        mUIManager.startLoginUI(intent);
    }

    private void launchRegisterScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "launchRegisterScreen");

        if(intent == null){
            intent = new Intent(Intent.ACTION_UI_FLAG);

            //lista de cargos
            intent.putFlag(Intent.FLAG_POSITIONS_DATA);
            intent.putList(Intent.KEY_DATA_POSITIONS, mRepository.getPositions());
        }

        mUIManager.startRegisterUI(intent);
//        Employee newUser = new Employee();
//        newUser.setNome(intent.getString(Intent.KEY_NAME));
//        newUser.setLogin(intent.getString(Intent.KEY_USERNAME));
//        newUser.setSenha(intent.getString(Intent.KEY_PASSWORD));
//        newUser.setCpf(intent.getString(Intent.KEY_CPF));
//        newUser.setRg(intent.getString(Intent.KEY_RG));
//        newUser.setSexo(intent.getString(Intent.KEY_SEXO));
//        newUser.setEstadoCivil(intent.getString(Intent.KEY_ESTADO_CIVIL));
//        newUser.setCargo(intent.getString(Intent.KEY_CARGO));
//        newUser.setMatricula(intent.getString(Intent.KEY_MATRICULA));
//        newUser.setEndereco(intent.getString(Intent.KEY_ENDERECO));
//        newUser.setPrivilegio(intent.getInt(Intent.KEY_PRIVILEGE));
//
//        //newUser.setAdmissao(intent.getString(Intent.KEY_ADMISSAO));
//        if(mRepository.isDbReady()){
//            mRepository.addEmployee(newUser);
//        }else {
//            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
//                GesLogger.e(TAG, "database NOT ready");
//        }
    }
}