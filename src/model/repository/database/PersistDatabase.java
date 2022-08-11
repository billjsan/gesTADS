package src.model.repository.database;

import src.model.model.Employee;
import src.model.model.Product;
import src.model.model.Transaction;
import src.util.GesLogger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersistDatabase implements GesTADSDataBaseInterface {

    private final String TAG = PersistDatabase.class.getSimpleName();

    private boolean isDBInitialized;
    private static PersistDatabase instance;


    private PersistDatabase() {
        if (GesLogger.ISFULLLOGABLE || GesLogger.ISSAFELOGGABLE)
            GesLogger.d(TAG, Thread.currentThread(), "Instanceate database");
    }

    public static PersistDatabase getInstance() {
        if (instance == null) {
            instance = new PersistDatabase();
        }
        return instance;
    }

    public void startUpDadaBase() {

        if (!isDBInitialized) {

            ConnectionDataBase.getCurrentConnection();
            ConnectionDataBase.getConnection();

        }
        isDBInitialized = true;
    }

    public boolean isDBInitialized() {
        return isDBInitialized;
    }

    public void closeDataBase() {

        if (isDBInitialized) {

            try {
                ConnectionDataBase.getCurrentConnection().close();
                ConnectionDataBase.getConnection().close();

            } catch (SQLException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "getCurrentConnection error: " + e.getMessage());

            } catch (NullPointerException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "getConnection error: " + e.getMessage());
            }

        }

        isDBInitialized = false;
    }

    public void insertEmployee(Employee employee) {
        String sql = "insert into employee(id, nome, login, senha, cargo, cpf) values (?,?,?,?,?,?)";

        try {
            PreparedStatement pstm = ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setLong(1, employee.getId());
            pstm.setString(2, employee.getNome());
            pstm.setString(3, employee.getLogin());
            pstm.setString(4, employee.getSenha());
            pstm.setString(5, employee.getCargo());
            pstm.setString(6, employee.getCpf());

            pstm.execute();

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao inserir employee: " + e.getMessage());
        } catch (NullPointerException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao inserir employee: " + e.getMessage());
        }
    }

    public List<Employee> getEmployees() {
        String sql = "select * from employee";
        List<Employee> employees = new ArrayList<>();

        try {
            PreparedStatement pstm = ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

                Employee e = new Employee();
                e.setId(rs.getLong("id"));
                e.setNome(rs.getString("nome"));
                e.setLogin(rs.getString("login"));
                e.setSenha(rs.getString("senha"));
                e.setCargo(rs.getString("cargo"));
                e.setCpf(rs.getString("cpf"));

                employees.add(e);
            }

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao listar employee: " + e.getMessage());
        }

        return employees;
    }

    public Employee getEmployeeByCPF(String cpf) {
        String sql = "select * from employee where cpf = ?";

        try {
            PreparedStatement pstm = ConnectionDataBase.getCurrentConnection().prepareStatement(sql);

            pstm.setString(1, cpf);

            ResultSet rs = pstm.executeQuery();

            Employee e = null;

            if (rs.next()) {
                e = new Employee();
                e.setId(rs.getLong("id"));
                e.setNome(rs.getString("nome"));
                e.setLogin(rs.getString("login"));
                e.setSenha(rs.getString("senha"));
                e.setCargo(rs.getString("cargo"));
                e.setCpf(cpf);
            }

            return e;

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao buscar employee por cpf: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Employee getEmployeeByNome(String nome) {
        String sql = "select * from employee where nome = ?";

        try {
            PreparedStatement pstm = ConnectionDataBase.getCurrentConnection().prepareStatement(sql);
            pstm.setString(1, nome);

            ResultSet rs = pstm.executeQuery();

            Employee e = null;

            if (rs.next()) {
                e = new Employee();
                e.setId(rs.getLong("id"));
                e.setLogin(rs.getString("login"));
                e.setSenha(rs.getString("senha"));
                e.setCargo(rs.getString("cargo"));
                e.setCpf(rs.getString("cpf"));
                e.setNome(nome);
            }

            return e;

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao buscar employee por cpf: " + e.getMessage());
        }
        return null;
    }

    public void removeEmployee(Employee employee, Long id) {

        String sql = "delete from employee where id = ?";

        try {
            PreparedStatement pstm = ConnectionDataBase
                    .getCurrentConnection().prepareStatement(sql);

            pstm.setLong(1, id);
            pstm.execute();

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao remover employee: " + e.getMessage());

        } catch (NullPointerException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao excluir employee: " + e.getMessage());
        }
    }

    public void updateEmployee(Employee employee, Long id) {

        String sql = "update employee set nome = ?, "
                + "login = ?, cargo = ?,  cpf = ?"
                + "where id = ?";

        try {
            PreparedStatement pstm = ConnectionDataBase.
                    getCurrentConnection().prepareStatement(sql);

            pstm.setString(1, employee.getNome());
            pstm.setString(2, employee.getLogin());
            pstm.setString(3, employee.getCargo());
            pstm.setString(4, employee.getCpf());
            pstm.setLong(5, id);
            pstm.execute();

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao atualizar employee: " + e.getMessage());

        } catch (NullPointerException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao atualizar employee: " + e.getMessage());

        }
    }

    public List<String> getCargos() {

        String sql = "select nome from cargo";

        List<String> cargos = new ArrayList<>();

        try {
            PreparedStatement pstm = ConnectionDataBase
                    .getCurrentConnection().prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                cargos.add(((rs.getString("nome"))));
            }

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao inserir cargo: " + e.getMessage());
        }

        return cargos;
    }

    public void setCargo(String position) {

        String sql = "insert into cargo(nome) values (?)";

        try {
            PreparedStatement pstm = ConnectionDataBase
                    .getCurrentConnection().prepareStatement(sql);

            pstm.setString(1, position);
            pstm.execute();

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao inserir cargo: " + e.getMessage());
        }

    }

    @Override
    public Product getProdutoPorId(Long id) {

        String sql = "select * from produto where id = ?";

        try {
            PreparedStatement pstm = ConnectionDataBase
                    .getCurrentConnection().prepareStatement(sql);

            pstm.setLong(1, id);

            ResultSet rs = pstm.executeQuery();

            Product p = null;

            if (rs.next()) {
                p = new Product();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setFabricante(rs.getString("fabricante"));
                p.setFabricante(rs.getString("quantidade"));
            }

            return p;

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao buscar produto por id: " + e.getMessage());
        }

        return null;
    }

    @Override
    public List<Product> getProdutos() {
        String sql = "select * from produto";
        List<Product> produtos = new ArrayList<>();

        try {
            PreparedStatement pstm = ConnectionDataBase
                    .getCurrentConnection().prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {

                Product p = new Product();
                p.setId(rs.getLong("id"));
                p.setNome(rs.getString("nome"));
                p.setFabricante(rs.getString("fabricante"));
                p.setQtdEsqoque(rs.getInt("quantidade"));

                produtos.add(p);
            }

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao listar produtos: " + e.getMessage());
        }

        return produtos;
    }

    @Override
    public void setProduto(Product produto) {
        String sql = "insert into produto (id, nome, fabricante, quantidade) values (?,?,?,?)";

        try {
            PreparedStatement pstm = ConnectionDataBase
                    .getCurrentConnection().prepareStatement(sql);

            pstm.setLong(1, produto.getId());
            pstm.setString(2, produto.getNome());
            pstm.setString(3, produto.getFabricante());
            pstm.setInt(4, produto.getQtdEstoque());

            pstm.execute();

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao inserir produto: " + e.getMessage());
        }
    }

    public void removeProdutoById(Long id) {
        String sql = "delete from produto where id = ?";

        try {
            PreparedStatement pstm = ConnectionDataBase
                    .getCurrentConnection().prepareStatement(sql);

            pstm.setLong(1, id);
            pstm.execute();

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao remover produto: " + e.getMessage());

        } catch (NullPointerException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao excluir produto: " + e.getMessage());
        }
    }

    /**
     * o metodo como está retorna apenas um produto por nome,
     * mas nós podemos ter mais de um produto com o mesmo nome.
     * Então precisa retornar uma lista.
     * <p>
     * exemplo de busca: "xicara"
     * <p>
     * resultados: "xicara", "xicara grande", "xicara pequena", etc.
     * <p>
     * todos os produtos que CONTÉM aquele @param nome devem ser incluidos no resultado
     */
    @Override
    public List<Product> getProdutosPorNome(String nome) {


//        String sql = "select * from produto where nome = ?";
//
//        try {
//            PreparedStatement pstm = src.model.repository.database.PersistDatabase.ConnectionDataBase
//                    .getCurrentConnection().prepareStatement(sql);
//
//            pstm.setString(1, nome);
//
//            ResultSet rs = pstm.executeQuery();
//
//            Product p = null;
//
//            if(rs.next()) {
//                p = new Product();
//                p.setId(rs.getLong("id"));
//                p.setNome(rs.getString("nome"));
//                p.setFabricante(rs.getString("fabricante"));
//                p.setFabricante(rs.getString("quantidade"));
//            }
//
//            return p;
//
//        } catch (SQLException e) {
//            if(GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
//                GesLogger.e(TAG, "Erro ao buscar produto por id: " + e.getMessage());
//        }
//
        return null;
    }

    @Override
    public void updateProduto(Product product, Long id) {
        String sql = "update produto set nome = ?, "
                + "fabricante = ?, quantidade = ? where id = ?";

        try {
            PreparedStatement pstm = ConnectionDataBase
                    .getCurrentConnection().prepareStatement(sql);

            pstm.setString(1, product.getNome());
            pstm.setString(2, product.getFabricante());
            pstm.setLong(3, product.getQtdEstoque());
            pstm.setLong(4, id);
            pstm.execute();

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao atualizar produto: " + e.getMessage());

        } catch (NullPointerException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao atualizar produto: " + e.getMessage());
        }
    }

    @Override
    public void setTransaction(Transaction transaction) {

        String sql = "insert into transaction (id, id_solicitante, id_produto, tipotransacao, quantidade) values (?,?,?,?,?)";

        try {
            PreparedStatement pstm = ConnectionDataBase
                    .getCurrentConnection().prepareStatement(sql);

            pstm.setLong(1, transaction.getId());
            pstm.setLong(2, transaction.getSolicitanteId());
            pstm.setLong(3, transaction.getProdutoId());
            pstm.setInt(4, transaction.getTipo());
            pstm.setInt(5, transaction.getQuantidade());

            pstm.execute();

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao criar uma transacao: " + e.getMessage());

        } catch (NullPointerException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro de exceção ao criar uma transacao: " + e.getMessage());
        }
    }

    @Override
    public List<Transaction> getTransacoes() {
        String sql = "select * from transaction";
        List<Transaction> transactions = new ArrayList<>();

        try {
            PreparedStatement pstm = ConnectionDataBase
                    .getCurrentConnection().prepareStatement(sql);

            ResultSet rs = pstm.executeQuery();

            while (rs.next()) {
                Transaction t = new Transaction();
                t.setId(rs.getLong("id"));
                t.setSolicitante(rs.getLong("id_solicitante"));
                t.setProdutoId(rs.getLong("id_produto"));
                t.setTipoTranacao(rs.getInt("tipotransacao"));
                t.setQuantidade(rs.getInt("quantidade"));

                transactions.add(t);
            }

        } catch (SQLException e) {
            if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                GesLogger.e(TAG, "Erro ao listar transações: " + e.getMessage());
        }

        return transactions;
    }

    private static class ConnectionDataBase {

        private static final String TAG = "ConnectionDataBase";
        private static final String URL = "jdbc:mysql://localhost/gesTADS?serverTimezone=UTC";
        private static final String USER = "gesTADSuser";
        private static final String PASSWORD = "gesTADSpassword";

        private static Connection currentConnection = null;

        public static Connection getCurrentConnection() {
            try {
                //Class.forName("com.mysql.jdbc.Driver");
                Class.forName("com.mysql.cj.jdbc.Driver").newInstance();

                if (currentConnection == null)
                    currentConnection = DriverManager.getConnection(URL, USER, PASSWORD);

            } catch (ClassNotFoundException e) {

                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "erro de recurso solicitado por um cliente não foi encontrado no servidor.: " + e.getMessage());


            } catch (SQLException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "erro de inserção de SQL: " + e.getMessage());

            } catch (InstantiationException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "erro de instanciação do database: " + e.getMessage());

            } catch (IllegalAccessException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "acesso ilegal : " + e.getMessage());
            }

            return currentConnection;
        }

        public static Connection getConnection() {

            try {
                return DriverManager.getConnection(URL, USER, PASSWORD);

            } catch (SQLException e) {
                if (GesLogger.ISFULLLOGABLE || GesLogger.ISERRORLOGABLE)
                    GesLogger.e(TAG, "erro de inserção de SQL: " + e.getMessage());
            }

            return null;
        }
    }
}