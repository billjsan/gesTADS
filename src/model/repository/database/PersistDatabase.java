package src.model.repository.database;

import src.model.model.Employee;
import src.model.model.Product;
import src.model.model.Transacao;
import src.util.tools.GesLogger;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PersistDatabase implements GesTADSDataBaseInterface {
    private final String TAG = PersistDatabase.class.getSimpleName();
    private boolean isDBInitialized;
    private static PersistDatabase instance;
    private final ExecutorService mExecutor;

    private PersistDatabase() {
        mExecutor = Executors.newFixedThreadPool(3); // define o n de Threads para o executor
    }

    public static PersistDatabase getInstance() {
        if (instance == null) {
            instance = new PersistDatabase();
        }
        return instance;
    }

    public void startUpDadaBase()  {

        if (isDBInitialized == false) {

            ConnectionDataBase.getCurrentConnection();
            ConnectionDataBase.getConnection();

        }
        isDBInitialized = true;
    }

    public boolean isDBInitialized() {
        return isDBInitialized;
    }

    public void closeDataBase()  {

        if (isDBInitialized == true) {

            try {
                ConnectionDataBase.getCurrentConnection().close();
                ConnectionDataBase.getConnection().close();

            } catch (SQLException e) {
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "getCurrentConnection error: " + e.getMessage());

            } catch (NullPointerException e){
                if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "getConnection error: " + e.getMessage());
            }

        }
        isDBInitialized = false;
    }

    public void insertEmployee(Employee employee){
        String sql = "insert into employee(nome, login, senha, cargo, cpf) values (?,?,?,?,?)";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setString(1,employee.getNome());
            pstm.setString(2,employee.getLogin());
            pstm.setString(3,employee.getSenha());
            pstm.setString(4,employee.getCargo());
            pstm.setString(5,employee.getCpf());

            pstm.execute();

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao inserir employee: " + e.getMessage());
        }
    }

    public  List<Employee> getEmployees(){
        String sql = "select * from employee";
        List<Employee> employees = new ArrayList<>();

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {

                Employee e = new Employee();

                e.setNome(rs.getString("nome"));
                e.setLogin(rs.getString("login"));
                e.setSenha(rs.getString("senha"));
                e.setCargo(rs.getString("cargo"));
                e.setCpf(rs.getString("cpf"));

                employees.add(e);
            }



        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao listar employee: " + e.getMessage());
        }

        return employees;
    }
    public Employee getEmployeeByCPF(String cpf) {
        String sql = "select * from employee where cpf = ?";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setString(1, cpf);

            ResultSet rs = pstm.executeQuery();

            Employee e = null;

            if(rs.next()) {
                e = new Employee();
                e.setNome(rs.getString("nome"));
                e.setLogin(rs.getString("login"));
                e.setSenha(rs.getString("senha"));
                e.setCargo(rs.getString("cargo"));
                e.setCpf(cpf);
            }

            return e;

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao buscar employee por cpf: " + e.getMessage());
        }
        return null;
    }

    public void removeEmployee(Employee employee) {

        long id = employee.getId();

        String sql = "delete from employee where id = ?";

        try {

            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setLong(1, id);

            pstm.execute();

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao remover employee: " + e.getMessage());
        }
    }

    public void updateEmployee(Employee employee, Long id){

        String sql = "update employee set nome = ?, "
                + "login = ?, senha = ?, cargo = ?, cpf = ?"
                + "where id = ?";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setString(1, employee.getNome());
            pstm.setString(2, employee.getLogin());
            pstm.setString(3, employee.getSenha());
            pstm.setString(4, employee.getCargo());
            pstm.setString(5, employee.getCpf());
            pstm.setLong(6, id);

            pstm.execute();

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao atualizar employee: " + e.getMessage());
        }
    }

    public List<String> getCargos() {

        String sql = "select cargo from employee";

        List<String> cargos = new ArrayList<>();

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {

                Employee e = new Employee();

                e.setCargo(rs.getString("cargo"));

                cargos.add(String.valueOf(e));
            }

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao inserir cargo: " + e.getMessage());
        }

        return cargos;
    }

    public void setCargo(String position){

    }

    @Override
    public Product getProdutoPorId(Long id) {
        return null;
    }

    @Override
    public List<Product> getProdutos() {
        return null;
    }

    @Override
    public void setProduto(Product produto) {

    }

    @Override
    public void editProduto(Product produto, Long id) {

    }

    @Override
    public void removeProduto(Product produto) {

    }

    @Override
    public void setSaidaProduto(Transacao transacao) {

    }

    @Override
    public void setEntradaProduto(Transacao transacao) {

    }

    @Override
    public Product getProdutoPorSerial(String serialNo) {
        return null;
    }

}