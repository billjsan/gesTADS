package src.control;

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
    private Employee mCurrentUser = null;

    public Control(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"constuctor");

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

        while(shouldRun){
            // [LAS]
            if(!isLoggedIn){
                mUIManager.startLoginUI();
            }//else  {
//                mUIManager.startHomeUI(mCurrentUser.getPrivilegio());
//            }
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
                    GesLogger.d(TAG, Thread.currentThread(), "ACTION_LOGIN");

                String login = intent.getString(Intent.KEY_USERNAME);
                String pass = intent.getString(Intent.KEY_PASSWORD);
                String matricula = intent.getString(Intent.KEY_MATRICULA);

                if(isValidCredential(matricula, login, pass)){
                    isLoggedIn = true;
                    shouldRun = false;
                }
                break;

            case Intent.ACTION_REGISTER:
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, "ACTION_REGISTER");

                Employee newUser = new Employee();
                newUser.setNome(intent.getString(Intent.KEY_NAME));
                newUser.setLogin(intent.getString(Intent.KEY_USERNAME));
                newUser.setSenha(intent.getString(Intent.KEY_PASSWORD));
                newUser.setCpf(intent.getString(Intent.KEY_CPF));
                newUser.setRg(intent.getString(Intent.KEY_RG));
                newUser.setSexo(intent.getString(Intent.KEY_SEXO));
                newUser.setEstadoCivil(intent.getString(Intent.KEY_ESTADO_CIVIL));
                newUser.setCargo(intent.getString(Intent.KEY_CARGO));
                newUser.setMatricula(intent.getString(Intent.KEY_MATRICULA));
                newUser.setEndereco(intent.getString(Intent.KEY_ENDERECO));
                newUser.setPrivilegio(intent.getInt(Intent.KEY_PRIVILEGE));

                //newUser.setAdmissao(intent.getString(Intent.KEY_ADMISSAO));
                mRepository.addEmployee(newUser);
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
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG,Thread.currentThread(),"isValidCredential");

        List<Employee> employees = mRepository.getEmployees();

        for (Employee employee : employees) {
            if(employee.getMatricula().equals(matricula)){
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
                    GesLogger.d(TAG,Thread.currentThread(), "matricula encontrada: " + matricula);

                if(employee.getSenha().equals(pass) && employee.getLogin().equals(login)){
                    if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) {
                        GesLogger.d(TAG,Thread.currentThread(), "credenciais encontradas com sucesso");
                    }

                    mCurrentUser = employee;
                    return true;
                }

                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE ) GesLogger.d(TAG,
                        Thread.currentThread(), "senha ou login errados l:" + login + " p:" + pass);
            }
        }

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE) GesLogger.d(TAG,
                Thread.currentThread(), "matricula nao encontrada " + matricula);
        return false;
    }
}