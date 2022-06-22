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
        mDB = VolatileDataBase.getInstance(); //operacao sincrona na thread main
        if(GesLogger.ISFULLLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName()
                + " antes de iniciar outa thread");

        mDB.startUpDadaBase(); // inicia uma thread de execução
        if(GesLogger.ISFULLLOGABLE) GesLogger.d(TAG, Thread.currentThread().getName()
                + " depois de iniciar outa thread");
    }

    public boolean checkDb(){
        System.out.println("is db ok: " + mDB.isDBInitialized());
        return mDB.isDBInitialized();
    }

    public static Repository getInstance(){  //singleton nullable
        if(instance == null){
            instance = new Repository();
        }
        if(GesLogger.ISFULLLOGABLE) GesLogger.d("Repository", Thread.currentThread().getName() +
                " vou retornar uma instacia de repository");
        return instance;
    }

    public HashMap<String, String> getCredentialsInfo() {
       return new HashMap<String, String>();
    }

    public List<Employee> getEmployees() {

        return new ArrayList<>();
    }

    public Employee getEmployeeByMatricula(String matricula) {
        if(GesLogger.ISFULLLOGABLE) GesLogger.d(TAG, Thread.currentThread(), "getEmployeeByMatricula");
        if(mDB.isDBInitialized()){
            return mDB.getEmployeeByMatricula(matricula); //[refactor] pensar sobre abordagem em outra thread
        }
        return null;
    }

    public void addEmployee(Employee employee) {
        mDB.insertEmployee(employee);
    }
}