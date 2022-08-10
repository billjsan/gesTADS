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
        String sql = "insert into employee(id, nome, login, senha, cargo, cpf) values (?,?,?,?,?,?)";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setLong(1,employee.getId());
            pstm.setString(2,employee.getNome());
            pstm.setString(3,employee.getLogin());
            pstm.setString(4,employee.getSenha());
            pstm.setString(5,employee.getCargo());
            pstm.setString(6,employee.getCpf());

            pstm.execute();

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao inserir employee: " + e.getMessage());
        } catch (Exception e){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao inserir employee: " + e.getMessage());
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

        String sql = "delete from employee where id = ?";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setLong(1, employee.getId());
            pstm.execute();

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao remover employee: " + e.getMessage());
        } catch (Exception e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao excluir employee: " + e.getMessage());
        }
    }

    public void updateEmployee(Employee employee, Long id){

        String sql = "update employee set nome = ?, "
                + "login = ?, cargo = ?,  cpf = ?"
                + "where id = ?";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setString(1, employee.getNome());
            pstm.setString(2, employee.getLogin());
            pstm.setString(3, employee.getCargo());
            pstm.setString(4, employee.getCpf());
            pstm.setLong(5, id);
            pstm.execute();

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao atualizar employee: " + e.getMessage());
        } catch (Exception e){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao atualizar employee: " + e.getMessage());
        }
    }

    public List<String> getCargos() {

        String sql = "select nome from cargo";

        List<String> cargos = new ArrayList<>();

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while(rs.next()){
                cargos.add(((rs.getString("nome"))));
            }

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao inserir cargo: " + e.getMessage());
        }

        return cargos;
    }

    public void setCargo(String position){

       String sql = "insert into cargo(nome) values (?)";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);
            pstm.setString(1, position);
            pstm.execute();

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao inserir cargo: " + e.getMessage());
        }

    }

    @Override
    public Product getProdutoPorId(Long id) {

        String sql = "select * from produto where id = ?";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);
            pstm.setLong(1, id);

            ResultSet rs = pstm.executeQuery();

            Product p = null;

            if(rs.next()) {
                p = new Product();
                p.setNome(rs.getString("nome"));
                p.setSerialN(rs.getString("serialn"));
                p.setFabricante(rs.getString("fabricante"));
                p.setDescricao(rs.getString("descricao"));
                p.setFabricacao(rs.getString("fabricacao"));
                p.setValidade(rs.getString("validade"));
            }

            return p;

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao buscar produto por id: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Product> getProdutos() {
        String sql = "select * from produto";
        List<Product> produtos = new ArrayList<>();

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while(rs.next()) {

                Product p = new Product();
                p.setNome(rs.getString("nome"));
                p.setSerialN(rs.getString("serialn"));
                p.setFabricante(rs.getString("fabricante"));
                p.setDescricao(rs.getString("descricao"));
                p.setFabricacao(rs.getString("fabricacao"));
                p.setValidade(rs.getString("validade"));

                produtos.add(p);
            }

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao listar produtos: " + e.getMessage());
        }

        return produtos;
    }

    @Override
    public void setProduto(Product produto) {
        String sql = "insert into produto (id, nome, serialn, fabricante, descricao, fabricacao, validade) values (?,?,?,?,?,?,?)";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setLong(1, produto.getId());
            pstm.setString(2, produto.getNome());
            pstm.setString(3, produto.getSerialN());
            pstm.setString(4, produto.getFabricante());
            pstm.setString(5, produto.getDescricao());
            pstm.setString(6, produto.getFabricacao());
            pstm.setString(7, produto.getValidade());

            pstm.execute();

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao inserir produto: " + e.getMessage());
        }
    }

    @Override
    public void editProduto(Product produto, Long id) {
        String sql = "update produto set nome = ?, "
                + "serialn = ?, fabricante = ?,  descricao = ?," +
                "fabricacao = ?, validade = ?"
                + "where id = ?";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setString(1, produto.getNome());
            pstm.setString(2, produto.getSerialN());
            pstm.setString(3, produto.getFabricante());
            pstm.setString(4, produto.getDescricao());
            pstm.setString(5, produto.getFabricacao());
            pstm.setString(6, produto.getValidade());
            pstm.setLong(7, id);
            pstm.execute();

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao atualizar produto: " + e.getMessage());
        } catch (Exception e){
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao atualizar produto: " + e.getMessage());
        }
    }

    @Override
    public void removeProduto(Product produto) {

        String sql = "delete from produto where id = ?";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);
            pstm.setLong(1, produto.getId());
            pstm.execute();

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao remover produto: " + e.getMessage());
        } catch (Exception e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao excluir produto: " + e.getMessage());
        }

    }

    @Override
    public void setSaidaProduto(Transacao transacao) {

    }

    @Override
    public void setEntradaProduto(Transacao transacao) {

    }

    @Override
    public Product getProdutoPorSerial(String serialNo) {

        String sql = "select * from produto where serialn = ?";

        try {
            PreparedStatement pstm = src.model.repository.database.ConnectionDataBase.getCurrentConnection().prepareStatement(sql);
            pstm.setString(1, serialNo);

            ResultSet rs = pstm.executeQuery();

            Product p = null;

            if(rs.next()) {
                p = new Product();
                p.setNome(rs.getString("nome"));
                p.setSerialN(rs.getString("serialn"));
                p.setFabricante(rs.getString("fabricante"));
                p.setDescricao(rs.getString("descricao"));
                p.setFabricacao(rs.getString("fabricacao"));
                p.setValidade(rs.getString("validade"));
            }

            return p;

        } catch (SQLException e) {
            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao buscar produto por serialn: " + e.getMessage());
        }

        return null;
    }

}