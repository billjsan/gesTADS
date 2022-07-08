package src.model.repository.database;

import src.model.model.Employee;
import src.util.tools.GesLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class VolatileDataBase implements GesTADSDataBaseInterface {
    public final String TAG = VolatileDataBase.class.getSimpleName();
    private final ExecutorService mExecutor;
    private static VolatileDataBase instance;
    private boolean isDBStarted;
    private final List<Employee> mEmployees = new ArrayList<>();

    private VolatileDataBase(){
        // [LAS]
        mExecutor = Executors.newFixedThreadPool(3); // define o n de Threads para o executor
    }

    public static VolatileDataBase getInstance(){
        // [LAS]
        if(instance == null){
            instance = new VolatileDataBase();
        }
        return instance;
    }
    @Override
    public void startUpDadaBase() {

        mExecutor.submit(() -> {
            try {
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, Thread.currentThread(),"startUpDadaBase");

                Thread.sleep(5000);
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, Thread.currentThread(),"banco de dados inicializado");
                isDBStarted = true;
                //closeDataBase();
            } catch (InterruptedException e) {
                if(GesLogger.ISFULLLOGABLE) GesLogger.e(TAG, e.getMessage());
            }
        });
    }

    @Override
    public void closeDataBase() {
        if(GesLogger.ISFULLLOGABLE) GesLogger.d(TAG,
                Thread.currentThread(),"closeDataBase");
        mExecutor.shutdown();
        try {
            if (!mExecutor.awaitTermination(1, TimeUnit.SECONDS)) {
                mExecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            mExecutor.shutdownNow();
            if(GesLogger.ISFULLLOGABLE) GesLogger.e(TAG, e.getMessage());
        }
    }

    @Override
    public boolean isDBInitialized() {
        // [LAS]
        return isDBStarted;
    }

    @Override
    public void executeInsertQuery(String query) {
        // [LAS]
    }

    @Override
    public void allOtherMethods() {
        // [LAS]
    }

    @Override
    public Employee getEmployeeByMatricula(String matricula) {
        // [LAS]
        for (Employee e: mEmployees){
            if(e.getMatricula().equals(matricula)){
                return e;
            }
        }
        return null;
    }

    @Override
    public void insertEmployee(Employee employee) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"insert default employee: login " + employee.getLogin()
            + " senha: " + employee.getSenha() + " matricula: " + employee.getMatricula());

        mEmployees.add(employee);
    }

    @Override
    public boolean isEmptyDB() {
        return true; //mEmployees.isEmpty();
    }

    @Override
    public List<Employee> getEmployees() {
        return new ArrayList<>(mEmployees);
    }
}