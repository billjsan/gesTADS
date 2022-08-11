package src.model.repository;

import src.model.model.Employee;
import src.model.model.Product;
import src.model.model.Transaction;
import src.model.repository.database.GesTADSDataBaseInterface;
import src.util.GesLogger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// [CDS] explicar o que a classe faz
public class Repository {

    private static Repository sInstance;
    private final GesTADSDataBaseInterface mDataBase;
    private static final String TAG = Repository.class.getSimpleName();
    private Employee currentUser;
    private ExecutorService mExecutor = null;
    private List<String> mCargos = new ArrayList<>();
    private boolean mIsLoggedIn = false;

    private Repository() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE) GesLogger.d(TAG, Thread.currentThread(),
                "Repository constructor");

        mExecutor = Executors.newSingleThreadExecutor();
        mDataBase = DBFactory.getDatabase(DBFactory.PERSIST_DB);
    }

    //[CDS]
    public void startRepository() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "startRepository");

        mExecutor.submit(() -> {
            mDataBase.startUpDadaBase();
            if (mDataBase.getEmployees().isEmpty()) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, Thread.currentThread(), "DB is empty");

                //configura user root do sistema
                Employee root = new Employee();
                root.setLogin("admin");
                root.setSenha("admin");
                root.setCargo(Employee.POSITION_ADMIN);
                root.setCpf("00000000000");
                root.generateID();
                root.setPrivilegio(Employee.PRIVILEGE_ADMIN);
                mDataBase.insertEmployee(root);

                //define os cargos padrão
                mDataBase.setCargo(Employee.POSITION_ADMIN);
                mDataBase.setCargo(Employee.POSITION_SUPERVISOR);
                mDataBase.setCargo(Employee.POSITION_OPERADOR);

            } else {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
                    GesLogger.d(TAG, Thread.currentThread(), "DB is not empty");
            }
        });
    }

    public void closeDatabase() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "closeDatabase");

        if (mExecutor == null) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "executor is null");
            return;
        }

        try {
            mDataBase.closeDataBase();
            mExecutor.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "can't awaitTermination " + e.getMessage());
        } finally {
            mExecutor.shutdown();
            mExecutor.shutdownNow();
        }
    }

    public static Repository getInstance() {
        if (sInstance == null) {
            sInstance = new Repository();
        }

        if (GesLogger.ISFULLLOGABLE) GesLogger.d(TAG, Thread.currentThread(), "getInstance");
        return sInstance;
    }

    public List<Employee> getEmployees() {

        ArrayList<Employee> employees = new ArrayList<>(mDataBase.getEmployees());

        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getEmployees size: " + employees.size());

        return employees;
    }

    public void addEmployee(Employee employee) {

        employee.generateID();
        String info = "id: " + employee.getId() + " nome: " + employee.getNome() + " cpf: "
                + employee.getCpf() + " cargo: " + employee.getCargo() + " priv: " + employee.getPrivilegio() ;

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "addEmployee: " + info);


        mDataBase.insertEmployee(employee);
    }

    public boolean isDbReady() {
        boolean response = mDataBase.isDBInitialized();
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "isDbReady: " + response);

        return response;
    }

    public List<String> getCargos() {

        this.mCargos = mDataBase.getCargos();
        ArrayList<String> cargos = new ArrayList<>(mCargos);

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getCargos size: " + cargos.size());

        return cargos;
    }

    public void setPosition(String position) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setPositions "+ position);

        this.mCargos.add(position);
    }

    public Employee getEmployeeByCPF(String cpf) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getEmployeeByCPF"+ cpf);

        return this.mDataBase.getEmployeeByCPF(cpf);
    }

    public void removeEmployee(Employee empregado, Long id) {
        // [LAS] mostrar nome e cpf do empregado

        mDataBase.removeEmployee(empregado, id);
    }

    public void updateEmployee(Employee employee, Long id) {

        mDataBase.updateEmployee(employee, id);
    }

    public List<Product> getProdutos() {

        return mDataBase.getProdutos();
    }

    public void addProduct(Product product) {
        String info = "id: " + product.getId() + " nome: " + product.getNome() + " fab: "
                + product.getFabricante() + " qtdEst.: " + product.getQtdEstoque();

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "addProduct: " + info);

        mDataBase.setProduto(product);
    }

    public Employee getCurrentUser(){
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getCurrentUser name: " + currentUser.getNome() +
                    " cargo: " + currentUser.getCargo());

        return currentUser;
    }

    public void setCurrentUser(Employee employee) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "setCurrentUser name: " + employee.getNome() +
                    " cargo: " + employee.getCargo());

        currentUser = employee;
    }

    public boolean isLoggedIn() {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "isLoggedIn");

        return mIsLoggedIn;
    }

    /**
     * não é a coisa mais inteligente do mundo mas resolve o problema de armazenar o status da sessão
     * para ser acessador por todos os controladores
     * define o @param status da sessão
     */
    public void isLoggedIn(boolean status) {
        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "isLoggedIn status "+ status);

        this.mIsLoggedIn = status;
    }

    public Product getProductBySerial(String serialNo) {
     /*   if(GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "getProductBYSerial");

        return this.mDataBase.getProdutoPorSerial(serialNo);*/
        return null;
    }

    public void updateProduto(Product product, Long id) {
        String info = "id: " + id + " nome: " + product.getNome() +
                " fab: " + product.getFabricante() + " qtdEst: " + product.getQtdEstoque();

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "updateProduto: " + info);
        mDataBase.updateProduto(product, id);
    }

    public void addTransaction(Transaction transaction) {
        String info = "id: " + transaction.getId() + " sol.id: " + transaction.getSolicitanteId() +
                "prod.id: " + transaction.getProdutoId() + " qtd: " + transaction.getQuantidade() +
                " tipo: " + transaction.getTipo();

        if(GesLogger.ISFULLLOGABLE || GesLogger.ISSENSITIVELOGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "addTransaction: " + info);

        mDataBase.setTransaction(transaction);
    }
}