package src.model.repository;

import src.model.model.Employee;
import src.model.repository.database.GesTADSDataBaseInterface;
import src.model.repository.database.VolatileDataBase;
import src.util.tools.GesLogger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Repository {

    private static Repository instance;
    private final GesTADSDataBaseInterface mDB;
    private final String TAG = Repository.class.getSimpleName();

    //private final List<String> teste = Collections.synchronizedList(new ArrayList<>());

    private Repository(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG, Thread.currentThread(),
                "Repository constructor");
        
        // [ICS] - talvez usar uma factory recebendo uma flag pra cada impl do GesTADSDataBaseInterface
        mDB = VolatileDataBase.getInstance();
        // [LAS] melhoria do log
    }

    //[CDS]
    public void startRepository(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "startRepository");
        
        mDB.startUpDadaBase();
//        if(mDB.getEmployees().isEmpty()){
//            if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
//                GesLogger.d(TAG, Thread.currentThread(), "DadaBase empty");
//
//            Employee root = new Employee();
//            root.setLogin("admin");
//            root.setSenha("admin");
//            root.setMatricula("admin");
//            root.setPrivilegio(Employee.PRIVILEGE_ADMIN);
//            mDB.insertEmployee(root);
//            return;
//        }
//        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
//            GesLogger.d(TAG, Thread.currentThread(), "DB not empty");
    }

    public static Repository getInstance(){
        if(instance == null){
            instance = new Repository();
        }
        if(GesLogger.ISFULLLOGABLE) GesLogger.d("Repository", Thread.currentThread(),
                "getInstance");
        return instance;
    }

    public List<Employee> getEmployees() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getEmployees");

        return new ArrayList<>(mDB.getEmployees());
    }

    public Employee getEmployeeByMatricula(String matricula) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getEmployeeByMatricula matricula: " + matricula);
        if(mDB.isDBInitialized()){
            return mDB.getEmployeeByMatricula(matricula); //[refactor] pensar sobre abordagem em outra thread
        }
        return null;
    }

    public void addEmployee(Employee employee) {
        mDB.insertEmployee(employee);
    }

    public boolean isDbReady() {
        boolean response = mDB.isDBInitialized();
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "isDbReady: " + response);
        return response;
    }
}