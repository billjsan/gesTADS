package src.model.repository;

import src.model.model.Employee;
import src.model.repository.database.GesTADSDataBaseInterface;
import src.model.repository.database.VolatileDataBase;
import src.util.tools.GesLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Repository {

    private static Repository sInstance;
    private final GesTADSDataBaseInterface mDB;
    private final String TAG = Repository.class.getSimpleName();
    private ExecutorService mExecutor = null;
    private List<String> mCargos = new ArrayList<>();

    //private final List<String> teste = Collections.synchronizedList(new ArrayList<>());

    private Repository(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG, Thread.currentThread(),
                "Repository constructor");

        mExecutor = Executors.newSingleThreadExecutor();
        // [ICS] - talvez usar uma factory recebendo uma flag pra cada impl do GesTADSDataBaseInterface
        mDB = VolatileDataBase.getInstance();
        // [LAS] melhoria do log
    }

    //[CDS]
    public void startRepository(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "startRepository");

        mExecutor.submit(() -> {
            mDB.startUpDadaBase();
            if(mDB.getEmployees().isEmpty()){
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, Thread.currentThread(), "DB is empty");

                Employee root = new Employee();
                root.setLogin("admin");
                root.setSenha("admin");
                root.setMatricula("admin");
                root.setPrivilegio(Employee.PRIVILEGE_ADMIN);
                mDB.insertEmployee(root);
            }else {
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, Thread.currentThread(), "DB is not empty");
            }
        });
    }

    public void closeDatabase() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "closeDatabase");

        if(mExecutor == null){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "executor is null");
            return;
        }

        try {
            mExecutor.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, e.getMessage());
        }
        finally {
                mExecutor.shutdown();
                mExecutor.shutdownNow();
        }
    }

    public static Repository getInstance(){
        if(sInstance == null){
            sInstance = new Repository();
        }
        if(GesLogger.ISFULLLOGABLE) GesLogger.d("Repository", Thread.currentThread(),
                "getInstance");
        return sInstance;
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

    public List<String> getPositions() {
        //[LAS]

        this.mCargos = mDB.getCargo();
       return new ArrayList<>(mCargos);
    }

    public void setPosition(String position){
        //[LAS]

        this.mCargos.add(position);
    }

    public Employee getEmployeeByCPF(String cpf) {
        //[LAS]

        return this.mDB.getEmployeeByCPF(cpf);
    }
}