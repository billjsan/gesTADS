package src.model.repository.database;

import src.model.model.Employee;
import src.util.tools.GesLogger;
import src.util.tools.Intent;

import java.util.ArrayList;
import java.util.List;

public class VolatileDataBase implements GesTADSDataBaseInterface {
    public final String TAG = VolatileDataBase.class.getSimpleName();
    private static VolatileDataBase instance;
    private volatile boolean isDBStarted;
    private final List<Employee> mEmployees = new ArrayList<>();
    private final List<String> mPositions = new ArrayList<>();
    private VolatileDataBase(){
        // [LAS]
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

            try {
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, Thread.currentThread(),"startUpDadaBase");

                populateDatabase();
                Thread.sleep(3000);
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, Thread.currentThread(),"banco de dados inicializado");
                isDBStarted = true;

            } catch (InterruptedException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, e.getMessage());
            }
    }

    private void populateDatabase() {
        if(GesLogger.ISFULLLOGABLE) GesLogger.d(TAG,
                Thread.currentThread(),"populateDatabase");

    setCargo(Employee.POSITION_ADMIN);
    setCargo(Employee.POSITION_SUPERVISOR);
    setCargo(Employee.POSITION_OPERADOR);
    }

    @Override
    public void closeDataBase() {
        if(GesLogger.ISFULLLOGABLE) GesLogger.d(TAG,
                Thread.currentThread(),"closeDataBase");

    }

    @Override
    public boolean isDBInitialized() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "isDBInitialized");

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
    public Employee getEmployeeByCPF(String cpf) {
        // [LAS]
        for (Employee e: mEmployees){
            if(e.getCpf().equals(cpf)){
                return e;
            }
        }
        return null;
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
            GesLogger.d(TAG, Thread.currentThread(),"insert employee: login " + employee.getLogin()
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

    @Override
    public List<String> getCargo() {

        return new ArrayList<>(mPositions);
    }

    @Override
    public void setCargo(String position) {
        //[LAS]

        this.mPositions.add(position);
    }

    @Override
    public void removeEmployee(Employee employee) {
        // [LAS]

        mEmployees.removeIf(storedEmployee -> storedEmployee.getCpf().equals(employee.getCpf()));
    }
}