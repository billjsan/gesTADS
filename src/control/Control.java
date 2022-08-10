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
    @Deprecated
    //private boolean isLoggedIn = false;
    private final String TAG = Control.class.getSimpleName();

    public Control(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"Control constructor");

        new ProductControl();
        new TransactionControl();

        mRepository = Repository.getInstance();
        mUIManager = UIManager.getInstance();
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

            case Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN:
                launchSearchEmployeeScreen(intent);
                break;

            case Intent.ACTION_LAUNCH_MAIN_SCREEN:

                    mUIManager.startMainUI(intent);

                break;

            case Intent.ACTION_LAUNCH_DIALOG_SCREEN:

                    mUIManager.startDialogUI(intent);
                break;

            case Intent.ACTION_LAUNCH_EDIT_EMPLOYEE:
                editEmployee(intent);
                break;

            case Intent.ACTION_UPDATE_EMPLOYEE:
                updateEmployee(intent);
                break;

            case Intent.ACTION_REMOVE_EMPLOYEE:
                removeEmployee(intent);
                break;

            case Intent.ACTION_VALIDATE_NEW_USER:
                validateNewUser(intent);
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

    private void updateEmployee(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "updateEmployee");

        List<Integer> flags = intent.getFlags();

        if(flags.contains(Intent.FLAG_RESULT_SET)){
            if(mRepository.isDbReady()){

                try {
                    Intent employeeIntent = (Intent) intent.getList(Intent.KEY_RESULT_SET).get(0);

                    Employee newEmployee = populateEmployeeWithIntent(employeeIntent);

                    mRepository.updateEmployee(newEmployee, employeeIntent.getLong(Intent.KEY_EMPLOYEE_ID));

                    showDialogUI("Usuário atualizado com sucesso");

                    mUIManager.startMainUI(null);

                }catch (NullPointerException e){
                    if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                        GesLogger.e(TAG, "um erro ocorreu ao acessar o dado");

                }catch (Exception e){
                    if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                        GesLogger.e(TAG, "um erro desconhecido ocorreu: " + e.getMessage());
                }

            }else {

                showDialogUI("banco de dados offline");
                mUIManager.startMainUI(null);
            }
        }else {
            showDialogUI("Erro ao processa a solicitação ");
            mUIManager.startMainUI(null);
        }
    }

    private void editEmployee(Intent intent) {
        // [LAS]

        if(mRepository.getCurrentUser().getPrivilegio() != Employee.PRIVILEGE_ADMIN){

            showDialogUI("Acesso negado!");
            mUIManager.startMainUI(null);
        }else {

            if(intent == null || intent.getAction() != Intent.ACTION_LAUNCH_EDIT_EMPLOYEE){
                intent = new Intent(Intent.ACTION_LAUNCH_EDIT_EMPLOYEE);
            }

            intent.putFlag(Intent.FLAG_POSITIONS_DATA);
            intent.putList(Intent.KEY_DATA_CARGOS, mRepository.getCargos());

            // reaproveita a tela de registro
            mUIManager.startRegisterUI(intent);
        }
    }

    private void removeEmployee(Intent intent) {
        //[LAS]

        if(mRepository.getCurrentUser().getPrivilegio() != Employee.PRIVILEGE_ADMIN){

            showDialogUI("Acesso negado!");
            mUIManager.startMainUI(null);
        }else{

            if (intent == null || !intent.hasExtras()){
               mUIManager.startMainUI(null);
            }

            try {
                List<Integer> flags = intent.getFlags();

                if (flags.contains(Intent.FLAG_RESULT_SET)){
                    List<Intent> list = (List<Intent>) intent.getList(Intent.KEY_RESULT_SET);
                    Intent empregado = list.get(0);

                    mRepository.removeEmployee(populateEmployeeWithIntent(empregado),
                            empregado.getLong(Intent.KEY_EMPLOYEE_ID));
                    showDialogUI("Usuário removido com sucesso");
                    mUIManager.startSearchEmployeeUI(new Intent(Intent.ACTION_LAUNCH_SEARCH_EMPLOYEE_SCREEN));
                }
            }catch (NullPointerException e){
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "erro ao acessar um recurso: " + e.getMessage());

                mUIManager.startMainUI(null);

            }catch (Exception e){
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "um erro desconhecido ocorreu: " + e.getMessage());

                mUIManager.startMainUI(null);
            }
        }
    }

    private void searchEmployee(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(), "searchEmployee");

        if(!intent.hasExtras()) {
            showDialogUI("Erro na busca");
            mUIManager.startMainUI(null);
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

    private void launchLoginScreen(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "launchLoginScreen");

        mUIManager.startLoginUI(intent);
    }

    private void logout() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "logout");

        mRepository.isLoggedIn(false); //[ICS] usar essa estrategia
        //mRepository.setCurrentUser(null);
        mUIManager.startHomeUI(null);
    }

    private void validateNewUser(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "validateNewUser");

        if(mRepository.isDbReady()){
            List<Employee> employees = mRepository.getEmployees();
            Employee newEmployee = populateEmployeeWithIntent(intent);

            for (Employee empl: employees) {
                if(empl.getCpf().equals(newEmployee.getCpf())){

                    showDialogUI("Usuário ja cadastrado");
                    mUIManager.startMainUI(intent);
                    return;
                }
            }

            newEmployee.generateID();
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

    private Employee populateEmployeeWithIntent(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "populateEmployeeWithIntent");

        return new Employee(
                intent.getString(Intent.KEY_EMPLOYEE_NAME),
                intent.getString(Intent.KEY_EMPLOYEE_USERNAME),
                intent.getString(Intent.KEY_EMPLOYEE_PASSWORD),
                intent.getString(Intent.KEY_EMPLOYEE_CARGO),
                intent.getString(Intent.KEY_EMPLOYEE_CPF)
        );
    }

    private void checkCredentials(Intent intent) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "checkCredentials");

        if(isValidCredentials(intent)){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "credentials validated");

            mRepository.isLoggedIn(true);
           // mRepository.setCurrentUser(populateEmployeeWithIntent(intent));
            mUIManager.startMainUI(populateIntentWithEmployee(Intent.ACTION_UI_FLAG, mRepository.getCurrentUser()));
        }else {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                GesLogger.d(TAG, Thread.currentThread(), "credentials refused");

            showDialogUI("Credenciais inválidas");
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

                    System.out.println(employee.getNome());
                    System.out.println(employee.getLogin());
                    System.out.println(employee.getSenha());
                    System.out.println(employee.getCargo());
                    System.out.println(employee.getCpf());
                    System.out.println(employee.getId());

                    mRepository.isLoggedIn(true);
                    mRepository.setCurrentUser(employee);
                    return true;
                }
            }
        }
        return false;
    }

    // [ICS] mudar para uma tela de dialogo
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

    // [MCS] talvez esse método possa ir
    // para a classe Intent como metodo estático
    private Intent populateIntentWithEmployee(int action, Employee employee){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(), "populateIntentWithEmployee");

        Intent intent = new Intent(action);
        intent.putString(Intent.KEY_EMPLOYEE_NAME, employee.getNome());
        intent.putString(Intent.KEY_EMPLOYEE_USERNAME, employee.getLogin());
        intent.putString(Intent.KEY_EMPLOYEE_CPF, employee.getCpf());
        intent.putString(Intent.KEY_EMPLOYEE_CARGO, employee.getCargo());
        intent.putInt(Intent.KEY_EMPLOYEE_PRIVILEGE, employee.getPrivilegio());
        intent.putLong(Intent.KEY_EMPLOYEE_ID, employee.getId());

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

        if(mRepository.getCurrentUser().getPrivilegio() != Employee.PRIVILEGE_ADMIN){

            showDialogUI("Acesso negado!");
            mUIManager.startMainUI(null);
        }else {

            if(intent == null || intent.getAction() != Intent.ACTION_LAUNCH_REGISTER_EMPLOYEE_SCREEN){
                intent = new Intent(Intent.ACTION_LAUNCH_REGISTER_EMPLOYEE_SCREEN);
            }

            intent.putFlag(Intent.FLAG_POSITIONS_DATA);
            intent.putList(Intent.KEY_DATA_CARGOS, mRepository.getCargos());

            mUIManager.startRegisterUI(intent);
        }
    }
}