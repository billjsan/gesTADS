package src.model.repository.database;

import src.model.model.Employee;
import src.model.model.Product;
import src.model.model.Transaction;
import src.util.GesLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VolatileDataBase implements GesTADSDataBaseInterface {

    public static final String TAG = VolatileDataBase.class.getSimpleName();
    private static VolatileDataBase instance;
    private volatile boolean isDBStarted;
    private final List<Employee> mEmployees = new ArrayList<>();
    private final List<String> mCargos = new ArrayList<>();
    private List<Product> mProdutos =  new ArrayList<>();
    private List<Transaction> mTransactions = new ArrayList<>();

    private VolatileDataBase(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "constructor");
    }

    public static VolatileDataBase getInstance(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getInstance");

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

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "erro fechando o banco de dados");
        }
    }

    @Override
    public boolean isDBInitialized() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "isDBInitialized");

        return isDBStarted;
    }

    @Override
    public Employee getEmployeeByCPF(String cpf) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getEmployeeByCPF "+ cpf);
        for (Employee e: mEmployees){
            if(e.getCpf().equals(cpf)){
                return e;
            }
        }

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
            GesLogger.e(TAG, "usuário não encontrado");
        return null;
    }

    @Override
    public Employee getEmployeeByNome(String nome) {
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
    public List<Employee> getEmployees() {
        return new ArrayList<>(mEmployees);
    }

    @Override
    public List<String> getCargos() {

        return new ArrayList<>(mCargos);
    }

    @Override
    public void setCargo(String cargo) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setCargo "+ cargo);

        this.mCargos.add(cargo);
    }

    @Override
    public void removeEmployee(Employee employee, Long id) {
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

    @Override
    public Product getProdutoPorId(Long id) {

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(),"getProdutoPorSerial: " + id);

        for (Product p: mProdutos) {
            if(p.getId().equals(id)){
                return p;
            }
        }

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
            GesLogger.e(TAG, "produto não encontrado");
        return null;
    }

    @Override
    public List<Product> getProdutos() {

        return new ArrayList<>(mProdutos);
    }

    @Override
    public void setProduto(Product produto) {

        mProdutos.add(produto);
    }

    @Override
    public void removeProdutoById(Long id) {

    }

    @Override
    public List<Product> getProdutosPorNome(String nome) {
        return null;
    }

    @Override
    public void updateProduto(Product product, Long id) {

        for (Product p: mProdutos) {
            if(p.getId().equals(id)){
                p.setQtdEsqoque(product.getQtdEstoque());
                p.setNome(product.getNome());
                p.setFabricante(product.getFabricante());
            }
        }
    }

    @Override
    public void setTransaction(Transaction transaction) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setTransaction");

        this.mTransactions.add(transaction);
    }

    @Override
    public List<Transaction> getTransacoes() {
        return null;
    }
}