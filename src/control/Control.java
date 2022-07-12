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

        mRepository.startRepository();
        mUIManager.startUIManager();

        mUIManager.startHomeUI(null);
    }

    @Override
    protected void onReceive(Intent intent) {

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG, "onReceive");
        if(intent == null || intent.getAction() == null) return;

        //usecases
        switch (intent.getAction()){
            case Intent.ACTION_LAUNCH_REGISTER_SCREEN:
                launchRegisterScreen(null);
                break;

            case Intent.ACTION_LAUNCH_LOGIN_SCREEN:
                launchLoginScreen(intent);
                break;

            case Intent.ACTION_LAUNCH_REMOVE_USER_SCREEN:
                launchRemoveScreen(intent);
                break;

            case Intent.ACTION_VALIDATE_NEW_USER:
                validateNewUser(intent);
                break;

            case Intent.ACTION_LOGIN:

                System.out.println("erro, nao deveria rodar a tela de login por aqui");
                break;

            case Intent.ACTION_SEARCH_EMPLOYEE:
                searchEmployee(intent);
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

    private void searchEmployee(Intent intent) {
        //[LAS]

        if(intent == null || intent.getAction() != Intent.ACTION_SEARCH_EMPLOYEE) return;
        List<Integer> flags = intent.getFlags();

        if(flags.contains(Intent.FLAG_SEARCH_BY_CPF)){

            mUIManager.startSearchUI(intent);

        } else if (flags.contains(Intent.FLAG_SEARCH_BY_MATRICULA)) {

            if(intent.getString(Intent.KEY_EMPLOYEE_MATRICULA) != null){
                Employee emp = mRepository.getEmployeeByMatricula(intent.getString(Intent.KEY_EMPLOYEE_MATRICULA));

                if(emp != null){
                    Intent populatedIntent = populateIntentWithEmployee(Intent.ACTION_SEARCH_EMPLOYEE, emp);
                    if(flags.contains(Intent.FLAG_REMOVING_USER_IN_PROGRESS)){
                        populatedIntent.putFlag(Intent.FLAG_REMOVING_USER_IN_PROGRESS);
                        mUIManager.startRemoveUI(populatedIntent);
                    }
                    mUIManager.startSearchUI(populatedIntent);
                }
            }else {

                //matricula nao encontrada
                mUIManager.startSearchUI(intent);
            }

        } else if (flags.contains(Intent.FLAG_SEARCH_BY_NOME)) {

            //outr o algo
        }else {
            //mais lele
        }

    }

    private void launchRemoveScreen(Intent intent) {
        //[LAS]

        intent.putFlag(Intent.FLAG_REMOVING_USER_IN_PROGRESS);
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
            Employee newEmployee = populateEmployee(intent);

            for (Employee empl: employees) {
                if(empl.getCpf().equals(newEmployee.getCpf())){

                    showDialogUI("Usuário ja cadastrado");

                    if(isLoggedIn){
                        mUIManager.startMainUI(intent);
                    }else {
                        mUIManager.startHomeUI(intent);
                    }
                    return;
                }
            }

            mRepository.addEmployee(newEmployee);

            showDialogUI("Usuário cadastrado com sucesso");
            mUIManager.startMainUI(intent);
        }else {

            showDialogUI("banco de dados offline");
            mUIManager.startMainUI(intent);
        }
    }

    private void showDialogUI(String message){
        //[LAS]

        Intent dialogIntent = new Intent(Intent.ACTION_UI_FLAG);
        dialogIntent.putString(Intent.KEY_MESSAGE_DIALOG, message);

        mUIManager.startDialogUI(dialogIntent);
    }

    private Employee populateEmployee(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "populateUser");

        return new Employee(
                intent.getString(Intent.KEY_EMPLOYEE_NAME),
                intent.getString(Intent.KEY_EMPLOYEE_USERNAME),
                intent.getString(Intent.KEY_EMPLOYEE_PASSWORD),
                intent.getString(Intent.KEY_EMPLOYEE_CARGO),
                intent.getString(Intent.KEY_EMPLOYEE_ADMISSAO),
                intent.getString(Intent.KEY_EMPLOYEE_SEXO),
                intent.getString(Intent.KEY_EMPLOYEE_CPF),
                intent.getString(Intent.KEY_EMPLOYEE_RG),
                intent.getString(Intent.KEY_EMPLOYEE_ENDERECO),
                intent.getString(Intent.KEY_EMPLOYEE_ESTADO_CIVIL),
                intent.getString(Intent.KEY_EMPLOYEE_MATRICULA)
        );
    }

    private void checkCredentials(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "checkCredentials");

        if(isValidCredentials(intent)){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "credentials validated");

            isLoggedIn = true;
            mUIManager.startMainUI(populateIntentWithEmployee(Intent.ACTION_UI_FLAG,mCurrentUser));
        }else {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "credentials refused");

            mUIManager.startLoginUI(intent);
        }
    }

    private boolean isValidCredentials(Intent intent) {

        if(intent == null) return false;

        String login = intent.getString(Intent.KEY_EMPLOYEE_USERNAME);
        String pass = intent.getString(Intent.KEY_EMPLOYEE_PASSWORD);

        if(login == null || pass == null) return false;

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) GesLogger.d(TAG, Thread.currentThread(),
                "isValidCredentials: login:" + login + " pass:" + pass);

        awaitDatabase();

        List<Employee> employees = mRepository.getEmployees();

        for (Employee employee : employees) {
            if(employee.getLogin().equals(login)){
                if(employee.getSenha().equals(pass) && employee.getLogin().equals(login)){

                    mCurrentUser = employee; //campo modificado aqui para evitar re-busca no aaray
                    return true;
                }
            }
        }
        return false;
    }

    private void awaitDatabase() {
        do {
            try {
                System.out.println("Aguarde...");
                Thread.sleep(500);
            } catch (InterruptedException e) {
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE) GesLogger.e(TAG, e.getMessage());
            }
        }while (!mRepository.isDbReady());
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

    private Intent populateIntentWithEmployee(int action, Employee employee){
        //[LAS]

        Intent intent = new Intent(action);
        intent.putString(Intent.KEY_EMPLOYEE_NAME, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_USERNAME, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_CPF, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_RG, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_SEXO, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_ENDERECO, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_ESTADO_CIVIL, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_MATRICULA, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_CARGO, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_ADMISSAO, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_PRIVILEGE, employee.getNome());

        return intent;
    }

    private void launchRegisterScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "launchRegisterScreen");

        if(intent == null){
            intent = new Intent(Intent.ACTION_UI_FLAG);

            //lista de cargos
            intent.putFlag(Intent.FLAG_POSITIONS_DATA);
            intent.putList(Intent.KEY_DATA_CARGOS, mRepository.getPositions());
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