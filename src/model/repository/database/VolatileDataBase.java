package src.model.repository.database;

import src.model.model.Employee;
import src.util.tools.GesLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VolatileDataBase implements GesTADSDataBaseInterface {

    public final String TAG = VolatileDataBase.class.getSimpleName();
    private static VolatileDataBase instance;
    private volatile boolean isDBStarted;
    private final List<Employee> mEmployees = new ArrayList<>();
    private final List<String> mCargos = new ArrayList<>();
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

                Thread.sleep(3000);
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, Thread.currentThread(),"banco de dados inicializado");
                isDBStarted = true;

            } catch (InterruptedException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, e.getMessage());
            }
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
    public void insertEmployee(Employee employee) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"insertEmployee: login: " + employee.getLogin()
            + " senha: " + employee.getSenha() + " id: " + employee.getId());

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
    public List<String> getCargos() {

        return new ArrayList<>(mCargos);
    }

    @Override
    public void setCargo(String cargo) {
        //[LAS]

        this.mCargos.add(cargo);
    }

    @Override
    public void removeEmployee(Employee employee) {
        // [LAS]

        mEmployees.removeIf(storedEmployee -> storedEmployee.getCpf().equals(employee.getCpf()));
    }

    @Override
    public void updateEmployee(Employee editedEmployee, Long id) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "updateEmployee name: "
                    + editedEmployee.getNome() + " id: " + id);

        for (Employee storedEmployee: mEmployees) {
            if (Objects.equals(storedEmployee.getId(), id)){
                storedEmployee.setNome(editedEmployee.getNome());
                storedEmployee.setLogin(editedEmployee.getLogin());
                storedEmployee.setCpf(editedEmployee.getCpf());
                storedEmployee.setCargo(editedEmployee.getCargo());

                return;
            }
        }
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
            GesLogger.e(TAG, "usuário não encontrado");
    }
}