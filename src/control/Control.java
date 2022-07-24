package src.control;

import src.model.model.Employee;
import src.model.repository.Repository;
import src.ui.UIManager;
import src.util.tools.BroadcastReceiver;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.List;

// [CDS] explicar o que a classe faz (Facade)
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

        // create other controls
        //new EmployeeControl();
    }

    /**
     * Entry point application
     */
    public void startApplication(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"startApplication");

        mRepository.startRepository();
        mUIManager.startUIManager();

        mUIManager.startHomeUI(null);
    }

    /**
     * Esse é o método mais importante do controller
     * ele recebe como parâmetro um Intent contendo
     * dados que serão tratatos de acordo com os casos
     * de uso
     */
    @Override
    protected void onReceive(Intent intent) {

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "onReceive action:" + intent.getAction());

        if(intent == null || intent.getAction() == null) return;

        //use-cases
        switch (intent.getAction()){
            case Intent.ACTION_LAUNCH_REGISTER_EMPLOYEE_SCREEN:
                launchRegisterScreen(intent);
                break;

            case Intent.ACTION_LAUNCH_LOGIN_SCREEN:
                launchLoginScreen(intent);
                break;

            case Intent.ACTION_LAUNCH_REMOVE_USER_SCREEN:
                launchRemoveScreen(intent);
                break;

            case Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN:
                launchSearchEmployeeScreen(intent);
                break;

            case Intent.ACTION_LAUNCH_MAIN_SCREEN:
                if (isLoggedIn){
                    mUIManager.startMainUI(intent);
                }
                break;

            case Intent.ACTION_LAUNCH_DIALOG_SCREEN:
                if (isLoggedIn){
                    mUIManager.startDialogUI(intent);
                }
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
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(), "searchEmployee");

        if(!intent.hasExtras()) {
            showDialogUI("Erro na busca");
            if (isLoggedIn) mUIManager.startMainUI(null);
            else mUIManager.startHomeUI(null);
        }

        List<Integer> flags = intent.getFlags();

        if(flags.contains(Intent.FLAG_SEARCH_EMPLOYEE_BY_CPF)){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG,Thread.currentThread(), "searchEmployee by cpf");


            String cpf = intent.getString(Intent.KEY_EMPLOYEE_CPF);
            Employee employee = mRepository.getEmployeeByCPF(cpf);

            if (employee == null){
                showDialogUI("Nenhum resultado encontrado");

                mUIManager.startSearchEmployeeUI(new Intent(Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN));
            }else {
                ArrayList<Intent> intents = new ArrayList<>();
                intents.add(populateIntentWithEmployee(Intent.ACTION_RESULT_SET, employee));

                Intent resulSetIntent = new Intent(Intent.ACTION_RESULT_SET);
                resulSetIntent.putList(Intent.KEY_RESULT_SET, intents);

                mUIManager.startSearchEmployeeUI(resulSetIntent);
            }

        }

    }

    private void launchSearchEmployeeScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(), "launchSearchEmployeeScreen");

        mUIManager.startSearchEmployeeUI(intent);
    }

    private void _searchEmployee(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(), "_searchEmployee");


        if(intent == null || intent.getAction() != Intent.ACTION_SEARCH_EMPLOYEE) return; // redundant [MCS]
        List<Integer> flags = intent.getFlags();

        if(flags.contains(Intent.FLAG_SEARCH_EMPLOYEE_BY_CPF)){

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
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(), "launchRemoveScreen");


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

        Intent dialogIntent = new Intent(Intent.ACTION_UI_FLAG);
        dialogIntent.putString(Intent.KEY_MESSAGE_DIALOG, message);

        mUIManager.startDialogUI(dialogIntent);
    }

    private Employee populateEmployee(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "populateEmployee");

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
            mUIManager.startMainUI(populateIntentWithEmployee(Intent.ACTION_UI_FLAG, mCurrentUser));
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

    // [MCS] talvez esse método possa ir
    // para a classe Intent como metodo estático
    private Intent populateIntentWithEmployee(int action, Employee employee){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(), "populateIntentWithEmployee");


        Intent intent = new Intent(action);
        intent.putString(Intent.KEY_EMPLOYEE_NAME, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_USERNAME, employee.getLogin());
        intent.putString(Intent.KEY_EMPLOYEE_CPF, employee.getCpf());
        intent.putString(Intent.KEY_EMPLOYEE_RG, employee.getRg());
        intent.putString(Intent.KEY_EMPLOYEE_SEXO, employee.getSexo());
        intent.putString(Intent.KEY_EMPLOYEE_ENDERECO, employee.getEndereco());
        intent.putString(Intent.KEY_EMPLOYEE_ESTADO_CIVIL, employee.getEstadoCivil());
        intent.putString(Intent.KEY_EMPLOYEE_MATRICULA, employee.getMatricula());
        intent.putString(Intent.KEY_EMPLOYEE_CARGO, employee.getCargo());
        intent.putString(Intent.KEY_EMPLOYEE_ADMISSAO, employee.getAdmissao());
        intent.putInt(Intent.KEY_EMPLOYEE_PRIVILEGE, employee.getPrivilegio());

        return intent;
    }

    /**
     * Esse método pode introduzir erros na página de
     * cadastro de novo usuário caso a intent recebida
     * possua a Intent.ACTION_UI_FLAG E NÃO possua os
     * cargos para mostrar na tela
     * @param intent
     */
    private void launchRegisterScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, "launchRegisterScreen");

        if(intent == null || intent.getAction() != Intent.ACTION_UI_FLAG){
            intent = new Intent(Intent.ACTION_UI_FLAG);

            //lista de cargos
            intent.putFlag(Intent.FLAG_POSITIONS_DATA);
            intent.putList(Intent.KEY_DATA_CARGOS, mRepository.getPositions());
        }

        mUIManager.startRegisterUI(intent);
    }
}