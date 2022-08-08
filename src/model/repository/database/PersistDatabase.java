package src.model.repository.database;

import src.model.model.Employee;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static src.model.repository.database.ConnectionDataBase.getConnection;
import static src.model.repository.database.ConnectionDataBase.getCurrentConnection;


public class PersistDatabase {
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
    public void startUpDadaBase() throws SQLException, ClassNotFoundException {
        if (isDBInitialized == false) {
            getCurrentConnection();
            getConnection();
        }
        isDBInitialized = true;
    }

    public boolean isDBInitialized() {
        return isDBInitialized;
    }

    public void closeDataBase() throws SQLException, ClassNotFoundException {
        if (isDBInitialized == true) {
            getCurrentConnection().close();
            getConnection().close();
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
       } catch (ClassNotFoundException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (SQLException e) {
           throw new RuntimeException(e);
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


      } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } catch (SQLException e) {
          throw new RuntimeException(e);
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

      } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } catch (SQLException e) {
          throw new RuntimeException(e);
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


      } catch (ClassNotFoundException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
      } catch (SQLException e) {
          throw new RuntimeException(e);
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

        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
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

       } catch (ClassNotFoundException e) {
           // TODO Auto-generated catch block
           e.printStackTrace();
       } catch (SQLException e) {
           throw new RuntimeException(e);
       }

       return cargos;

   }

}